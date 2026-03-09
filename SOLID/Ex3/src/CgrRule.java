public class CgrRule implements EligibilityRule {
    private final double min;
    public CgrRule(double min) { this.min = min; }

    @Override
    public String check(StudentProfile s) {
        if (s.cgr < min) return "CGR below " + String.format("%.1f", min);
        return null;
    }
}
