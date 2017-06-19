package com.xzy.mq.consumer.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FirstDemoMessage {

    private String name;

    private Long serialId;
}
