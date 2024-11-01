package org.demo.mapper.module.file.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "존재하지 않는 디렉토리")
public class NotFoundDirectoryException extends RuntimeException {
}
