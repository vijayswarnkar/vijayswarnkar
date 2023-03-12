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
        this.multiplier = getMultiplier();
    }

    @Override
    public int getPrice() {
        return price * multiplier;
    }

    @Override
    public Size getSize() {
        return size;
    }

    int getMultiplier() {
        int multiplier = 1;
        switch (this.size) {
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
}

abstract class Coffee extends BaseBeverage {
    CoffeeType coffeeType;

    Coffee(Size size, CoffeeType type, int price) {
        super(size, price);
        this.coffeeType = type;
    }

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

    public int getPrice() {
        return extraPrice + beverage.getPrice();
    }
}

class Milk extends Decorator {
    Milk(Beverage beverage) {
        super(beverage, 5);
    }
}

class Hot extends Decorator {
    Hot(Beverage beverage) {
        super(beverage, 5);
    }
}

public class CoffeeMachine {
    public static void main(String[] args) {
        Beverage beverage = Coffee.getInstance(Size.MEDIUM, CoffeeType.BLACK);
        System.out.println(beverage.getPrice());
        System.out.println(new Milk(beverage).getPrice());
        Water water = new Water(Size.LARGE);
        System.out.println(water.getPrice());
        System.out.println(new Hot(water).getPrice());
    }
}
