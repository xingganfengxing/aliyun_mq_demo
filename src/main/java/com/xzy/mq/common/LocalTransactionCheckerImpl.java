package com.xzy.mq.common;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;

/**
 * Created by RuzzZZ on 2017/4/14.
 */
public class LocalTransactionCheckerImpl implements LocalTransactionChecker {
    @Override
    public TransactionStatus check(Message msg) {
        return TransactionStatus.CommitTransaction;
    }

}
