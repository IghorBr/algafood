package com.ibn.algafood.domain.exception;

public class StorageException extends AlgafoodException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
