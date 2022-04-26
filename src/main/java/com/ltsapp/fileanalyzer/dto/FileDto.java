package com.ltsapp.fileanalyzer.dto;

import com.ltsapp.fileanalyzer.domain.File;

public record FileDto(
        String uri,
        String key
) {

    public FileDto(File file) {
        this(file.getUri(), file.getKey());
    }

}
