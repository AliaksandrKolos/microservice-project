package com.kolos.resourceprocessor.service.exception;

public class MetadataProcessingException extends ApplicationException {

    public MetadataProcessingException() {
        super();
    }

    public MetadataProcessingException(String message) {
        super(message);
    }

    public MetadataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetadataProcessingException(Throwable cause) {
        super(cause);
    }

    protected MetadataProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
