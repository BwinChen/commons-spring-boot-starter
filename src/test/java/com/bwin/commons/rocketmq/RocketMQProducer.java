package com.bwin.commons.rocketmq;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import javax.annotation.PostConstruct;

/**
 * @see <a href="http://www.spring4all.com/article/682">SpringBoot RocketMQ 整合使用和监控</a>
 * @see <a href="https://www.cnblogs.com/coder-lzh/p/9006048.html">RocketMQ在windows环境下的安装</a>
 */
@Slf4j
@AllArgsConstructor
@Component
public class RocketMQProducer {

    private final RocketMqProperties rocketMqProperties;

    @PostConstruct
    public void defaultMQProducer() {
        //生产者的组名
        DefaultMQProducer producer = new DefaultMQProducer(rocketMqProperties.getProducerGroup());
        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(rocketMqProperties.getNameSrvAddr());

        try {
            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            producer.start();

            //创建一个消息实例，包含 topic、tag 和 消息体
            //如下：topic 为 "TopicTest"，tag 为 "push"
            Message message = new Message("TopicTest", "push", "发送消息----zhisheng-----".getBytes(RemotingHelper.DEFAULT_CHARSET));

            StopWatch stop = new StopWatch();
            stop.start();

            for (int i = 0; i < 10; i++) {
                SendResult result = producer.send(message);
                System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
            }
            stop.stop();
            System.out.println("----------------发送一万条消息耗时：" + stop.getTotalTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

}