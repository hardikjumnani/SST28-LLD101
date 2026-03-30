package com.parkinglot.models;

import java.util.Map;

// parking slot and distances to gates
public class ParkingSlot {
    private String id;
    private SlotType type;
    private boolean isOccupied;
    private Map<String, Integer> distancesToGates;
    
    public ParkingSlot(String id, SlotType type, Map<String, Integer> distances) {
        this.id = id;
        this.type = type;
        this.isOccupied = false;
        this.distancesToGates = distances;
    }
    
    public String getId() { return id; }
    public SlotType getType() { return type; }
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { this.isOccupied = occupied; }
    
    // safe fallback to MAX_VALUE if distance unknown
    public int getDistanceToGate(String gateId) {
        return distancesToGates.getOrDefault(gateId, Integer.MAX_VALUE);
    }
}
