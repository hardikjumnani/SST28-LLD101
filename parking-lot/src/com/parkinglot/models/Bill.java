package com.parkinglot.models;

import java.time.LocalDateTime;

// generated final bill on exit
public class Bill {
    private String id;
    private Ticket ticket;
    private LocalDateTime exitTime;
    private double amount;
    
    public Bill(String id, Ticket ticket, LocalDateTime time, double amount) {
        this.id = id;
        this.ticket = ticket;
        this.exitTime = time;
        this.amount = amount;
    }
    
    public String getId() { return id; }
    public Ticket getTicket() { return ticket; }
    public LocalDateTime getExitTime() { return exitTime; }
    public double getAmount() { return amount; }
}
