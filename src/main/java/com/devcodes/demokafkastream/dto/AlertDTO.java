package com.devcodes.demokafkastream.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ing. Danilo Montoya Hernandez(Galy);
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>demo-kafka-stream</b> 07/02/2023, 11:25 AM
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertDTO {
    private int id;
    private Integer idTrace;
    private String type;
    private String sensorId;
    private LocalDateTime date;
    private Double bearing;
    private Double range;
    private Integer size;
    private Integer speed;
    private Integer heading;
    private String sensorName;
    private String sensorType;
    private Double latitude;
    private Double longitude;
}
