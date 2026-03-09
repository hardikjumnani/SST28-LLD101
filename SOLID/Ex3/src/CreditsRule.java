public class CreditsRule implements EligibilityRule {
    private final int min;
    public CreditsRule(int min) { this.min = min; }

    @Override
    public String check(StudentProfile s) {
        if (s.earnedCredits < min) return "credits below " + min;
        return null;
    }
}
