package com.tiffin_umbrella.first_release_1.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

@Slf4j
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public String explanation;

    public BadRequestException(final ErrorCode code) {
        super(code.name());
    }

    public BadRequestException(final ErrorCode code, final String explanation) {
        super(code.name());
        this.explanation = explanation;
    }

    public static void throwException(final ErrorCode code) {
        log.error("ERROR: BAD_REQUEST {} handled on {}", code, Instant.now().toEpochMilli());
        throw new BadRequestException(code, null);
    }

    public static void throwException(final ErrorCode code, final String explanation) {
        log.error("ERROR: BAD_REQUEST {} handled on {}", code, Instant.now().toEpochMilli());
        throw new BadRequestException(code, explanation);
    }
}