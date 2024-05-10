package com.example.samplespringboot2javagradle.exception.prometheus;

import com.example.samplespringboot2javagradle.exception.BaseRuntimeException;
import feign.FeignException;
import lombok.Getter;

/**
 *
 *
 * <h3>Prometheus API Exception</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Getter
public class PrometheusApiException extends BaseRuntimeException {

    public PrometheusApiException(FeignException e) {
        super(e.getMessage(), e);
    }
}
