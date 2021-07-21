package com.tiffin_umbrella.first_release_1.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;

@ControllerAdvice
public class ServiceExceptionHandler {

    private static final String LINE_SEPARATOR = "line.separator";
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServiceErrorResponseDto> handleBadRequestException(
            final BadRequestException ex) {
        LOGGER.error("BadRequestException: {}", ex.getMessage());
        final ServiceErrorResponseDto beckerServiceErrorResponseDto = ServiceErrorResponseDto.builder()
                .timestamp(Instant.now())
                .status(400L)
                .errorCode(ErrorCode.valueOf(ex.getMessage()))
                .explanation(ex.explanation)
                .detailedErrorMessage(getPartialStackTrace(ex))
                .build();
        return new ResponseEntity<>(beckerServiceErrorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingPathVariableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServiceErrorResponseDto> handleMissingPathVariableException(
            final MissingPathVariableException ex) {
        LOGGER.error("MissingPathVariableException");
        final ServiceErrorResponseDto serviceErrorResponseDto = ServiceErrorResponseDto
                .builder()
                .timestamp(Instant.now())
                .status(400L)
                .errorCode(ErrorCode.REQUEST_VALIDATION_EXCEPTION)
                .detailedErrorMessage(getPartialStackTrace(ex))
                .build();
        return new ResponseEntity<>(serviceErrorResponseDto, HttpStatus.BAD_REQUEST);
    }

    private String getPartialStackTrace(final Throwable e) {
        final StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        final String[] lines = writer.toString().split(System.getProperty(LINE_SEPARATOR));
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(lines.length, 5); i++) {
            sb.append(lines[i]).append(System.getProperty(LINE_SEPARATOR));
        }
        return sb.toString();
    }
}