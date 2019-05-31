package xyz.warspear;

import com.auth0.jwt.JWT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import xyz.warspear.gateway.filter.AuthorizationFilter;
import xyz.warspear.gateway.filter.LoginFilter;
import xyz.warspear.gateway.filter.LogoutFilter;


@EnableZuulProxy
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "xyz.warspear")
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableJpaAuditing
public class ZuulApplication {
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(ZuulApplication.class);
    }


    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }
    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter();
    }
    @Bean
    public LogoutFilter logoutFilter() {
        return new LogoutFilter();
    }
}
