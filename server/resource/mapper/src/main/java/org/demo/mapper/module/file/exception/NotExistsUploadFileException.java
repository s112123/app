package org.demo.mapper.module.file.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "첨부된 파일 없음")
public class NotExistsUploadFileException extends RuntimeException {
}
