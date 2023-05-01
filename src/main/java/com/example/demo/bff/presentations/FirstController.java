package com.example.demo.bff.presentations;

import com.example.demo.bff.infrastructure.FileApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FirstController {

    private final FileApi fileApi;

    @PostMapping(value = "/upload")
    public String upload(@RequestPart("jsonValue") MyRequest request, @RequestPart("file") MultipartFile file) throws IOException {
        return fileApi.send(request, file);
    }
}
