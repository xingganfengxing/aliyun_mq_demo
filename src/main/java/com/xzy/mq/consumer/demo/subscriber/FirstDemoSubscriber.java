package com.xzy.mq.consumer.demo.subscriber;

import com.xzy.mq.consumer.AbstractSubscriber;
import com.xzy.mq.consumer.demo.model.FirstDemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by RuzzZZ on 2017/6/19.
 */
@Slf4j
@Component
public class FirstDemoSubscriber extends AbstractSubscriber<FirstDemoMessage> {

    @Override
    public void process(FirstDemoMessage message) {
        log.info("message:{}",message.toString());
    }
}
