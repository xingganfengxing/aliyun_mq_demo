package com.xzy.mq.consumer;

import com.google.common.collect.Maps;
import com.xzy.mq.common.AbstractMessageConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 消息的消费者，包含一个或多个消息的订阅者
 * Created by RuzzZZ on 2017/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommonMessageConsumer extends AbstractMessageConfig {

    private String consumerId;

    private List<AbstractSubscriber> subscribers;

    private volatile Map<String, AbstractSubscriber> subscriberMap = Maps.newConcurrentMap();

    private final Set<String> messageKeys = new ConcurrentSkipListSet<>();
}
