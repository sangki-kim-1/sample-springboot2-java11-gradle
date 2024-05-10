package com.example.samplespringboot2javagradle.controller.prometheus;

import com.example.samplespringboot2javagradle.service.prometheus.PrometheusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * <h3>Prometheus controller</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@RequestMapping("api/prometheus/v1")
@RestController
@RequiredArgsConstructor
public class PrometheusRestController {

    private final PrometheusService prometheusService;

    @GetMapping("/autoscale-info")
    public Object getAutoscaleInfo() {
        return prometheusService.getAutoscaleInfo();
    }

    @GetMapping("/cpu-usage")
    public Object getCpuUsage() {
        return prometheusService.getCpuUsage();
    }

    @GetMapping("/memory-usage")
    public Object getMemoryUsage() {
        return prometheusService.getMemoryUsage();
    }
}
