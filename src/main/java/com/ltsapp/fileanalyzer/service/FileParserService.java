package com.ltsapp.fileanalyzer.service;

import com.ltsapp.fileanalyzer.exception.FileParsingException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileParserService {

    public String parse(MultipartFile sourceFile, int startPage, int endPage) {
        String originalName = sourceFile.getOriginalFilename();
        File file = new File("src/main/resources/" + originalName + ".pdf");
        try {
            sourceFile.transferTo(file.toPath());
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String text = pdfTextStripper.getText(document);
            return text;
        } catch (IOException e) {
            throw new FileParsingException("Process of parsing have not been done");
        }
    }

}
