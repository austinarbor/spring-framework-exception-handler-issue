package dev.aga.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UploadControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Test
    void testUploadFile() {
        var origFile = new MockMultipartFile("file", "name.csv", "text/csv", new byte[]{});
        var spyFile = Mockito.spy(origFile);
        try {
            doThrow(new IOException("Unit Test")).when(spyFile).getInputStream();
            mvc.perform(
                    multipart("/api/upload").file(spyFile)
            ).andExpect(status().isInternalServerError());

        } catch (Exception e) {
            fail(e);
        }
    }
}
