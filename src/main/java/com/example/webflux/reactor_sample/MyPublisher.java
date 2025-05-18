package com.example.webflux.reactor_sample;

//자바에서 제공해주는 라이브러리

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class MyPublisher implements Flow.Publisher<SampleMessage> {
    private final List<Flow.Subscriber<? super SampleMessage>> subscribers = new ArrayList<>();
    //비동기 동작을 위한 객체
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    //구독자를 구독자 리스트에 추가
    @Override
    public void subscribe(Flow.Subscriber<? super SampleMessage> subscriber) {
        System.out.println("Subscribe to " + subscriber);
        subscribers.add(subscriber);
        //Subscriber에게 Subscription 생성 후 전달
        subscriber.onSubscribe(new MySubscription(this));
    }

    //구독자들에게 메세지 전송
    public void notifySubscribers(SampleMessage message) {
        for (Flow.Subscriber<? super SampleMessage> subscriber : subscribers) {
            //구독자에게 비동기 방식으로 메세지를 전송
            executorService.execute(() -> subscriber.onNext(message));
        }
    }

    public void close() {
        for (Flow.Subscriber<? super SampleMessage> subscriber : subscribers) {
            executorService.execute(() -> subscriber.onComplete());
        }

        //executorService 종료
        executorService.shutdown();
    }
}
