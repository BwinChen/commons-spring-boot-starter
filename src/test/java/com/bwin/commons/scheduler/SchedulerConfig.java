package com.bwin.commons.scheduler;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 分布式定时任务
 * @see <a href="https://www.baeldung.com/shedlock-spring">Guide to ShedLock with Spring</a>
 * @see <a href="https://github.com/lukas-krecan/ShedLock">lukas-krecan/ShedLock</a>
 */
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@EnableScheduling
@Configuration
public class SchedulerConfig {

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        return new RedisLockProvider(connectionFactory, "shedlock");
    }

}
