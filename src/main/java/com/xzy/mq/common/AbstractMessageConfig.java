package com.xzy.mq.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractMessageConfig {

    /** 阿里云的accessKey */
    private String accessKey;

    /** 阿里云的secretKey */
    private String secretKey;
}
