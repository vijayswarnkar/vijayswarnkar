package com.example.zeta.interview;

interface Beverage {
    int getPrice();

    Size getSize();
}

abstract class BaseBeverage implements Beverage {
    int price;
    Size size;
    int multiplier;

    BaseBeverage(Size size, int price) {
        this.size = size;
        this.price = price;
        this.multiplier = getMultiplier(size);
    }
    @Override
    public String toString() {
        return "Beverage ";
    }

    @Override
    public int getPrice() {
        return price * multiplier;
    }

    @Override
    public Size getSize() {
        return size;
    }

    static int getMultiplier(Size size) {
        int multiplier = 0;
        switch (size) {
            case SMALL:
                multiplier = 1;
                break;
            case MEDIUM:
                multiplier = 2;
                break;
            case LARGE:
                multiplier = 3;
                break;
        }
        return multiplier;
    }
}

class Water extends BaseBeverage {
    Water(Size size) {
        super(size, 0);
    }

    @Override
    public String toString() {
        return "water " + super.toString();
    }
}

abstract class Coffee extends BaseBeverage {
    CoffeeType coffeeType;

    Coffee(Size size, CoffeeType type, int price) {
        super(size, price);
        this.coffeeType = type;
    }

    @Override
    public String toString() {
        return "Coffee " + super.toString();
    }
}

class CoffeeFactory {
    static Coffee getInstance(Size size, CoffeeType type) {
        switch (type) {
            case BLACK:
                return new BlackCoffee(size);
        }
        return new BlackCoffee(size);
    }
}

class BlackCoffee extends Coffee {
    public BlackCoffee(Size size) {
        super(size, CoffeeType.BLACK, 100);
    }

    @Override
    public String toString() {
        return "Black " + super.toString();
    }
}

enum CoffeeType {
    BLACK,
    GREEN
}

enum Size {
    SMALL,
    MEDIUM,
    LARGE
}

abstract class Decorator extends BaseBeverage {
    Beverage beverage;
    int extraPrice;

    Decorator(Beverage beverage, int extraPrice) {
        super(beverage.getSize(), beverage.getPrice());
        this.beverage = beverage;
        this.extraPrice = extraPrice;
    }

    @Override
    public int getPrice() {
        return extraPrice + beverage.getPrice();
    }
}

class Milk extends Decorator {
    Milk(Beverage beverage) {
        super(beverage, 5);
    }
    @Override
    public String toString() {
        return "Milk " + beverage.toString();
    }
}

class Hot extends Decorator {
    Hot(Beverage beverage) {
        super(beverage, 5);
    }

    @Override
    public String toString() {
        return "Hot " + beverage.toString();
    }
}

public class CoffeeMachine {
    public static void main(String[] args) {
        Beverage beverage = CoffeeFactory.getInstance(Size.MEDIUM, CoffeeType.BLACK);
        System.out.println(beverage.getPrice());
        System.out.println(new Milk(beverage).getPrice());
        Water water = new Water(Size.LARGE);
        System.out.println(water.getPrice());
        System.out.println(new Hot(water).getPrice());
        System.out.println(new Milk(new Milk(water)));
    }
}
