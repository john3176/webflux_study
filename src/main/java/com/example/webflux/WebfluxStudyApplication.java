package com.example.webflux;

import com.example.webflux.reactor_sample.ReactorSample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebfluxStudyApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(WebfluxStudyApplication.class, args);

		ReactorSample reactorSample = new ReactorSample();

		reactorSample.testReactorSample();
	}

}
