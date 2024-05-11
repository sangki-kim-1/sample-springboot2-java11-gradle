package com.example.samplespringboot2javagradle.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import java.io.IOException;
import java.nio.ByteBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 *
 * <h3>Global Controller Advice</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(FeignException e) {
        log.error("Feign exception occurred", e);
        var httpCode = HttpStatus.valueOf(e.status());
        var responseByteBuffer = e.responseBody().orElseGet(() -> ByteBuffer.wrap(new byte[0]));
        var responseBody = deSerializer(responseByteBuffer);
        var ow = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            var loggingMessage =
                    String.format(
                            "status: %d(%s). body: %s",
                            httpCode.value(), httpCode.name(), ow.writeValueAsString(responseBody));
            log.error(loggingMessage);
        } catch (JsonProcessingException e2) {
            log.error("ResponseBody logging failed!", e2);
        }
        return ResponseEntity.status(e.status()).body(e.getMessage());
    }

    private Object deSerializer(ByteBuffer byteBuffer) {
        var bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        try {
            return objectMapper.readValue(bytes, Object.class);
        } catch (IOException e) {
            log.error("Failed to deserialize object. return null.", e);
        }
        return null;
    }
}
