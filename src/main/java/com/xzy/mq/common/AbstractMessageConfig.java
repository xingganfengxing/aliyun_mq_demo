package com.xzy.mq.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@Data
public abstract class AbstractMessageConfig {

    /**
     * 阿里云的accessKey
     */
//    @Value("#{configProperties['ons.access.key']}")
    private String accessKey = "******";

    /**
     * 阿里云的secretKey
     */
//    @Value("#{configProperties['ons.access.secret']}")
    private String secretKey = "******";
}
