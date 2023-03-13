package com.example.garbaseCollection;

class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    /* Overriding finalize method to check which object is garbage collected */
    protected void finalize() throws Throwable {
        System.out.println("Person object - " + this.name + " -> successfully garbage collected");
    }
}

public class GarbageCollectorCode {
    public static void main(String[] args) throws InterruptedException {
        gcSample1();
        gcSample2();
        Thread.currentThread().join();
    }

    private static void gcSample1() {
        Person p1 = new Person("John Doe");
        p1 = null;
        System.gc(); // p1 will be garbage-collected
    }

    private static void gcSample2() {
        Person p1 = new Person("John Doe");
        Person p2 = new Person("Jane Doe");
        p1 = p2;
        System.gc(); // p1 will be garbage-collected
    }
}


class PersonTest {
    static void createMale() {
        //object p1 inside method becomes unreachable after createMale() completes
        Person p1 = new Person("John Doe");
        createFemale();

        // calling garbage collector
        System.out.println("GC Call inside createMale()");
        System.gc(); // p2 will be garbage-collected
    }

    static void createFemale() {
        //object p2 inside method becomes unreachable after createFemale() completes
        Person p2 = new Person("Jane Doe");
    }

    public static void main(String args[]) {
        createMale();

        // calling garbage collector
        System.out.println("\nGC Call inside main()");
        System.gc(); // p1 will be garbage-collected
    }
}