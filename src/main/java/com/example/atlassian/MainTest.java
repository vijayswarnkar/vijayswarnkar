package com.example.atlassian;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainTest {
    @Test
    public void shouldAnswerWithTrue() throws Exception {
        RateLimiter rateLimiter = RateLimiterImplFactory.getInstance(Strategy.TOKEN_BUCKET_STRATEGY, List.of(
            new Config(1, TimeUnit.SECONDS, 2, 1),
            new Config(2, TimeUnit.DAYS, 2, 1)
        ));
        assertTrue(rateLimiter.isAllowed(1));
        assertFalse(rateLimiter.isAllowed(1));
        TimeUnit.SECONDS.sleep(2);
        assertTrue(rateLimiter.isAllowed(1));
        TimeUnit.SECONDS.sleep(6);
        assertTrue(rateLimiter.isAllowed(1));
        assertFalse(rateLimiter.isAllowed(1));
    }
}
