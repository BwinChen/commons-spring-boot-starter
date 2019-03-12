package com.bwin.commons.retry;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class RetryTest {

    @Retryable(value = { IllegalArgumentException.class }, maxAttempts = 3, backoff = @Backoff(delay = 3000))
    public void testRetryable(String argument) {
        log.info("testRetryable");
        throw new IllegalArgumentException(argument);
    }

    @Recover
    void testRecover(IllegalArgumentException e, String argument) {
        log.error(e.getMessage());
    }

    public int testRetryTemplate(int test) {
        log.info("testRetryTemplate");
        return 1 / test;
    }

}
