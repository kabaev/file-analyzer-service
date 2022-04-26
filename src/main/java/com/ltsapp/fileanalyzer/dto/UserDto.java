package com.ltsapp.fileanalyzer.dto;

import com.ltsapp.fileanalyzer.domain.User;

import java.util.List;

public record UserDto(
        String name,
        String email,
        List<String> files) {
    public UserDto(User user) {
        this(
                user.getName(),
                user.getEmail(),
                user.getFileUriList()
        );
    }

}
