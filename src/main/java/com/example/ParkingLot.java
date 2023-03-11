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

class Car extends Vehicle {
    Car(String number){
        super(number, VehicleType.CAR);
    }
}

enum VehicleType {
    BIKE,
    CAR,
    TRUCK
}
@AllArgsConstructor
@Data
class Ticket {
    String number;
    Date date;
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
}
@AllArgsConstructor
@Data
abstract class Spot implements SpotInterface{
    boolean isEmpty;
    SpotType spotType;

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public boolean park(Vehicle vehicle) {
        if(isEmpty == true) {
            isEmpty = false;
            return true;
        }
        return false;
    }
}
enum SpotType {
    TWO_WHEELER_SPOT,
    FOUR_WHEELER_SPOT,
    HEAVY_VEHICLE_SPOT
}

class BikeSpot extends Spot {
    BikeSpot(){
        super(true, SpotType.TWO_WHEELER_SPOT);
    }
}


interface ParkingLotAppInterface {
    Ticket enter(Vehicle vehicle);

    Spot parkVehicle(Vehicle vehicle);

    Bill exit(Ticket ticket);

    double calculateBill(Ticket ticket);

    boolean makePayment(Bill bill, PaymentType paymentType);
}

interface layoutInterface {

}

@AllArgsConstructor
@Data
abstract class BaseParkingLayout {

}

class ParkingLayout extends BaseParkingLayout {

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

@AllArgsConstructor
@Data
class ParkingLotApp implements ParkingLotAppInterface {
    ParkingLayout parkingLayout;
    PaymentManager paymentManager;
    BillingStrategy billingStrategy;
    Logger logger;

    static ParkingLotApp getInstance() {
        return new ParkingLotApp(new ParkingLayout(), new PaymentManager(), new BasicBillingStrategy(), new Logger());
    }

    @Override
    public Ticket enter(Vehicle vehicle) {
        return new Ticket(vehicle.number, new Date());
    }

    @Override
    public Spot parkVehicle(Vehicle vehicle) {
        return null;
    }

    @Override
    public Bill exit(Ticket ticket) {
        return null;
    }

    @Override
    public double calculateBill(Ticket ticket) {
        return 0.0;
    }

    @Override
    public boolean makePayment(Bill bill, PaymentType paymentType) {
        return false;
    }
}

public class ParkingLot {
    public static void main(String[] args) {
        System.out.println("ParkingLot.main()");
        ParkingLotApp app = ParkingLotApp.getInstance();
        System.out.println(app.parkVehicle(new Car("KA-53-MK-0926")));
    }
}
