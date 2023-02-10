package com.devcodes.demokafkastream.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ing. Danilo Montoya Hernandez(Galy);
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>demo-kafka-stream</b> 08/02/2023, 8:32 AM
 **/
@Data
@ConfigurationProperties("kafka-props")
public class KafkaProperties {
    private String bootstrapServers;
    private String inboundTopic;
    private String outboundTopic;
}
