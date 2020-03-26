package com.qati.cats.data.source.api.errors;

public class ServerError {
    int code;
    int error_code;
    String message;

    public ServerError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getErrorCode() {
        return error_code;
    }

    public void setErrorCode(int errorCode) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
