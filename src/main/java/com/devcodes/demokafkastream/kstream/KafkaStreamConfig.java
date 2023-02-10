package com.devcodes.demokafkastream.kstream;

import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ing. Danilo Montoya Hernandez(Galy);
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>demo-kafka-stream</b> 08/02/2023, 8:27 AM
 **/
@Configuration
public class KafkaStreamConfig {

    @Bean
    public StreamsBuilder builder(){
        return new StreamsBuilder();
    }
}
