package com.example.webflux.reactor_sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class MySubscription implements Flow.Subscription {
    private final MyPublisher myPublisher;
    private final ExecutorService executor;
    private boolean canceled = false;

    public MySubscription(MyPublisher myPublisher) {
        this.myPublisher = myPublisher;
        this.executor = Executors.newFixedThreadPool(2);
    }

    @Override
    //n은 수신받을 데이터의 개수
    public void request(long n) {
        if (!canceled) {
            executor.submit(() -> {
                //n개의 데이터 만큼 수신자에게 데이터 전송
                for (int i = 0; i < n; i++) {
                    //취소되면 중단
                    if(canceled) {
                        break;
                    }

                    //수신자에게 데이터 전송
                    myPublisher.notifySubscribers(new SampleMessage("Message"));
                }
            });
        }
    }

    @Override
    public void cancel() {
        canceled = true;
        executor.shutdown();
    }
}
