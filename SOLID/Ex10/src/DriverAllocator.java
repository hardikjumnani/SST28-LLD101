public class DriverAllocator implements DriverAllocatorInterface {
    @Override
    public String allocate(String studentId) {
        // fake deterministic driver
        return "DRV-17";
    }
}
