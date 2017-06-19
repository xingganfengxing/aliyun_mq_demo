package com.xzy.mq.consumer;

import com.aliyun.openservices.ons.api.Message;
import com.google.common.collect.Maps;
import com.xzy.mq.common.AbstractMessageConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
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

    private volatile Map<String/** subscriber type */, AbstractSubscriber> subscriberMap = Maps.newConcurrentMap();

    private final Set<String> messageKeys = new ConcurrentSkipListSet<>();

    @PostConstruct
    public void initSubscriberMap() {
        for (AbstractSubscriber subscriber : subscribers) {
            //得到subscriber的泛型父类
            Type interfaceType = subscriber.getClass().getGenericSuperclass();
            if (interfaceType instanceof ParameterizedType) {
                //获取泛型的参数类型
                Type messageType = ((ParameterizedType) interfaceType).getActualTypeArguments()[0];
                String messageTypeName = messageType.toString().replace("class ", "");
                String simpleTypeName = messageTypeName.substring(messageTypeName.lastIndexOf(".") + 1);

                if (!subscriberMap.containsKey(simpleTypeName)) {
                    subscriberMap.put(simpleTypeName, subscriber);
                }
            }
        }
    }

    public void operateMessageTemplate(Message message) throws NoSuchAlgorithmException {
        AbstractSubscriber subscriber = subscriberMap.get(message.getTag());
        if (subscriber != null) {
            String hashed = new String(message.getBody());
            if (!messageKeys.contains(hashed)) {//尽量保证集群每个节点消息只被消费一次，后期应使用redis做消息去重
                messageKeys.add(hashed);
                subscriber.operateMessage(message);
            }
        } else {
            throw new RuntimeException("No access subscriber.");
        }
    }
}
