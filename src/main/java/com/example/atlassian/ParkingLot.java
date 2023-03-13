package com.example.atlassian;

import java.util.*;

/**
 * Provide code for a parking lot with the following assumptions,
 * <p>
 * • The parking lot has multiple levels. Each level has multiple rows of spots.
 * • The parking lot has motorcycle spots and car spots.
 * • A motorcycle can park in any empty spot.
 * • A car can only park in a single empty car spot.
 **/
// Runner -> Obj = ParkingLot(),
// ParkingLot -> LEVELS -> ROWS[] -> Spot
// ROW -> [B B C C]
// Spots -> CarSpot, BikeSpot, TruckSpot
// Vehicle -> Car, Bike
// Main class should be named 'Solution'
// ParkingLot(level[])
// Level(rows[])
// Row(Spots[])
//


interface SpotInterface {
     boolean isEmpty();
     void markFilled(Vehicle v);
}

enum SpotType {
    BIKE,
    CAR,
    TRUCK
}

abstract class Spot implements SpotInterface {
    boolean filled;
    int type;
    int id;
    Vehicle vehicle;
    int level;

    Spot() {
        this.filled = false;
    }

    public boolean isEmpty() {
        return !filled;
    }

    public void markFilled(Vehicle v) {
        this.vehicle = v;
        filled = true;
    }

    void setFree() {
        this.vehicle = null;
        filled = false;
    }
}

class CarSpot extends Spot {
    CarSpot(int type, int id) {
        this.id = id;
        this.type = type;
    }
}

class BikeSpot extends Spot {
    BikeSpot(int type, int id) {
        this.type = type;
        this.id = id;
    }
}


enum VehicleType {
    BIKE(1),
    CAR(2);

    final int label;

    VehicleType(int label) {
        this.label = label;
    }
}
abstract class Vehicle {

    Spot spot;
    int type;

    Vehicle(VehicleType type){

    }
    void remove() {
        spot = null;
    }

    void setSpot(Spot spot) {
        this.spot = spot;
    }
}

class Car extends Vehicle {
    Car() {
        super(VehicleType.CAR);
        this.type = VehicleType.CAR.label;
    }
}

class Bike extends Vehicle {
    Bike() {
        super(VehicleType.BIKE);
        this.type = VehicleType.BIKE.label;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Row {
    List<Spot> spots;

    Row(List<Spot> spots) {
        this.spots = spots;
    }
}

class Level {
    List<Row> rows;

    Level(List<Row> rows) {
        this.rows = rows;
    }
}

class ParkingLot {
    Map<Integer, List<Spot>> map = new HashMap<>();
    int MAX_VEHICLE_TYPE = 2;
    List<Level> levels;

    void printSpots() {
        for (int type : map.keySet()) {

        }
    }

    ParkingLot(List<Level> levels) {
        this.levels = levels;
        for (Level level : levels) {
            for (Row row : level.rows) {
                for (Spot spot : row.spots) {
                    List<Spot> spots = map.getOrDefault(spot.type, new ArrayList<>());
                    spots.add(spot);
                    map.put(spot.type, spots);
                }
            }
        }
        printSpots();
    }

    Spot findSuitableSpot(Vehicle vehicle) {
        int VehicleType = vehicle.type;
        for (int i = VehicleType; i <= MAX_VEHICLE_TYPE; i++) {
            System.out.println(map.get(i));
            for (Spot spot : map.get(i)) {
                if (spot.isEmpty()) {
                    spot.markFilled(vehicle);
                    return spot;
                }
            }
        }
        return null;
    }

    void remove(Spot spot) {
        spot.setFree();
        //vehicle.remove();
    }

    Optional<Spot> assign(Vehicle vehicle) {
        Spot spot = findSuitableSpot(vehicle);
        if (spot != null) {
            vehicle.setSpot(spot);
            System.out.println(spot.id);
            return Optional.of(spot);
        } else {
            //throw Exception("No spot available");
            System.out.println("No");
        }
        return Optional.empty();
    }
}

class Solution {
    static ParkingLot createLot(int[][][] lot) {
        int ctr = 1;
        int l = 0;
        int r = 0;
        List<Level> levels = new ArrayList<>();
        for (int[][] level : lot) {
            l += 1;
            List<Row> rows = new ArrayList<>();
            for (int[] row : level) {
                r += 1;
                List<Spot> spots = new ArrayList<>();
                for (int spot : row) {
                    switch (spot) {
                        case 1:
                            spots.add(new BikeSpot(spot, ctr));
                            break;
                        case 2:
                            spots.add(new CarSpot(spot, ctr));
                            break;
                    }
                    ctr += 1;
                }
                rows.add(new Row(spots));
            }
            levels.add(new Level(rows));
        }
        return new ParkingLot(levels);
    }

    public static void main(String[] args) {
        ParkingLot p = createLot(new int[][][]{
                {
                        {
                                1, 1, 2, 2
                        }
                }
        });
        p.assign(new Car());
        p.assign(new Car());
        p.assign(new Bike());
    }
}
