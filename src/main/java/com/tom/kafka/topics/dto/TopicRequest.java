package com.tom.kafka.topics.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class TopicRequest {

    private Collection<TopicDetails> createTopics = new ArrayList<>();
    private Collection<String> describeTopics = new ArrayList<>();
    private Collection<String> deleteTopics = new ArrayList<>();

}
