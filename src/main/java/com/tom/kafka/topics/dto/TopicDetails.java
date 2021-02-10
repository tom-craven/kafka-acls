package com.tom.kafka.topics.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicDetails {

    private String topicName;
    private short replicationFactor;
    private int topicPartitions;
}
