package com.xzy.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.*;
import com.google.common.collect.Maps;
import com.xzy.mq.common.AbstractMessageConfig;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Properties;

/**
 * 消息监听者，包含一个或多个消费者
 * 主要负责创建消费者，并订阅与其相对应的topic
 * Created by RuzzZZ on 2017/6/19.
 */
@Slf4j
public class ConsumerEventListener extends AbstractMessageConfig {

    /**
     * 消息的消费者中,consumerId和consumer对象的集合
     */
    private static volatile Map</** consumerId */String, Consumer> consumers = Maps.newConcurrentMap();

    @Setter
    private Map</** topic */String, CommonMessageConsumer> processConsumers;

    @PostConstruct
    public void initConsumers() {
        for (Map.Entry</** topic */String, /** consumerId */CommonMessageConsumer> ent : processConsumers.entrySet()) {
            CommonMessageConsumer msgConsumer = ent.getValue();
            initOneConsumer(ent.getKey(), msgConsumer);
        }
    }

    private void initOneConsumer(String topic, final CommonMessageConsumer msgConsumer) {
        String consumerId = msgConsumer.getConsumerId();
        if (consumers.get(consumerId) == null) {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.ConsumerId, consumerId);
            properties.put(PropertyKeyConst.AccessKey, getAccessKey());
            properties.put(PropertyKeyConst.SecretKey, getSecretKey());
            Consumer consumer = ONSFactory.createConsumer(properties);
            consumer.subscribe(topic, "*", new MessageListener() {
                @Override
                public Action consume(Message msg, ConsumeContext consumeContext) {
                    try {
                        log.info(Thread.currentThread().getName() + "Topic:" + msg.getTopic()
                                + " Receive New Messages: " + JSON.toJSONString(msg));
                        msgConsumer.operateMessageTemplate(msg);
                        return Action.CommitMessage;
                    } catch (Exception e) {
                        log.error("Error dealing with metaq message. Message:" + msg.toString(), e);
                        return Action.ReconsumeLater;
                    }
                }
            });
            consumer.start();
            consumers.put(consumerId, consumer);
        }
    }
}
