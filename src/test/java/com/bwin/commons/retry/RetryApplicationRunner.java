package com.bwin.commons.retry;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

/**
 * @see <a href="https://blog.csdn.net/qq_29229567/article/details/83785672">Spring Boot：项目启动时如何执行特定方法</a>
 */
@Slf4j
@AllArgsConstructor
@Order(value = 1)
@Component
public class RetryApplicationRunner implements ApplicationRunner {

    private final RetryTest retryTest;
    private final RetryTemplate retryTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        log.info("RetryApplicationRunner started");
//        retryTemplate.execute(context -> retryTest.testRetryTemplate(0));
        retryTest.testRetryable("retryable");
    }

}
