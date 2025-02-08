package com.example.assignment.service.Impl;

import com.example.assignment.dto.ExternalUserDto;
import com.example.assignment.dto.ExternalUserResponse;
import com.example.assignment.entity.User;
import com.example.assignment.exception.ResourceNotFoundException;
import com.example.assignment.repository.UserRepository;
import com.example.assignment.service.UserService;
import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Value(("${external.api.url}"))
    private String externalApiUrl;

    @Override
    public void loadUsers() {
        log.info("Fetching user data from external API");
        ExternalUserResponse response = restTemplate.getForObject(externalApiUrl, ExternalUserResponse.class);
        if (response != null && response.getUsers() != null) {
            List<User> users = (List<User>) response.getUsers().stream()
                    .map(this::mapToUser)
                    .toList();

            userRepository.saveAll(users);
            log.info("Successfully loaded {} users into the database.", users.size());
        }
    }

    private User mapToUser(ExternalUserDto userDto) {
        User user = new User();
        try {
            user.setId(Long.valueOf(userDto.getId()));
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setSsn(userDto.getSsn());
            user.setPhone(userDto.getPhone());
            return user;
        }catch (Exception e) {
            throw e;
        }
    }


    @Override
    public List<User> searchUsers(String query) {
        log.info("Performing free-text search for: {}", query);
        SearchSession searchSession = Search.session(entityManager);
        return searchSession.search(User.class)
                .where(f -> f.match()
                        .fields("firstName", "lastName", "ssn")
                        .matching(query))
                .fetchHits(20);
    }

    @Override
    public User getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
