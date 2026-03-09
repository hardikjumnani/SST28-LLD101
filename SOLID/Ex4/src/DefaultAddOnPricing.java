public class DefaultAddOnPricing implements AddOnPricing {
    @Override
    public double priceFor(AddOn a) {
        return switch (a) {
            case MESS -> 1000.0;
            case LAUNDRY -> 500.0;
            case GYM -> 300.0;
            default -> 0.0;
        };
    }
}
