package com.xzy.mq.producer;

import com.aliyun.openservices.ons.api.*;
import com.xzy.mq.producer.impl.FirstDemoMessageProducer;
import com.xzy.mq.producer.model.FirstDemoMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Properties;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class FirstDemoMQTest {

    @Autowired
    private FirstDemoMessageProducer producer;

    @Test
    public void sendMsg() {
        FirstDemoMessage firstDemoMessage = new FirstDemoMessage("Test", 100L);
        SendResult sendResult = producer.sendMsg(firstDemoMessage);
        System.out.println(String.format("SendResult:%s", sendResult));
    }

    @Test
    public void receiveMsg() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_test_first_mq_consumer");// 您在MQ控制台创建的Consumer ID
        properties.put(PropertyKeyConst.AccessKey, "*******");// 鉴权用AccessKey，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "*******");// 鉴权用SecretKey，在阿里云服务器管理控制台创建
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("tp_test_first_mq_topic", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}
