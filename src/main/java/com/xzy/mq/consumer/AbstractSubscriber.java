package com.xzy.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.aliyun.openservices.ons.api.Message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    /**
     *
     * @param message
     */
    public void operateMessage(Message message) {
        T t = parseMessage(message);
        process(t);
    }

    /**
     * 以JSON的形式获取解析message!
     * producer存放的地方也应该存放的是JSON形式的数据
     * @param message
     * @return
     */
    private T parseMessage(Message message) {
        if (message == null || message.getBody() == null) {
            return null;
        }
        final Type type = this.getMessageType();
        if (type instanceof Class) {
            Object data = JSON.parseObject(message.getBody(), type);
            return (T) data;
        } else {
            TypeReference typeRef = new TypeReference() {
                @Override
                public Type getType() {
                    return type;
                }
            };
            try {
                String isoStr = new String(message.getBody(), "utf-8");
                Object data = JSON.parseObject(isoStr, typeRef);
                return (T) data;
            } catch (Exception e) {
                throw new RuntimeException("Parse msg error.");
            }
        }
    }

    private Type getMessageType() {
        Type superType = this.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            return ((ParameterizedType) superType).getActualTypeArguments()[0];
        } else {
            throw new RuntimeException("Unkown parameterized type.");
        }
    }
}
