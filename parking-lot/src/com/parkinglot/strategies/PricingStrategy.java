package com.parkinglot.strategies;

import com.parkinglot.models.SlotType;
import java.time.LocalDateTime;

// Strategy for abstracting final pricing logic
public interface PricingStrategy {
    double calculatePrice(SlotType allocatedSlotType, LocalDateTime entryTime, LocalDateTime exitTime);
}
