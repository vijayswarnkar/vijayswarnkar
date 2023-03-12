package com.example;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
abstract class Vehicle {
    String number;
    VehicleType vehicleType;
}

class Bike extends Vehicle {
    Bike(String number) {
        super(number, VehicleType.BIKE);
    }
}

class Car extends Vehicle {
    Car(String number) {
        super(number, VehicleType.CAR);
    }
}

class Truck extends Vehicle {
    Truck(String number) {
        super(number, VehicleType.TRUCK);
    }
}

@AllArgsConstructor
enum VehicleType {
    BIKE(1),
    CAR(2),
    TRUCK(3);

    int level;
}

@AllArgsConstructor
@Data
class Ticket {
    String number;
    Date date;
    Vehicle vehicle;
    Spot spot;
    int id;
    TicketStatus ticketStatus;
}
enum TicketStatus {
    ENTERED,
    PARKED,
    EXITED,
    PAID
}
@AllArgsConstructor
@Data
class Bill {
    double amount;
    Date enterTime;
    Date exitTime;
}

interface SpotInterface {
    boolean isEmpty();

    boolean park(Vehicle vehicle);

    boolean release(Vehicle vehicle);
}

@Data
abstract class Spot implements SpotInterface {
    SpotStatus spotStatus;
    SpotType spotType;
    Vehicle vehicle;

    Spot(SpotType spotType) {
        this.spotType = spotType;
        this.spotStatus = SpotStatus.FREE;
    }

    @Override
    public boolean isEmpty() {
        return spotStatus.equals(SpotStatus.FREE);
    }

    static List<SpotType> getOrderedSpot() {
        return List.of(
                SpotType.TWO_WHEELER_SPOT,
                SpotType.FOUR_WHEELER_SPOT,
                SpotType.HEAVY_VEHICLE_SPOT);
    }

    @Override
    public boolean park(Vehicle vehicle) {
        if (spotStatus.equals(SpotStatus.FREE)) {
            spotStatus = SpotStatus.OCCUPIED;
            this.vehicle = vehicle;
            return true;
        }
        return false;
    }

    @Override
    public boolean release(Vehicle vehicle) {
        if (spotStatus.equals(SpotStatus.OCCUPIED)) {
            spotStatus = SpotStatus.FREE;
            this.vehicle = null;
            return true;
        }
        return false;
    }
}

enum SpotStatus {
    FREE,
    OCCUPIED,
    MAINTAINENCE
}

@AllArgsConstructor
enum SpotType {
    TWO_WHEELER_SPOT(1),
    FOUR_WHEELER_SPOT(2),
    HEAVY_VEHICLE_SPOT(3);

    int level;
}

class BikeSpot extends Spot {
    BikeSpot() {
        super(SpotType.TWO_WHEELER_SPOT);
    }
}

class CarSpot extends Spot {
    CarSpot() {
        super(SpotType.FOUR_WHEELER_SPOT);
    }
}

class HeavyVehicleSpot extends Spot {
    HeavyVehicleSpot() {
        super(SpotType.HEAVY_VEHICLE_SPOT);
    }
}

interface ParkingLotAppInterface {
    Ticket enter(Vehicle vehicle);

    Spot parkVehicle(Ticket ticket);

    Bill exit(Ticket ticket);

    Bill calculateBill(Ticket ticket);

    boolean makePayment(Bill bill, PaymentType paymentType);
}

interface layoutInterface {
    Spot getAvailableSpot(VehicleType vehicleType);
}

interface Storage {
    Spot getEmptySpot(SpotType type);
}

@AllArgsConstructor
class InMemoryDao implements Storage {
    Map<SpotType, List<Spot>> map;

    @Override
    public Spot getEmptySpot(SpotType type) {
        int flag = 0;
        for (SpotType spotType : Spot.getOrderedSpot()) {
            if (type.equals(spotType)) {
                flag = 1;
            }
            if (flag == 1) {
                List<Spot> list = map.getOrDefault(spotType, new ArrayList<>());
                for (Spot spot : list) {
                    if (spot.isEmpty()) {
                        return spot;
                    }
                }
            }
        }
        return null;
    }
}

