package com.github.wenhao.domain.enumeration;

public enum ErrorCode {
    FIELD_VALIDATION_ERROR(400000);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
