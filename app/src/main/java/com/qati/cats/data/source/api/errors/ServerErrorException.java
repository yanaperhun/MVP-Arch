package com.qati.cats.data.source.api.errors;


public class ServerErrorException extends RuntimeException {
    private final ServerError error;

    public ServerErrorException(ServerError error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }
}
