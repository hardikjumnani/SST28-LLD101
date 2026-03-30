package com.parkinglot.models;

import java.time.LocalDateTime;

// holds assigned ticket info
public class Ticket {
    private String id;
    private Vehicle vehicle;
    private ParkingSlot allocatedSlot;
    private LocalDateTime entryTime;
    
    public Ticket(String id, Vehicle vehicle, ParkingSlot slot, LocalDateTime time) {
        this.id = id;
        this.vehicle = vehicle;
        this.allocatedSlot = slot;
        this.entryTime = time;
    }
    
    public String getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSlot getAllocatedSlot() { return allocatedSlot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public SlotType getAllocatedSlotType() { return allocatedSlot.getType(); }
}
