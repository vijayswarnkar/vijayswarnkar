package com.example;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;

interface SplitWiseInterface {
    void addExpense(Expense expense);
    void getHistory();
    void getHistory(User user);
    void getHistory(Group group);
    Group createGroup();
    void printExpense();
    void printExpense(Group group);
    void simplifyExpense();
    void simplifyExpense(Group group);
}

class Expense{

}
class Group{
    Set<User> users = new HashSet<>();
    Group addUser(User user){
        users.add(user);
        return this;
    }
}
@AllArgsConstructor
class User{
    String name;
}
public class Splitwise implements SplitWiseInterface {
    
    @Override
    public void addExpense(Expense expense) {
        throw new UnsupportedOperationException("Unimplemented method 'addExpense'");
    }

    @Override
    public void getHistory() {
        throw new UnsupportedOperationException("Unimplemented method 'getHistory'");
    }

    @Override
    public void getHistory(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'getHistory'");
    }

    @Override
    public void getHistory(Group group) {
        throw new UnsupportedOperationException("Unimplemented method 'getHistory'");
    }

    @Override
    public Group createGroup() {
        return new Group();
    }

    @Override
    public void printExpense() {
        throw new UnsupportedOperationException("Unimplemented method 'printExpense'");
    }

    @Override
    public void printExpense(Group group) {
        throw new UnsupportedOperationException("Unimplemented method 'printExpense'");
    }

    @Override
    public void simplifyExpense() {
        throw new UnsupportedOperationException("Unimplemented method 'simplifyExpense'");
    }

    @Override
    public void simplifyExpense(Group group) {
        throw new UnsupportedOperationException("Unimplemented method 'simplifyExpense'");
    }
    public static void main(String[] args) {
        System.out.println("SplitWise!");
        Splitwise splitwise = new Splitwise();
        Group group1 = splitwise.createGroup()
            .addUser(new User("A"))
            .addUser(new User("B"));
        // splitwise.addExpense(null);
    }

}
