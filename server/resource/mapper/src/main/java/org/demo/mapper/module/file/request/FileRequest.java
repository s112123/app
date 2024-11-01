package org.demo.mapper.module.file.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class FileRequest {

    private String directory;
    private MultipartFile file;
}
