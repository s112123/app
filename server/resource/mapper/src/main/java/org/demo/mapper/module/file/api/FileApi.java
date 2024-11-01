package org.demo.mapper.module.file.api;

import lombok.RequiredArgsConstructor;
import org.demo.mapper.module.file.exception.NotFoundDirectoryException;
import org.demo.mapper.module.file.request.FileRequest;
import org.demo.mapper.module.file.response.FileInformation;
import org.demo.mapper.module.file.utils.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileApi {

    private final FileUtils fileUtils;

    // 파일 저장
    @PostMapping
    public ResponseEntity<FileInformation> uploadFile(@ModelAttribute FileRequest fileRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileUtils.uploadFile(fileRequest));
    }

    // 파일 조회
    @GetMapping("/{directory}/{fileName}")
    public ResponseEntity<Resource> findFile(
            @PathVariable("directory") String directory, @PathVariable("fileName") String fileName
    ) throws IOException {
        // 업로드 폴더가 올바른 폴더인지 확인
        if (!fileUtils.isCollectDirectory(directory)) {
            throw new NotFoundDirectoryException();
        }
        // 파일 조회
        Resource resource = fileUtils.findFile(directory, fileName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFile().toPath())
                .body(resource);
    }
}
