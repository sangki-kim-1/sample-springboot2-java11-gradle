package com.example.samplespringboot2javagradle.exception;

/**
 *
 *
 * <h3>BaseRuntimeException</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
public class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
