package com.vedeng.mjx.web.httpclientpool;


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyClientPool {

    private LinkedList<HttpClient> pool = new LinkedList<HttpClient>();

    private static final int INIT_CONNECTIONS = 100;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // 通过构造方法初始化连接
    public MyClientPool() {
        for (int i = 0; i < INIT_CONNECTIONS; i++) {
                HttpClient client = new DefaultHttpClient();
                pool.addLast(client);
        }
    }

    // 获取数据库连接
    public HttpClient getConnection() {
        HttpClient conn = null;
        lock.lock();
        try {

            while (pool.size() < 0) {
                try {
                    System.out.println(pool.size()+"等待");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!pool.isEmpty()) {
                conn = pool.removeFirst();
            }else {
                    conn=new DefaultHttpClient();
            }
        } finally {
            lock.unlock();
        }
        return conn;
    }

    // 释放数据库连接
    public void releaseConnection(HttpClient conn) {
        if (conn != null) {

            lock.lock();
            try {
                // 释放连接过程就是把连接放回连接池过程
                if (pool.size()>=100){
                    conn=null;
                }else {
                    pool.addLast(conn);
                    System.out.println(pool.size()+"归还后");
                    condition.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
