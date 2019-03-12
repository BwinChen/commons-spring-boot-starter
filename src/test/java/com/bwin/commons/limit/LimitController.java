package com.bwin.commons.limit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试限流注解
 */
@RequestMapping("/limit")
@RestController
public class LimitController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     * 意味著 100S 内最多允許訪問10次
     */
    @Limit(prefix = "limit:", name="upload:", limitType = LimitType.IP, period = 100, count = 10)
    @GetMapping("/test")
    public int testLimiter() {
        return ATOMIC_INTEGER.incrementAndGet();
    }

}
