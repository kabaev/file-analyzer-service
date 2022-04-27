package com.ltsapp.fileanalyzer.controller;

import com.ltsapp.fileanalyzer.exception.FileUploadException;
import com.ltsapp.fileanalyzer.service.FileParserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/files")
public class FileController {

    private final FileParserService parserService;

    public FileController(FileParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping(value = "/{userId}/file/{startPage}/{endPage}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(
            @PathVariable("userId") String userId,
            @PathVariable("startPage") int startPage,
            @PathVariable("endPage") int endPage,
            @RequestPart(name = "file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new FileUploadException("File is not included in the request");
        }

        if (!Objects.equals(file.getContentType(), "application/pdf")) {
            throw new FileUploadException("Type of the file must be pdf");
        }

        // TODO Handle SizeLimitExceedException
        return parserService.parse(file, startPage, endPage);
    }

}
