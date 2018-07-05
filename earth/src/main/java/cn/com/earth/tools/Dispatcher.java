package cn.com.earth.tools;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.com.earth.tools.logger.LogUtils;


/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  16/12/12 下午4:48
 */

public class Dispatcher {
    private static long KEEP_ALIVE_TIME = 60L;
    private static ThreadPoolExecutor executor;
    private static Dispatcher instance;
    private static final int WORK_QUEUE_SIZE = 100; //任务队列尺寸
    private static ArrayList<Future> futures = new ArrayList<>();
    private static ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE);

    private static ThreadPoolExecutor defaultExcutor() {
        return new ThreadPoolExecutor(
                1, 1,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                workQueue,
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

    public static Dispatcher getInstance() {
        if (instance == null) {
            synchronized (Dispatcher.class) {
                if (instance == null) {
                    instance = new Dispatcher();
                }
            }
        }
        return instance;
    }

    private Dispatcher() {
        executor = defaultExcutor();
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                LogUtils.d("负荷运行");
            }
        });
    }

    public void execute(Runnable runnable) {
        try {
            executor.execute(runnable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Future submit(Runnable runnable) {
        update();
        Future future = executor.submit(runnable);
        futures.add(future);
        return future;
    }

    public void update() {
        ArrayList<Future> delFutureList = new ArrayList<>();
        for (Future future : futures) {
            if (future.isDone()) {
                delFutureList.add(future);
            }
        }
        futures.removeAll(delFutureList);
    }

    public void cancleAll() {
        for (Runnable runnable : workQueue) {
            executor.remove(runnable);
        }

        for (Future future : futures) {
            if (!future.isDone()) {
                future.cancel(true);
            }
        }
    }


}
