package dev.pfilaretov42.glue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Profile("virtual-thread-pool")
public class VirtualThreadPoolConfig {

    @Bean
    public ExecutorService executor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
