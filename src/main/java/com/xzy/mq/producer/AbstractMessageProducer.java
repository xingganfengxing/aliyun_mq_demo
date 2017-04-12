package com.xzy.mq.producer;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractMessageProducer<T> extends AbstractMessageConfig {

    /**
     * 消息topic
     */
    private String topic;

    /**
     * 生产者ID
     */
    private String producerId;

    /**
     * 生产者实例
     */
    private volatile Producer producer;

    @PostConstruct
    public void initProducer() {
        if (producer == null) {
            synchronized (AbstractMessageProducer.class) {
                if(producer == null){
                    Properties properties = new Properties();
                    properties.put(PropertyKeyConst.ProducerId, this.getProducerId());// 您在MQ控制台创建的Producer ID
                    properties.put(PropertyKeyConst.AccessKey, this.getAccessKey());// 鉴权用AccessKey，在阿里云服务器管理控制台创建
                    properties.put(PropertyKeyConst.SecretKey, this.getSecretKey());// 鉴权用SecretKey，在阿里云服务器管理控制台创建
                    Producer producer = ONSFactory.createProducer(properties);
                    // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可
                    producer.start();
                }
            }
        }
    }

    @PreDestroy
    public void destroyProducers() throws InterruptedException {
        if (producer != null) {
            synchronized (AbstractMessageProducer.class) {
                if (producer != null) {
                    // 等待3秒，防止其它线程未将消息发完
                    Thread.sleep(3000);
                    producer.shutdown();
                }
            }
        }
    }

    public void sendMsg(T message){

        Message msg = new Message(topic, getTag(message), getKey(message), JSON.toJSONString(message).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("Send Message Success.%nTopic is {}.%nMessage is {}.%nSendResult is {}.", this.topic,
                JSON.toJSONString(message), JSON.toJSONString(sendResult));
    }

    public abstract String getKey(T message);

    public abstract String getTag(T message);
}
