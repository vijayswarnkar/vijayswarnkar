package com.example.lld;

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

    boolean release();
}

@Data
abstract class Spot implements SpotInterface {
    SpotStatus spotStatus;
    SpotType spotType;
    Vehicle vehicle;
    String metadata;

    Spot(SpotType spotType) {
        this.spotType = spotType;
        this.spotStatus = SpotStatus.FREE;
        this.metadata = "";
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
            System.out.println(this + " -> parked");
            return true;
        }
        return false;
    }

    @Override
    public boolean release() {
        if (spotStatus.equals(SpotStatus.OCCUPIED)) {
            spotStatus = SpotStatus.FREE;
            this.vehicle = null;
            System.out.println(this + " -> released");
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
@Data
abstract class BaseParkingLayout implements layoutInterface {
    Map<SpotType, List<Spot>> map;

    Spot getEmptySpot(SpotType type) {
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
        return this.getEmptySpot(minLevel);
    }
}

class ParkingLayout extends BaseParkingLayout {
    ParkingLayout(Map<SpotType, List<Spot>> map) {
        super(map);
    }
}

class NewParkingLayout extends BaseParkingLayout {

    public static Map<SpotType, List<Spot>> levelsToMap(List<Level> levels) {
        Map<SpotType, List<Spot>> map = new HashMap<>();
        int i = 0;
        for (Level level : levels) {
            i++;
            int j = 0;
            for (Row row : level.rows) {
                j++;
                for (Spot spot : row.spots) {
                    List<Spot> list = map.getOrDefault(spot.spotType, new ArrayList<>());
                    spot.setMetadata("{level:" + i + ", row:" + j + "}");
                    list.add(spot);
                    map.put(spot.spotType, list);
                }
            }
        }
        System.out.println(map);
        return map;
    }

    NewParkingLayout(List<Level> levels) {
        super(levelsToMap(levels));
    }
}

@AllArgsConstructor
class Level {
    List<Row> rows;
    boolean isActive;
}

@AllArgsConstructor
class Row {
    List<Spot> spots;
    boolean isActive;
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

    public Ticket getTicket(int id) {
        return tMap.getOrDefault(id, null);
    }

    public void setTicket(Ticket ticket) {
        counter++;
        ticket.id = counter;
        // System.out.println(ticket);
        tMap.put(counter, ticket);
    }
}

@AllArgsConstructor
@Data
class ParkingLotApp implements ParkingLotAppInterface {
    BaseParkingLayout parkingLayout;
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
        if (spot != null) {
            spot.park(vehicle);
            ticket.setSpot(spot);
        } else {
            System.out.println("No spot available. " + ticket);
        }
        return spot;
    }

    @Override
    public Bill exit(Ticket ticket) {
        ticket.spot.release();
        ticket.spot = null;
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
                new ParkingLayout(Map.of(
                        SpotType.TWO_WHEELER_SPOT, List.of(new BikeSpot()),
                        SpotType.FOUR_WHEELER_SPOT, List.of(new CarSpot()),
                        SpotType.HEAVY_VEHICLE_SPOT, List.of(new HeavyVehicleSpot()))),
                new PaymentManager(),
                new BasicBillingStrategy(),
                new Logger(),
                new TicketManager());
        app.parkVehicle(app.enter(new Bike("KA-53-MK-0926")));
        app.parkVehicle(app.enter(new Bike("KA-53-MK-0926")));
        app.parkVehicle(app.enter(new Bike("KA-53-MK-0926")));
        // app.exit(app.ticketManager.getTicket(3));
        app.parkVehicle(app.enter(new Bike("KA-53-MK-0926")));
        // System.out.println(app.ticketManager.tMap);
    }
}

class ParkingLotRunner2 {
    public static void main(String[] args) {
        System.out.println("ParkingLot.main()");
        ParkingLotApp app = new ParkingLotApp(
                new NewParkingLayout(List.of(
                        new Level(List.of(
                                new Row(List.of(
                                        new CarSpot()),
                                        true)),
                                true),
                        new Level(List.of(
                                new Row(List.of(
                                        new CarSpot(), new HeavyVehicleSpot()),
                                        true)),
                                true))),
                new PaymentManager(),
                new BasicBillingStrategy(),
                new Logger(),
                new TicketManager());
        app.parkVehicle(app.enter(new Bike("KA-53-MK-0926")));
        app.parkVehicle(app.enter(new Bike("KA-53-MK-0926")));
        app.parkVehicle(app.enter(new Bike("KA-53-MK-0926")));
        app.exit(app.ticketManager.getTicket(3));
        app.parkVehicle(app.enter(new Bike("KA-53-MK-0926")));
        System.out.println(app.ticketManager.tMap);
    }
}
