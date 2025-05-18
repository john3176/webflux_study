package com.example.webflux.reactor_sample;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;

public class MySubscriber implements Flow.Subscriber<SampleMessage> {
    private Flow.Subscription subscription;
    private final int bufferSize = 10;
    //동시성 프로그래밍을 위한 객체
    private final AtomicInteger processedItems = new AtomicInteger(0);

    //Subscriber가 Publisher를 구독했을 때 실행
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        //subscription 저장
        this.subscription = subscription;
        //구독후 Subscription에게 bufferSize 만큼의 데이터를 요청
        subscription.request(bufferSize+2);
    }

    @Override
    //수신 받은 데이터를 처리
    public void onNext(SampleMessage sampleMessage) {
        System.out.println("OnNext: " + sampleMessage);
        
        processedItems.incrementAndGet();
        //버퍼사이즈 이상의 데이터를 수신했을 때 처리
        if(processedItems.get() >= bufferSize) {
            System.out.println("Buffer overflow");
            //processedItems 초기화
            processedItems.set(0);
            //subscription에게 데이터 요청
            subscription.request(2);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }
}
