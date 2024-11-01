package org.demo.mapper.module.file.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileInformation {

    private String filePath;
    private String originalFileName;
    private String uploadFileName;
    private String extension;
}
