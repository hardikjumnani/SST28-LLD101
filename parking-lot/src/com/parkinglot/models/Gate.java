package com.parkinglot.models;

// represents an entry or exit gate
public class Gate {
    private String id;
    
    public Gate(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
}
