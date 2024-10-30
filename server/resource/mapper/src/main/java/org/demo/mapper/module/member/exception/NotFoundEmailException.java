package org.demo.mapper.module.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "존재하지 않는 이메일")
public class NotFoundEmailException extends RuntimeException {
}
