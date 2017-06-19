package com.xzy.mq.common;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;

/**
 * Created by RuzzZZ on 2017/4/14.
 */
public class LocalTransactionCheckerImpl implements LocalTransactionChecker {

    /**
     * 基于事物的producer的回查本地事物
     * Broker回调Producer，将未结束的事务发给Producer，由Producer来再次决定事务是提交还是回
     *
     * @param msg
     * @return
     */
    @Override
    public TransactionStatus check(Message msg) {
        return TransactionStatus.CommitTransaction;
    }

}
