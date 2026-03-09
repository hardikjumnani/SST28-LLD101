public class AttendanceRule implements EligibilityRule {
    private final int min;
    public AttendanceRule(int min) { this.min = min; }

    @Override
    public String check(StudentProfile s) {
        if (s.attendancePct < min) return "attendance below " + min;
        return null;
    }
}
