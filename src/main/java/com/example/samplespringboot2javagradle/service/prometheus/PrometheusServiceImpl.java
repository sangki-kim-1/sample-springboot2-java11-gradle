package com.example.samplespringboot2javagradle.service.prometheus;

import com.example.samplespringboot2javagradle.adapter.prometheus.PrometheusApiAdapter;
import com.example.samplespringboot2javagradle.consts.prometheus.StepUnit;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 *
 * <h3>Prometheus service impl</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PrometheusServiceImpl implements PrometheusService {

    private final PrometheusApiAdapter prometheusApiAdapter;

    private final String start = Instant.now().minus(7, ChronoUnit.DAYS).toString();
    private final String end = Instant.now().toString();
    private final String step = String.format("%d%s", 1, StepUnit.MINUTE.getUnit());

    @Override
    public Object getCpuUsage() {
        var namespace = "apppaas";
        var container = "backoffice-be";
        var query =
                String.format(
                        "irate(container_cpu_usage_seconds_total{namespace=\"%s\", container=\"%s\"}[5m])",
                        namespace, container);
        return prometheusApiAdapter.queryRange(query, start, end, step);
    }

    @Override
    public Object getMemoryUsage() {
        var namespace = "apppaas";
        var container = "backoffice-be";
        var query =
                String.format(
                        "max_over_time(container_memory_working_set_bytes{namespace=\"%s\", container=\"%s\"}[5m])",
                        namespace, container);
        return prometheusApiAdapter.queryRange(query, start, end, step);
    }

    @Override
    public Object getNetworkIngress() {
        var namespace = "50203-c1eeb2";
        var podLike = "a82e94907805ce825825d709c83773c22.+";
        var query =
                String.format(
                        "irate(container_network_receive_bytes_total{namespace=\"%s\", pod=~\"%s\"}[5m])",
                        namespace, podLike);
        return prometheusApiAdapter.queryRange(query, start, end, step);
    }

    @Override
    public Object getNetworkEgress() {
        return null;
    }

    @Override
    public Object getAutoscaleInfo() {
        var namespace = "apppaas";
        var horizontalpodautoscaler = "backoffice-be";
        var query =
                String.format(
                        "kube_horizontalpodautoscaler_info{namespace=\"%s\",horizontalpodautoscaler=\"%s\"}",
                        namespace, horizontalpodautoscaler);
        return prometheusApiAdapter.queryRange(query, start, end, step);
    }

    @Override
    public Object getHttpRequestCount() {
        var uri = "/api/members/v1";
        var query = String.format("http_server_requests_seconds_count{uri=\"%s\"}", uri);
        return prometheusApiAdapter.queryRange(query, start, end, step);
    }
}
