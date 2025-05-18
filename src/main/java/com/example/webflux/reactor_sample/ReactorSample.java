package com.example.webflux.reactor_sample;

public class ReactorSample {

    public void testReactorSample() throws InterruptedException {
        MyPublisher myPublisher = new MyPublisher();
        MySubscriber mySubscriber = new MySubscriber();

        myPublisher.subscribe(mySubscriber);

        myPublisher.notifySubscribers(new SampleMessage("TestMessage"));

        Thread.sleep(200);

        myPublisher.close();
    }
}
