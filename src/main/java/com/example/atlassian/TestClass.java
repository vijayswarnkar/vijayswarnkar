package com.example.atlassian;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestClass {
    @Test
    public void SingleCount() {
        Winner obj = new Winner();
        String name = obj.Winner(Arrays.asList("a", "b", "c"));
        Assert.assertEquals(name, "c");
    }

    @Test
    public void WinnerWithMaxCount() {
        Winner obj = new Winner();
        String name = obj.Winner(Arrays.asList("a", "b", "c", "b"));
        Assert.assertEquals(name, "b");
    }

    @Test
    public void emptyList() {
        Winner obj = new Winner();
        String name = obj.Winner(Arrays.asList());
        Assert.assertEquals(name, null);
    }

    @Test
    public void nullList() {
        Winner obj = new Winner();
        String name = obj.Winner(null);
        Assert.assertEquals(name, null);
    }

    @Test
    public void caseInSensitiveWinner() {
        Winner obj = new Winner();
        String name = obj.Winner(Arrays.asList("a", "B", "c", "b"));
        Assert.assertEquals(name, "b");
    }

    @Test
    public void multipleBalletsSingleWinner() {
        Winner obj = new Winner();
        String name = obj.Winner2(Arrays.asList(
                Arrays.asList("a", "b", "c"),
                Arrays.asList("d", "c", "a")
        ));
        Assert.assertEquals(name, "a");
    }
}
