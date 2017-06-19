package com.xzy.mq.consumer;

import com.aliyun.openservices.ons.api.Consumer;
import com.google.common.collect.Maps;
import com.xzy.mq.common.AbstractMessageConfig;
import lombok.Setter;

import java.util.Map;

/**
 * 消息监听者，包含一个或多个消费者
 * 主要负责创建消费者，并订阅与其相对应的topic
 * Created by RuzzZZ on 2017/6/19.
 */
public class ConsumerEventListener extends AbstractMessageConfig {

    /**
     * 消息的消费者中,consumerId和consumer对象的集合
     */
    private static volatile Map</** consumerId */String, Consumer> consumers = Maps.newConcurrentMap();

    @Setter
    private Map</** topic */String, CommonMessageConsumer> processConsumers;


}
