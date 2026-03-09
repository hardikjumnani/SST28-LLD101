public class CodeGrader implements Grader {
    @Override
    public int grade(Submission s, Rubric r) {
        // fake scoring (but deterministic)
        return 78;
    }
}
