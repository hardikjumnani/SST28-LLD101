package com.parkinglot.strategies;

import com.parkinglot.models.ParkingSlot;
import com.parkinglot.models.SlotType;
import com.parkinglot.models.VehicleType;
import java.util.List;
import java.util.Optional;

// Strategy to abstract how we assign a slot
public interface SlotAssignmentStrategy {
    Optional<ParkingSlot> assignSlot(VehicleType vehicleType, SlotType requestedType, String gateId, List<ParkingSlot> slots);
}
