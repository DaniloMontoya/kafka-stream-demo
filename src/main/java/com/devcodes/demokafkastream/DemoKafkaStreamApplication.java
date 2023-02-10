package com.devcodes.demokafkastream;

import com.devcodes.demokafkastream.properties.KafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(KafkaProperties.class)
@SpringBootApplication
public class DemoKafkaStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoKafkaStreamApplication.class, args);
    }

}
