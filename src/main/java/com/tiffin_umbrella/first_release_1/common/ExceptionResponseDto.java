package com.tiffin_umbrella.first_release_1.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDto {
    public Long status;
    public String timestamp;
    public String errorCode;
    public String explanation;
    public String detailedErrorMessage;
}
