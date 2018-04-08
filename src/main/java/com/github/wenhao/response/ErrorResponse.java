package com.github.wenhao.response;

import org.springframework.validation.FieldError;

import java.util.List;

import static com.github.wenhao.domain.enumeration.ErrorCode.FIELD_VALIDATION_ERROR;

public class ErrorResponse {
    private int code;
    private String message;
    private List<FieldError> fieldErrors;

    public ErrorResponse(int code, List<FieldError> fieldErrors) {
        this.code = code;
        this.fieldErrors = fieldErrors;
    }

    public static ErrorResponse of(List<FieldError> fieldErrors) {
        return new ErrorResponse(FIELD_VALIDATION_ERROR.getCode(), fieldErrors);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
