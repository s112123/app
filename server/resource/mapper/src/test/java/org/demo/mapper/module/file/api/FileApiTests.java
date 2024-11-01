package org.demo.mapper.module.file.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.demo.mapper.module.file.request.FileRequest;
import org.demo.mapper.module.file.response.FileInformation;
import org.demo.mapper.module.file.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class FileApiTests {

    @Value("${upload.directory.root}")
    private String uploadRootDirPath;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FileUtils fileUtils;

    @Test
    @DisplayName("Upload File Success")
    void uploadFileSuccess() throws Exception {
        // Mock 파일 생성을 위해 MockMultipartFile 이 필요하다
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "lenna.png",
                MediaType.IMAGE_PNG_VALUE,
                "mock file content".getBytes()
        );

        // API 검증
        MvcResult result = mockMvc.perform(multipart("/api/files")
                        .file(mockFile)
                        .param("directory", "profiles"))
                .andExpect(status().isOk())
                .andReturn();

        // 응답 정보를 받는다
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        FileInformation fileInformation = objectMapper.readValue(responseBody, FileInformation.class);

        // 업로드된 파일 검증
        Path filePath = Paths.get(uploadRootDirPath, "profiles", fileInformation.getUploadFileName());
        assert Files.exists(filePath);

        // 파일 삭제
        Files.deleteIfExists(filePath);
    }

    @Test
    @DisplayName("Find Upload File Success")
    void findFileSuccess() throws Exception {
        // Mock 파일 생성을 위해 MockMultipartFile 이 필요하다
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "lenna.png",
                MediaType.IMAGE_PNG_VALUE,
                "mock file content".getBytes()
        );

        // 파일 저장
        String directory = "profiles";
        FileRequest fileRequest = new FileRequest(directory, mockFile);
        FileInformation fileInformation = fileUtils.uploadFile(fileRequest);

        // API 검증
        // "/" 를 File.separator 로 하면 경로를 못 읽고 404 에러를 발생시킬 수 있다
        mockMvc.perform(get("/api/files/" + directory + "/" + fileInformation.getUploadFileName())
                        .param("directory", directory)
                        .param("fileName", fileInformation.getUploadFileName()))
                .andExpect(status().isOk())
                .andReturn();

        // 업로드된 파일 검증
        Path filePath = Paths.get(uploadRootDirPath, directory, fileInformation.getUploadFileName());
        assert Files.exists(filePath);

        // 파일 삭제
        Files.deleteIfExists(filePath);
    }
}