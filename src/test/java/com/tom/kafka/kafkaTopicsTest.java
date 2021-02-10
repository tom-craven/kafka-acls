package com.tom.kafka;


import com.tom.kafka.topics.KafkaTopicService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

// no assertions yet just a demo
@Slf4j
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class kafkaTopicsTest {

    @Autowired
    KafkaTopicService kafkaTopicService;
    @BeforeAll
    public void init() {
    }

    @Test
    void A_itshouldCreateTopic()  {
        //  log.info("Updated System Topic Acl: {}", result.values().toString());
    }

    @Test
    void B_itshouldReadTopicDescription() {
    }

    @Test
    void C_itshouldDeleteTopic() {
    }
}
