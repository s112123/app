package org.demo.mapper.module.member.exception;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException(String message) {
        super(message);
    }
}
