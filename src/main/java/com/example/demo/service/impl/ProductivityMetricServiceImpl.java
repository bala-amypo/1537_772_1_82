@Service
@Transactional
public class ProductivityMetricServiceImpl implements ProductivityMetricService {

    private final ProductivityMetricRecordRepository metricRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final AnomalyRuleRepository ruleRepo;
    private final AnomalyFlagRecordRepository flagRepo;

    public ProductivityMetricServiceImpl(
            ProductivityMetricRecordRepository metricRepo,
            EmployeeProfileRepository employeeRepo,
            AnomalyRuleRepository ruleRepo,
            AnomalyFlagRecordRepository flagRepo) {

        this.metricRepo = metricRepo;
        this.employeeRepo = employeeRepo;
        this.ruleRepo = ruleRepo;
        this.flagRepo = flagRepo;
    }

    @Override
    public ProductivityMetricRecord recordMetric(ProductivityMetricRecord metric) {

        Long empId = metric.getEmployeeId();

        EmployeeProfile employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (!employee.getActive()) {
            throw new IllegalStateException("Employee not active");
        }

        boolean exists = metricRepo
                .findByEmployeeId(empId)
                .stream()
                .anyMatch(m -> m.getDate().equals(metric.getDate()));

        if (exists) {
            throw new IllegalStateException("Metric already exists");
        }

        double score = ProductivityCalculator.computeScore(
                metric.getHoursLogged(),
                metric.getTasksCompleted(),
                metric.getMeetingsAttended()
        );

        metric.setProductivityScore(score);
        metric.setSubmittedAt(LocalDateTime.now());

        ProductivityMetricRecord saved = metricRepo.save(metric);

        ruleRepo.findByActiveTrue().forEach(rule -> {
            if (saved.getProductivityScore() < rule.getThresholdValue()) {
                AnomalyFlagRecord flag = new AnomalyFlagRecord();
                flag.setEmployeeId(empId);
                flag.setMetricId(saved.getId());
                flag.setRuleCode(rule.getRuleCode());
                flag.setSeverity("LOW");
                flag.setResolved(false);
                flag.setFlaggedAt(LocalDateTime.now());
                flagRepo.save(flag);
            }
        });

        return saved;
    }

    @Override
    public ProductivityMetricRecord updateMetric(Long id, ProductivityMetricRecord updated) {
        ProductivityMetricRecord existing = metricRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Metric not found"));

        existing.setHoursLogged(updated.getHoursLogged());
        existing.setTasksCompleted(updated.getTasksCompleted());
        existing.setMeetingsAttended(updated.getMeetingsAttended());

        double score = ProductivityCalculator.computeScore(
                existing.getHoursLogged(),
                existing.getTasksCompleted(),
                existing.getMeetingsAttended()
        );

        existing.setProductivityScore(score);

        return metricRepo.save(existing);
    }

    @Override
    public List<ProductivityMetricRecord> getMetricsByEmployee(Long employeeId) {
        return metricRepo.findByEmployeeId(employeeId);
    }

    @Override
    public Optional<ProductivityMetricRecord> getMetricById(Long id) {
        return metricRepo.findById(id);
    }

    @Override
    public List<ProductivityMetricRecord> getAllMetrics() {
        return metricRepo.findAll();
    }
}
