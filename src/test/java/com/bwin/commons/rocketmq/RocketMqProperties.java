package com.bwin.commons.rocketmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "rocketmq")
@PropertySource("classpath:my.properties")
@Component
public class RocketMqProperties implements Serializable {

    private String consumerGroup;
    private String producerGroup;
    private String nameSrvAddr;

}