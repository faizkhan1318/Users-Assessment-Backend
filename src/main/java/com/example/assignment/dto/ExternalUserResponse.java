package com.example.assignment.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExternalUserResponse {
    private List<ExternalUserDto> users;

    public List<ExternalUserDto> getUsers() {
        return users;
    }
}
