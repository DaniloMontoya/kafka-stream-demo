package com.devcodes.demokafkastream.kstream;

import com.devcodes.demokafkastream.dto.AlertDTO;
import com.devcodes.demokafkastream.model.Detection;
import com.devcodes.demokafkastream.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.serializer.JsonSerde;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Ing. Danilo Montoya Hernandez(Galy);
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>demo-kafka-stream</b> 07/02/2023, 11:22 AM
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaDetectionStream {

    private final StreamsBuilder builder;
    private final KafkaProperties kafkaProperties;

    public Properties streamConfigProps(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "demo-kafka-stream");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return properties;
    }

    @PostConstruct
    public void processData(){
        JsonSerde<AlertDTO> inJsonSerde = new JsonSerde<>(AlertDTO.class);
        JsonSerde<Detection> outJsonSerde = new JsonSerde<>(Detection.class);
        final KStream<String, AlertDTO> source = builder.stream(kafkaProperties.getInboundTopic(), Consumed.with(Serdes.String(), inJsonSerde));
        source
                .map((KeyValueMapper<String, AlertDTO, KeyValue<String, Detection>>) (key, alertDTO) ->{
                    log.info("Inbound data: {}", alertDTO);
                    Detection detection = this.radarAlertToDetection(alertDTO);
                    log.info("Outbound data: {}", detection);
                    return new KeyValue<>(UUID.randomUUID().toString(), detection);
                })
                .to(kafkaProperties.getOutboundTopic(), Produced.with(Serdes.String(), outJsonSerde));

        Properties props = this.streamConfigProps();
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

    public Detection radarAlertToDetection(AlertDTO alertDTO){
        Detection detection = new Detection();
        detection.setSensorId(String.valueOf(alertDTO.getSensorId()));
        detection.setSourceName(alertDTO.getSensorName());
        detection.setLatitude(alertDTO.getLatitude());
        detection.setLongitude(alertDTO.getLongitude());
        detection.setDateTime(alertDTO.getDate());
        detection.setDescription(alertDTO.getType());
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("id", alertDTO.getId());
        additionalInfo.put("idTrace", alertDTO.getIdTrace());
        additionalInfo.put("bearing", alertDTO.getBearing());
        additionalInfo.put("range", alertDTO.getRange());
        additionalInfo.put("size", alertDTO.getSize());
        additionalInfo.put("speed", alertDTO.getSpeed());
        additionalInfo.put("sensorName", alertDTO.getSensorName());
        additionalInfo.put("sensorType", alertDTO.getSensorType());
        detection.setAdditionalInfo(additionalInfo);
        return detection;
    }

}
