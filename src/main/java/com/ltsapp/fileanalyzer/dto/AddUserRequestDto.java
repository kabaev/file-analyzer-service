package com.ltsapp.fileanalyzer.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// TODO fix validation
public record AddUserRequestDto(
        @Size(min = 10, max = 100)
        @NotNull
        String name,
        @Size(min = 10, max = 1000)
        @NotNull
        String email) {
}
