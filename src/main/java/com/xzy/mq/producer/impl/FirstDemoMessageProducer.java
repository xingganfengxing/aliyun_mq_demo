package com.xzy.mq.producer.impl;

import com.xzy.mq.producer.AbstractMessageProducer;
import com.xzy.mq.producer.model.FirstDemoMessage;
import lombok.Data;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@Data
public class FirstDemoMessageProducer extends AbstractMessageProducer<FirstDemoMessage> {

    @Override
    public String getKey(FirstDemoMessage message) {
        return "serialId:" + String.valueOf(message.getSerialId());
    }
}
