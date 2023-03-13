package com.example.lambdaExpression;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LambdaTutorial {
    public static void main(String[] args) throws InterruptedException {
        long st = System.currentTimeMillis();
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        names.sort((a,b)-> b.compareTo(a));
        System.out.println(names);
        List<Character> ch = names.stream()
                .map(x -> x.charAt(0))
                .filter(c -> c != 'a')
                .collect(Collectors.toList());
        System.out.println(ch);
        names.stream()
            .sorted()
            .map(String::toUpperCase)
            .map(x -> x.charAt(0))
            .forEach(System.out::println);

        Optional<Integer> sum = names.stream()
                .sorted()
                .map(String::toUpperCase)
                .map(x -> x.charAt(0))
                .map(Integer::new)
                .reduce((x,y) -> x+y);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(sum.get());

        long ed = System.currentTimeMillis();
        System.out.println(ed-st);
    }
}
