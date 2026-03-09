public interface EligibilityRule {
    // returns null when rule passes, or a reason string when it fails
    String check(StudentProfile s);
}
