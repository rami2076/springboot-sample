package com.example.demo.bff.infrastructure;

import com.example.demo.bff.presentations.MyRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RestClientTest(FileApi.class)
class FileApiTest {
    @Autowired
    private FileApi fileApi;

    @Autowired
    private MockRestServiceServer server;

    @Nested
    @DisplayName("method: send")
    class Send {

        @Test
        @DisplayName("受け取ったjsonとファイルを元にリクエストを投げて帰ってきたレスポンスをStringに返却できる。")
        void send() throws IOException {
            // Setup
            MyRequest myRequest = new MyRequest();
            myRequest.setIsUseDefaultWordList(true);

            MockMultipartFile file = new MockMultipartFile(
                    "file",
                    "file.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    "test".getBytes());

            server.expect(
                    requestTo("/word-list")).andRespond(
                    withSuccess("hello", MediaType.TEXT_PLAIN));

            // Exec
            String actual = fileApi.send(myRequest, file);

            // Verify
            Assertions.assertEquals("hello", actual);
        }
    }


}