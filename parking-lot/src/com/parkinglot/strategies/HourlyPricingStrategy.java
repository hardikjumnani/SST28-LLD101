package com.parkinglot.strategies;

import com.parkinglot.models.SlotType;
import java.time.Duration;
import java.time.LocalDateTime;

// Base hourly pricing based on assigned slot
public class HourlyPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(SlotType allocatedSlotType, LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = Duration.between(entryTime, exitTime).toMinutes();
        long hours = (long) Math.ceil(minutes / 60.0);
        if (hours == 0) hours = 1; // minimum 1 hour charge
        
        return hours * getRate(allocatedSlotType);
    }

    private double getRate(SlotType type) {
        switch (type) {
            case SMALL: return 10.0;
            case MEDIUM: return 20.0;
            case LARGE: return 50.0;
            default: return 0.0;
        }
    }
}
