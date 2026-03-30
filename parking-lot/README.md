# Multilevel Parking Lot System

A Java-based Low-Level Design (LLD) implementation of a Multilevel Parking Lot system that I built to solve this problem statement.

## My Design and Approach

I designed this system with a strong focus on maintainability, scalability, and adherence to object-oriented SOLID principles. 

### Key Concepts & Design Patterns

1.  **SOLID Principles:**
    *   **Single Responsibility Principle (SRP):** I explicitly separated concerns into distinct classes. My core business orchestrations reside in `ParkingLotService`, data management lives in `ParkingLotRepository`, while the complex algorithms have their own strategy classes.
    *   **Open/Closed Principle (OCP):** I made sure the system easily scales without modifying core logic. I can introduce dynamic pricing or new VIP slot assignment algorithms by simply adding new strategy implementations.
    *   **Dependency Inversion Principle (DIP):** The main service `ParkingLotService` injects behavioral abstractions (`PricingStrategy`, `SlotAssignmentStrategy`) instead of coupling tightly to concrete logic.

2.  **Design Patterns Used:**
    *   **Strategy Pattern:** 
        *   `SlotAssignmentStrategy`: Dictates how a slot is acquired. My `NearestSlotAssignmentStrategy` specifically searches for the exact `requestedSlotType` first. If all preferred slots are occupied, the algorithm gracefully falls back to looking for the nearest available, compatible larger slot.
        *   `PricingStrategy`: Abstracts billing mechanics. My `HourlyPricingStrategy` ensures billing relies on the *allocated* slot type rate rather than the raw vehicle type (which is essential for when I park a BIKE in a LARGE slot).
    *   **Repository Pattern:** `ParkingLotRepository` acts as my in-memory data store. It mocks the behavior of a genuine database, decoupling storage logic from my application logic.

3.  **Core Functional Flows:**
    *   **Park:** When a vehicle enters, I take its vehicle type, preferred slot size, and entry gate ID. My engine evaluates compatibility (e.g., Cars cannot park in Small slots), finds the nearest valid fallback option slot, reserves it, and issues a standard `Ticket`.
    *   **Status:** A simple extraction of unoccupied `ParkingSlot` objects, grouped mathematically by `SlotType`.
    *   **Exit:** Using the `Ticket` ID, the software fetches entry times and the attached `ParkingSlot`. It unbinds the occupation status from the slot, processes duration math, creates a final `Bill`, and safely deletes the active ticket.

## Class Diagram

```mermaid
classDiagram
    class ParkingLotService {
        -ParkingLotRepository repository
        -SlotAssignmentStrategy assignmentStrategy
        -PricingStrategy pricingStrategy
        +park(vehicle: Vehicle, entryTime: LocalDateTime, requestedSlotType: SlotType, entryGateID: String) Ticket
        +status() Map~SlotType, Long~
        +exit(parkingTicketId: String, exitTime: LocalDateTime) Bill
    }

    class ParkingLotRepository {
        -Map~String, ParkingSlot~ slots
        -Map~String, Ticket~ activeTickets
        -List~Bill~ bills
        +addSlot(slot: ParkingSlot)
        +getAllSlots() List~ParkingSlot~
        +saveTicket(ticket: Ticket)
        +getTicket(ticketId: String) Ticket
        +removeTicket(ticketId: String)
        +saveBill(bill: Bill)
    }

    class ParkingSlot {
        -String id
        -SlotType type
        -boolean isOccupied
        -Map~String, Integer~ distancesToGates
        +getDistanceToGate(gateId: String) Integer
        +setOccupied(occupied: boolean)
    }

    class Ticket {
        -String id
        -Vehicle vehicle
        -ParkingSlot allocatedSlot
        -LocalDateTime entryTime
    }

    class Bill {
        -String id
        -Ticket ticket
        -LocalDateTime exitTime
        -double amount
    }

    class SlotAssignmentStrategy {
        <<interface>>
        +assignSlot(vehicleType: VehicleType, requestedType: SlotType, gateId: String, slots: List~ParkingSlot~) Optional~ParkingSlot~
    }
    
    class PricingStrategy {
        <<interface>>
        +calculatePrice(allocatedSlotType: SlotType, entryTime: LocalDateTime, exitTime: LocalDateTime) double
    }
    
    class NearestSlotAssignmentStrategy {
        +assignSlot(...) Optional~ParkingSlot~
    }
    
    class HourlyPricingStrategy {
        +calculatePrice(...) double
    }

    ParkingLotService --> ParkingLotRepository
    ParkingLotService --> SlotAssignmentStrategy
    ParkingLotService --> PricingStrategy
    
    SlotAssignmentStrategy <|.. NearestSlotAssignmentStrategy
    PricingStrategy <|.. HourlyPricingStrategy
    
    ParkingLotRepository *-- ParkingSlot
    ParkingLotRepository *-- Ticket
    ParkingLotRepository *-- Bill
    
    Ticket --> ParkingSlot
    Bill --> Ticket
```

## How to Run locally

1. Navigate to the core `src` directory containing the `com` folder.
2. Compile the Java files:
   ```bash
   javac $(find . -name "*.java")
   ```
3. Run the demonstration (`Main.java`):
   ```bash
   java com.parkinglot.Main
   ```
