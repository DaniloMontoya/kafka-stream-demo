package com.devcodes.demokafkastream.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Ing. Danilo Montoya Hernandez(Galy);
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>demo-kafka-stream</b> 07/02/2023, 11:16 AM
 **/
@Data
public class Detection {
    private String sourceName;
    private String sensorId;
    private double latitude;
    private double longitude;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    private String description;
    private Map<String, Object> additionalInfo;
}
