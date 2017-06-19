package com.xzy.mq.producer;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.xzy.mq.common.AbstractMessageConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractMessageProducer<T> extends AbstractMessageConfig implements InitializingBean, DisposableBean {

    public static final String DEFAULT_KEY = "demo_key";

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

    @Override
    public void afterPropertiesSet() throws Exception {
        if (producer == null) {
            synchronized (AbstractMessageProducer.class) {
                Properties properties = new Properties();
                properties.put(PropertyKeyConst.ProducerId, this.getProducerId());// 您在MQ控制台创建的Producer ID
                properties.put(PropertyKeyConst.AccessKey, this.getAccessKey());// 鉴权用AccessKey，在阿里云服务器管理控制台创建
                properties.put(PropertyKeyConst.SecretKey, this.getSecretKey());// 鉴权用SecretKey，在阿里云服务器管理控制台创建

                //基于事物的producer
//                LocalTransactionCheckerImpl localTransactionChecker = new LocalTransactionCheckerImpl();
//                TransactionProducer transactionProducer = ONSFactory.createTransactionProducer(properties, localTransactionChecker);
                //2.普通的producer
                producer = ONSFactory.createProducer(properties);
                // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可
                producer.start();
                if (producer == null)
                    log.error("producer is null");
            }
        }
    }

    @Override
    public void destroy() throws Exception {
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

    /**
     * 以JSON形式发送message
     * consumer在消费的时候需采用相同的解析方式
     *
     * @param message
     * @return
     */
    public SendResult sendMsg(T message) {
        //消息的tag,也是订阅消息时的key
        String subscriberKey = generateMessageTag(message.getClass().getSimpleName());
//        String tag = message.getClass().getSimpleName();
        Message msg = new Message(topic, subscriberKey, getKey(message), JSON.toJSONString(message).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("Send Message Success.%nTopic is {}.%nMessage is {}.%nSendResult is {}.", this.topic,
                JSON.toJSONString(message), JSON.toJSONString(sendResult));
        return sendResult;
    }

    public abstract String getKey(T message);
}
