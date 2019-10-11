package com.vedeng.mjx.service.redis;

import java.util.List;

public class QueueFlag {
    Boolean queueflag;
    Object infoQueue;

    public Object getInfoQueue() {
        return infoQueue;
    }

    public void setInfoQueue(Object infoQueue) {
        this.infoQueue = infoQueue;
    }

    public Boolean getQueueflag() {
        return queueflag;
    }

    public void setQueueflag(Boolean queueflag) {
        this.queueflag = queueflag;
    }

}
