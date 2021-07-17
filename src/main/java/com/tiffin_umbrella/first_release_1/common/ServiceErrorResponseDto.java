package com.tiffin_umbrella.first_release_1.common;

import lombok.Builder;

import java.time.Instant;

/**
 * @author yash.sahu@app-scoop.com
 */

@Builder(toBuilder = true)
public class ServiceErrorResponseDto {
    public Long status;
    public Instant timestamp;
    public ErrorCode errorCode;
    public String explanation;
    public String detailedErrorMessage;
}