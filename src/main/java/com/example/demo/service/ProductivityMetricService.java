public interface ProductivityMetricService {

    ProductivityMetricRecord recordMetric(ProductivityMetricRecord metric);

    ProductivityMetricRecord updateMetric(Long id, ProductivityMetricRecord updated);

    List<ProductivityMetricRecord> getMetricsByEmployee(Long employeeId);

    Optional<ProductivityMetricRecord> getMetricById(Long id);

    List<ProductivityMetricRecord> getAllMetrics();
}
