package com.sakshi.sarma.networkmonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NetworkmonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkmonitoringApplication.class, args);
    }

}
