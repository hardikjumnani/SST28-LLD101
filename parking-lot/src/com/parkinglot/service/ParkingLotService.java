package com.parkinglot.service;

import com.parkinglot.models.*;
import com.parkinglot.repository.ParkingLotRepository;
import com.parkinglot.strategies.PricingStrategy;
import com.parkinglot.strategies.SlotAssignmentStrategy;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

// Main orchestration service
public class ParkingLotService {
    private ParkingLotRepository repository;
    private SlotAssignmentStrategy assignmentStrategy;
    private PricingStrategy pricingStrategy;

    public ParkingLotService(ParkingLotRepository repo, SlotAssignmentStrategy assignStrategy, PricingStrategy priceStrategy) {
        this.repository = repo;
        this.assignmentStrategy = assignStrategy;
        this.pricingStrategy = priceStrategy;
    }

    public Ticket park(Vehicle vehicle, LocalDateTime entryTime, SlotType requestedSlotType, String entryGateID) {
        ParkingSlot allocatedSlot = assignmentStrategy.assignSlot(
            vehicle.getType(), 
            requestedSlotType, 
            entryGateID, 
            repository.getAllSlots()
        ).orElseThrow(() -> new RuntimeException("No compatible slot available"));

        allocatedSlot.setOccupied(true);
        String ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 8);
        Ticket ticket = new Ticket(ticketId, vehicle, allocatedSlot, entryTime);
        repository.saveTicket(ticket);

        return ticket;
    }

    public Map<SlotType, Long> status() {
        return repository.getAllSlots().stream()
                .filter(slot -> !slot.isOccupied())
                .collect(Collectors.groupingBy(ParkingSlot::getType, Collectors.counting()));
    }

    public Bill exit(String parkingTicketId, LocalDateTime exitTime) {
        Ticket ticket = repository.getTicket(parkingTicketId);
        if (ticket == null) throw new RuntimeException("Invalid ticket");

        double amount = pricingStrategy.calculatePrice(ticket.getAllocatedSlotType(), ticket.getEntryTime(), exitTime);
        
        ticket.getAllocatedSlot().setOccupied(false);
        repository.removeTicket(ticket.getId());

        Bill bill = new Bill("BILL-" + UUID.randomUUID().toString().substring(0, 8), ticket, exitTime, amount);
        repository.saveBill(bill);

        return bill;
    }
}
