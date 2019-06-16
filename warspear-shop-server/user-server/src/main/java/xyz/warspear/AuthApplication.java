package xyz.warspear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "xyz.warspear")
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableJpaAuditing
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);
    }

    @Bean
    public ExecutorService newThreadPool(){
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public ExecutorService emailThreadPool(){
        return Executors.newFixedThreadPool(2);
    }
}
