package com.tom.kafka.topics;

import com.tom.kafka.topics.dto.TopicDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
public class KafkaTopicService {

    private final KafkaAdmin admin;
    private AdminClient client;

    @Autowired
    public KafkaTopicService(KafkaAdmin admin) {
        this.admin = admin;
    }

    @PostConstruct
    public void init() {
        client = AdminClient.create(admin.getConfigurationProperties());
    }

    public CreateTopicsResult createTopics(Collection<TopicDetails> topicRequest) {
        Collection<NewTopic> topics = new ArrayList<>();
        topicRequest.forEach(topicDescription -> {
            topics.add(
                    new NewTopic(topicDescription.getTopicName()
                            , topicDescription.getTopicPartitions()
                            , topicDescription.getReplicationFactor()));
        });
        return client.createTopics(topics);
    }

    public DescribeTopicsResult describeTopics(Collection<String> topicNames) {
        return client.describeTopics(topicNames);
    }

    public DeleteTopicsResult deleteTopics(Collection<String> topics) {
        return client.deleteTopics(topics);
    }

    @PreDestroy
    public void cleanup() {
        client.close();
    }
}
