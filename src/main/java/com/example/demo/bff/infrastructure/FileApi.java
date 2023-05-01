package com.example.demo.bff.infrastructure;

import com.example.demo.bff.presentations.MyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public class FileApi {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FileApi(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder
                .rootUri("http://localhost:8080")
                .build();
        this.objectMapper = objectMapper;
    }


    public String send(@NonNull MyRequest request, @NonNull MultipartFile file) throws IOException {
        
        //multipart
        MultiValueMap<String, Object> formMap = new LinkedMultiValueMap<>();
        //file-part
        var fileResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        formMap.add("file", fileResource);

        //json-part
        var requestJsonPart = objectMapper.writeValueAsString(request);
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        formMap.add("jsonValue", new HttpEntity<>(requestJsonPart, jsonHeaders));

        //body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        var body = new HttpEntity<>(formMap, headers);

        var response = restTemplate.postForEntity("/word-list", body, String.class);

        return response.getBody();

    }

}
