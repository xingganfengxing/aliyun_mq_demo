package com.xzy.mq.producer;

import com.aliyun.openservices.ons.api.SendResult;
import com.xzy.mq.producer.impl.FirstDemoMessageProducer;
import com.xzy.mq.producer.model.FirstDemoMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class FirstDemoMessageProducerTest {

    @Autowired
    private FirstDemoMessageProducer producer;

    @Test
    public void sendMsgOfFirstDemoTest(){
        FirstDemoMessage firstDemoMessage = new FirstDemoMessage("Test",100L);
        SendResult sendResult = producer.sendMsg(firstDemoMessage);
        System.out.println(String.format("SendResult:%s",sendResult));
    }
}
