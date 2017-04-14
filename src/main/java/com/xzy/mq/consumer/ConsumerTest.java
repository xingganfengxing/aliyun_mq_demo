package com.xzy.mq.consumer;

import com.aliyun.openservices.ons.api.*;

import java.util.Properties;

public class ConsumerTest {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_test_first_mq_consumer");// 您在MQ控制台创建的Consumer ID
        properties.put(PropertyKeyConst.AccessKey, "LTAIfIUA4FMmzhRU");// 鉴权用AccessKey，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "hkrfVoMKQ0fV7tKOXlgQz5eQ82BxO4");// 鉴权用SecretKey，在阿里云服务器管理控制台创建
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