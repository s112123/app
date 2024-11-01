package org.demo.mapper.module.file.utils;

import org.demo.mapper.module.file.exception.NotExistsUploadFileException;
import org.demo.mapper.module.file.exception.NotFoundDirectoryException;
import org.demo.mapper.module.file.exception.NotFoundResourceException;
import org.demo.mapper.module.file.request.FileRequest;
import org.demo.mapper.module.file.response.FileInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {

    @Value("${upload.directory.root}")
    private String uploadRootDirPath;

    // 파일 저장
    public FileInformation uploadFile(FileRequest fileRequest) {
        // 저장할 디렉토리 이름
        String uploadDir = fileRequest.getDirectory();
        // 업로드 파일
        MultipartFile uploadFile = fileRequest.getFile();

        // 업로드 폴더 생성
        createUploadDir(uploadDir);
        // 업로드 파일 첨부 유무
        validateExistsUploadFile(uploadFile);

        // 파일 저장
        return saveFile(uploadDir, uploadFile);
    }

    // 파일 조회
    public Resource findFile(String directory, String fileName) {
        Resource resource = new FileSystemResource(uploadRootDirPath + directory + File.separator + fileName);
        if (!resource.exists()) {
            throw new NotFoundResourceException();
        }
        return resource;
    }

    // 파일을 서버 저장소에 저장
    private FileInformation saveFile(String uploadDir, MultipartFile uploadFile) {
        // 업로드 이름 생성
        String originalFileName = uploadFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID() + "." + extension;

        // 업로드 파일 경로
        String filePath = uploadRootDirPath + uploadDir + File.separator + uploadFileName;

        // 파일 저장
        try {
            uploadFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 저장된 파일 정보 반환
        return new FileInformation(filePath, originalFileName, uploadFileName, extension);
    }

    // 업로드 파일 첨부 유무
    private void validateExistsUploadFile(MultipartFile uploadFile) {
        if (uploadFile.isEmpty()) {
            throw new NotExistsUploadFileException();
        }
    }

    // 업로드 폴더 생성
    private void createUploadDir(String uploadDirectory) {
        // uploadDirectory 파일 이름이 전달되지 않았거나 지정된 폴더 이외 다른 이름인 경우
        if (uploadDirectory.isEmpty() || !isCollectDirectory(uploadDirectory)) {
            throw new NotFoundDirectoryException();
        }

        File uploadDir = new File(uploadRootDirPath + uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    // 업로드 폴더 확인
    public boolean isCollectDirectory(String uploadDirectory) {
        List<String> directories = List.of("profiles", "videos", "thumbnails");
        return directories.contains(uploadDirectory);
    }
}
