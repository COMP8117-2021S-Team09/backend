package com.tiffin_umbrella.first_release_1.common;

public enum ErrorCode {
    /* validation related */
    REST_API_EXCEPTION,
    REQUEST_VALIDATION_EXCEPTION,

    /* seller related */
    SELLER_NOT_FOUND_BY_ID,
    SELLER_ALREADY_EXISTS_BY_EMAIL,

    /* image related */
    IMAGE_NOT_FOUND_BY_ID,
    IMAGE_SIZE_EXCEEDS_ALLOWED_LIMIT_IN_MB,
    IMAGE_SIZE_EXCEEDS_ALLOWED_DIMENSIONS,

    /* plan related */
    PLAN_NOT_FOUND_BY_ID
}