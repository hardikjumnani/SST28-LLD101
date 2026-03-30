package com.parkinglot;

import com.parkinglot.models.*;
import com.parkinglot.repository.ParkingLotRepository;
import com.parkinglot.service.ParkingLotService;
import com.parkinglot.strategies.*;

import java.time.LocalDateTime;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ParkingLotRepository repo = new ParkingLotRepository();
        
        // initialize slots with gate distances
        repo.addSlot(new ParkingSlot("S1", SlotType.SMALL, Map.of("G1", 10, "G2", 50)));
        repo.addSlot(new ParkingSlot("S2", SlotType.SMALL, Map.of("G1", 20, "G2", 40)));
        repo.addSlot(new ParkingSlot("M1", SlotType.MEDIUM, Map.of("G1", 30, "G2", 30)));
        repo.addSlot(new ParkingSlot("L1", SlotType.LARGE, Map.of("G1", 5, "G2", 60)));
        
        ParkingLotService service = new ParkingLotService(
            repo, new NearestSlotAssignmentStrategy(), new HourlyPricingStrategy()
        );

        System.out.println("Initial Status: " + service.status());

        // 1. Two wheeler parks at G1, requests SMALL
        Vehicle bike1 = new Vehicle("AP-01", VehicleType.TWO_WHEELER);
        Ticket t1 = service.park(bike1, LocalDateTime.now(), SlotType.SMALL, "G1");
        System.out.println("Bike1 allocated: " + t1.getAllocatedSlot().getId()); // Expect S1 (closer to G1)

        // 2. Car parks at G2, requests MEDIUM
        Vehicle car1 = new Vehicle("AP-02", VehicleType.CAR);
        Ticket t2 = service.park(car1, LocalDateTime.now().plusHours(1), SlotType.MEDIUM, "G2");
        System.out.println("Car1 allocated: " + t2.getAllocatedSlot().getId()); // Expect M1

        // 3. Status after initial
        System.out.println("Status after parking: " + service.status());
        
        // 4. Bike2 parks at G1, fills remaining SMALL slot (S2)
        Vehicle bike2 = new Vehicle("AP-03", VehicleType.TWO_WHEELER);
        service.park(bike2, LocalDateTime.now(), SlotType.SMALL, "G1");
        
        // 5. Bike3 requests SMALL at G1, but SMALL is full. 
        // Falls back to another type (LARGE L1 is closer to G1 than MEDIUM M1)
        Vehicle bike3 = new Vehicle("AP-04", VehicleType.TWO_WHEELER);
        Ticket t3 = service.park(bike3, LocalDateTime.now(), SlotType.SMALL, "G1");
        System.out.println("Bike3 allocated (fallback): " + t3.getAllocatedSlot().getId() + " - " + t3.getAllocatedSlotType());
        
        // 6. Exit calculate
        Bill bill1 = service.exit(t1.getId(), t1.getEntryTime().plusMinutes(65)); // 2 hours
        System.out.println("Bike1 (S1) bill amount: " + bill1.getAmount()); // 2 * 10 = 20.0
        
        Bill bill3 = service.exit(t3.getId(), t3.getEntryTime().plusMinutes(120)); // 2 hours
        System.out.println("Bike3 (L1 fallback) bill amount: " + bill3.getAmount()); // 2 * 50 = 100.0 (billed based on assigned slot)

        System.out.println("Final Status: " + service.status());
    }
}
