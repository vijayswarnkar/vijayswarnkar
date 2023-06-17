package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

enum SplitType {
    EQUAL,
    UNEQUAL,
    PERCENT
}

@AllArgsConstructor
@Data
class Expense {
    int id;
    List<User> users;
    List<Double> userShares;
    Double totalAmount;
    SplitType splitType;
}

@AllArgsConstructor
@Data
class User {
    String name;
}

@AllArgsConstructor
class Balance {
    int amount;
    String currency;
}

interface SplitwiseInterface {
    void addExpense(Expense expense);
}

interface SettlementStrategy {
    List<UserDebt> settle(List<UserDebt> userDebtList);
}

@AllArgsConstructor
class UserDebt {
    User user;
    Balance debtMap;
}

class BaseSettlementStrategy implements SettlementStrategy {
    @Override
    public List<UserDebt> settle(List<UserDebt> userDebtList) {
        return userDebtList;
    }
}
class HistoryManager {

}


class ExpenseManager {
    Map<User, Balance> userBalanceMap;

    Map<User, Double> getUserShareMap(Expense expense){
        Map<User, Double> tempUserBalanceMap = new HashMap<>();
        switch (expense.splitType){
            case EQUAL:
                for(User user: expense.users){
                    tempUserBalanceMap.put(user, expense.totalAmount/expense.users.size());
                }
                break;
            case PERCENT:
                break;
            case UNEQUAL:
                break;
        }
        return tempUserBalanceMap;
    }

    void updateTotalBalance(Map<User, Double> shares){

    }

    void add(Expense expense){
        Map<User, Double> shares = getUserShareMap(expense);
        updateTotalBalance(shares);
    }
}

@AllArgsConstructor
public class Splitwise implements SplitwiseInterface {
    SettlementStrategy settlementStrategy;
    ExpenseManager expenseManager;

    @Override
    public void addExpense(Expense expense) {
        expenseManager.add(expense);
    }
}

class SplitwiseRunner {
    public static void main(String[] args) {
        System.out.println("SplitwiseRunner.main");
        Splitwise splitwise = new Splitwise(
                new BaseSettlementStrategy(),
                new ExpenseManager());
        User user1 = new User("user1");
        User user2 = new User("user2");
        User user3 = new User("user3");
        splitwise.addExpense(new Expense(1,
                List.of(user1, user2, user3),
                null, 3000.0,
                SplitType.EQUAL));
        splitwise.show();
    }
}