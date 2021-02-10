package com.tom.kafka.topics;

import com.tom.kafka.topics.dto.TopicRequest;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/kafka/topic")
public class KafkaTopicController {

    private final KafkaTopicService kafkaTopicService;

    public KafkaTopicController(KafkaTopicService kafkaAclService) {
        this.kafkaTopicService = kafkaAclService;
    }

    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateTopicsResult createTopics(@RequestBody @Valid TopicRequest topicRequest) {
        return kafkaTopicService.createTopics(topicRequest.getCreateTopics());
    }

    @ResponseBody
    @GetMapping(value= "/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    public DescribeTopicsResult describeTopics(@RequestBody TopicRequest topicRequest)  {
        return kafkaTopicService.describeTopics(topicRequest.getDescribeTopics());
    }

    @ResponseBody
    @DeleteMapping
    public DeleteTopicsResult deleteTopics(@RequestBody @Valid TopicRequest topicRequest) {
        return kafkaTopicService.deleteTopics(topicRequest.getDeleteTopics());
    }

}
