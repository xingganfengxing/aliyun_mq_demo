package com.xzy.mq.common;

import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by RuzzZZ on 2017/4/12.
 */
@Data
public abstract class AbstractMessageConfig {

    /**
     * 阿里云的accessKey
     */
    @Value("#{configProperties['ons.access.key']}")
    private String accessKey;

    /**
     * 阿里云的secretKey
     */
    @Value("#{configProperties['ons.access.secret']}")
    private String secretKey;

    protected static String generateMessageTag(String messageType) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(messageType.getBytes());
            return Base64.encodeBase64URLSafeString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Get message digest error.", e);
        }
    }
}
