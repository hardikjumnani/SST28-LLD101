public interface PaymentProcessor {
    String charge(String studentId, double amount);
}
