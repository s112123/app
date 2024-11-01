package org.demo.mapper.module.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "중복된 이메일")
public class DuplicatedEmailException extends RuntimeException {
}
