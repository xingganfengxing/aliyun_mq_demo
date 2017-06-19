package com.xzy.mq.producer;

import com.aliyun.openservices.ons.api.SendResult;
import com.xzy.mq.producer.impl.FirstDemoMessageProducer;
import com.xzy.mq.producer.model.FirstDemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by RuzzZZ on 2017/6/19.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class MQTest {

    @Autowired
    private FirstDemoMessageProducer producer;

    @Test
    public void sendMsg() {
        FirstDemoMessage firstDemoMessage = new FirstDemoMessage("Test", 100L);
        SendResult sendResult = producer.sendMsg(firstDemoMessage);
        log.info("SendResult :{} ", sendResult.toString());
    }

    @Test
    public void receiveMsg() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            FirstDemoMessage firstDemoMessage = new FirstDemoMessage("Test", (long) i);
            SendResult sendResult = producer.sendMsg(firstDemoMessage);
            log.info("SendResult : ", sendResult.toString());
        }
        //如果希望执行时间长一点,可以修改如下的Sleep的数值
        Thread.sleep(30000L);
    }

}
