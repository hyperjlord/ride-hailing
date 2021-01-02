package com.example.orderservice.config;

import feign.Retryer;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class FeignConfig {
    public Retryer feignRetryer(){
        return new Retryer.Default(100,SECONDS.toMillis(1),5);
    }
}
