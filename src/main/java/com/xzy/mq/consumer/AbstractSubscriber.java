package com.xzy.mq.consumer;

/**
 * 消息订阅者，消息实际上的处理者
 * 消息的具体消费者继承该抽象类，并复写process方法
 * Created by RuzzZZ on 2017/6/19.
 */
public abstract class AbstractSubscriber<T> {

    /**
     * metaq消息处理类
     *
     * @param message
     */
    public abstract void process(T message);
}
