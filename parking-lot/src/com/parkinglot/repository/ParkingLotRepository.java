package com.parkinglot.repository;

import com.parkinglot.models.ParkingSlot;
import com.parkinglot.models.Ticket;
import com.parkinglot.models.Bill;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// in-memory data store for the parking lot
public class ParkingLotRepository {
    private Map<String, ParkingSlot> slots = new HashMap<>();
    private Map<String, Ticket> activeTickets = new HashMap<>();
    private List<Bill> bills = new ArrayList<>();

    public void addSlot(ParkingSlot slot) { slots.put(slot.getId(), slot); }
    public List<ParkingSlot> getAllSlots() { return new ArrayList<>(slots.values()); }
    
    public void saveTicket(Ticket ticket) { activeTickets.put(ticket.getId(), ticket); }
    public Ticket getTicket(String ticketId) { return activeTickets.get(ticketId); }
    public void removeTicket(String ticketId) { activeTickets.remove(ticketId); }
    
    public void saveBill(Bill bill) { bills.add(bill); }
}
