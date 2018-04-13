package com.elderbyte.spring.cloud.bootstrap.feign;

import com.netflix.hystrix.Hystrix;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;


@ConditionalOnClass({ Hystrix.class })
@EnableCircuitBreaker
public class EnableCircuitBreakerOnHystrix {

}
