package com.xzy.mq.producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstDemoMessage {

    private String name;

    private Long serialId;
}
