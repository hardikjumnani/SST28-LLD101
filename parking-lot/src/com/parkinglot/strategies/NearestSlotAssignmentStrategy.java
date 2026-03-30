package com.parkinglot.strategies;

import com.parkinglot.models.ParkingSlot;
import com.parkinglot.models.SlotType;
import com.parkinglot.models.VehicleType;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// Assigns nearest slot, prioritizing requested type fallback
public class NearestSlotAssignmentStrategy implements SlotAssignmentStrategy {

    @Override
    public Optional<ParkingSlot> assignSlot(VehicleType vehicleType, SlotType requestedType, String gateId, List<ParkingSlot> slots) {
        return slots.stream()
                .filter(slot -> !slot.isOccupied())
                .filter(slot -> isCompatible(vehicleType, slot.getType()))
                .min(Comparator
                        .<ParkingSlot, Integer>comparing(slot -> slot.getType() == requestedType ? 0 : 1)
                        .thenComparing(slot -> slot.getDistanceToGate(gateId)));
    }

    private boolean isCompatible(VehicleType vType, SlotType sType) {
        switch (vType) {
            case TWO_WHEELER:
                return true; // bike can park anywhere
            case CAR:
                return sType == SlotType.MEDIUM || sType == SlotType.LARGE;
            case BUS:
                return sType == SlotType.LARGE;
            default:
                return false;
        }
    }
}
