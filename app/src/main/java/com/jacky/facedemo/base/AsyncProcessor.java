package com.jacky.facedemo.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/4 上午1:07
 */

public class AsyncProcessor {
    final static String TAG = "AsyncProcessor";

    BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1);
    private static long KEEP_ALIVE_TIME = 60L;
    ThreadPoolExecutor executor;

    private ThreadPoolExecutor defaultExecutor() {
        return new ThreadPoolExecutor(
                1, 1,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                blockingQueue,
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

    public AsyncProcessor() {
        executor = defaultExecutor();
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    static class InstanceHolder {
        public static AsyncProcessor instance = new AsyncProcessor();
    }

    public static AsyncProcessor getInstance() {
        return InstanceHolder.instance;
    }
    public synchronized void clearAll() {
        if (!blockingQueue.isEmpty()) {
            try {
                blockingQueue.remove();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
