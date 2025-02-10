package com.example.assignment.service;

import com.example.assignment.entity.User;
import com.example.assignment.exception.ResourceNotFoundException;
import com.example.assignment.repository.UserRepository;
import com.example.assignment.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "externalApiUrl", "https://dummyjson.com/users");
    }

    @Test
    void testLoadUsers() {
        // Act: Call the real method
        userService.loadUsers();

        // Assert: Check expected behavior (e.g., database updates)
        verify(userRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Emily");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals("Emily", result.getFirstName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetUserByEmail() {
        User user = new User();
        user.setEmail("emily.johnson@x.dummyjson.com");

        when(userRepository.findByEmail("emily.johnson@x.dummyjson.com")).thenReturn(Optional.of(user));
        User result = userService.getUserByEmail("emily.johnson@x.dummyjson.com");
        assertNotNull(result);
        assertEquals("emily.johnson@x.dummyjson.com", result.getEmail());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail("emily.johnson@x.dummyjson.com")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail("emily.johnson@x.dummyjson.com"));
    }

}