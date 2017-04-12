package com.xzy.mq.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
