package com.ltsapp.fileanalyzer.dto;

import com.ltsapp.fileanalyzer.domain.User;

public record AddUserResponseDto(
        String name,
        String email) {

    public AddUserResponseDto(User product) {
        this(
                product.getName(),
                product.getEmail()
        );
    }

}