@AllArgsConstructor
@Data
abstract class BaseParkingLayout implements layoutInterface {
    Storage storage;

    @Override
    public Spot getAvailableSpot(VehicleType vehicleType) {
        SpotType minLevel = null;
        switch (vehicleType) {
            case BIKE:
                minLevel = SpotType.TWO_WHEELER_SPOT;
                break;
            case CAR:
                minLevel = SpotType.FOUR_WHEELER_SPOT;
                break;
            case TRUCK:
                minLevel = SpotType.HEAVY_VEHICLE_SPOT;
                break;
        }
        return storage.getEmptySpot(minLevel);
    }
}

class ParkingLayout extends BaseParkingLayout {
    ParkingLayout(Storage storage) {
        super(storage);
    }
}

@AllArgsConstructor
@Data
class PaymentManager {

}

enum PaymentType {
    CARD,
    UPI,
    FAST_TAG
}

interface BillingStrategy {
    double calculateBill(Ticket ticket);
}

enum BillingStrategyEnums {
    BASIC_HOURLY
}

class BasicBillingStrategy implements BillingStrategy {
    double baseRate = 50;
    int baseUnit = 3;
    double extraPerUnit = 20;
    Ticket ticket;

    int calculateUnits() {
        return 5;
    }

    @Override
    public double calculateBill(Ticket ticket) {
        return baseRate + Math.max(0, calculateUnits() - baseUnit) * extraPerUnit;
    }
}

class Logger {

}

class TicketManager {
    Map<Integer, Ticket> tMap = new HashMap<>();
    int counter = 0;
    public Ticket getTicket(int id){
        return tMap.getOrDefault(id, null);
    }    
    public void setTicket(Ticket ticket){
        counter++;
        ticket.id = counter;
        System.out.println(ticket);
        tMap.put(counter, ticket);
    }    
}

@AllArgsConstructor
@Data
class ParkingLotApp implements ParkingLotAppInterface {
    ParkingLayout parkingLayout;
    PaymentManager paymentManager;
    BillingStrategy billingStrategy;
    Logger logger;
    TicketManager ticketManager;

    @Override
    public Ticket enter(Vehicle vehicle) {
        Ticket ticket = new Ticket(vehicle.number, new Date(), vehicle, null, -1, TicketStatus.ENTERED);
        ticketManager.setTicket(ticket);
        return ticket;
    }

    @Override
    public Spot parkVehicle(Ticket ticket) {
        Vehicle vehicle = ticket.vehicle;
        Spot spot = parkingLayout.getAvailableSpot(vehicle.vehicleType);
        ticket.setSpot(spot);
        spot.setSpotStatus(SpotStatus.OCCUPIED);
        return spot;
    }

    @Override
    public Bill exit(Ticket ticket) {
        ticket.spot.setSpotStatus(SpotStatus.FREE);
        return calculateBill(ticket);
    }

    @Override
    public Bill calculateBill(Ticket ticket) {
        return new Bill(0.0, null, null);
    }

    @Override
    public boolean makePayment(Bill bill, PaymentType paymentType) {
        return false;
    }
}

public class ParkingLot {
    public static void main(String[] args) {
        System.out.println("ParkingLot.main()");
        ParkingLotApp app = new ParkingLotApp(
                new ParkingLayout(new InMemoryDao(Map.of(
                        SpotType.TWO_WHEELER_SPOT, List.of(new BikeSpot()),
                        SpotType.FOUR_WHEELER_SPOT, List.of(new CarSpot()),
                        SpotType.HEAVY_VEHICLE_SPOT, List.of(new HeavyVehicleSpot())))),
                new PaymentManager(),
                new BasicBillingStrategy(),
                new Logger(),
                new TicketManager());
        System.out.println(app.parkVehicle(app.enter(new Bike("KA-53-MK-0926"))));
        System.out.println(app.parkVehicle(app.enter(new Bike("KA-53-MK-0926"))));
        System.out.println(app.parkVehicle(app.enter(new Bike("KA-53-MK-0926"))));
        app.exit(app.ticketManager.getTicket(3));
        System.out.println(app.parkVehicle(app.enter(new Bike("KA-53-MK-0926"))));
        // System.out.println(app.ticketManager.tMap);
    }
}
