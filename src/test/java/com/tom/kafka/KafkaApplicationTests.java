package com.tom.kafka;


import com.tom.kafka.acls.KafkaAclService;
import com.tom.kafka.acls.dto.AclRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.CreateAclsResult;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

// no assertions yet just a demo
@Slf4j
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaApplicationTests {

    @Autowired
    KafkaAclService kafkaAclService;
    private AclRequest testTopicAcls;

    @BeforeAll
    public void init() throws UnknownHostException {
        String host = "*";
        String principle = "User:Tom";
        String hostname = InetAddress.getLocalHost().getHostName();

        testTopicAcls = AclRequest.builder()
                .topicName("dev-topic")
                .aclOperation(AclOperation.ALL)
                .permissionType(AclPermissionType.ALLOW)
                .hostname(host)
                .principle(principle)
                .build();
    }

    @Test
    void A_itshouldCreateACL() throws ExecutionException, InterruptedException {
        CreateAclsResult result = kafkaAclService.createACL(testTopicAcls);
        log.info("Updated System Topic Acl: {}", result.values().toString());
        log.info("current acls are: {}", kafkaAclService.describeAcls());
    }

    @Test
    void B_itshouldReadACL() throws ExecutionException, InterruptedException {
        kafkaAclService.describeAcls();
    }

    @Test
    void C_itshouldDeleteACL() {
        kafkaAclService.deleteAcls(testTopicAcls);
    }
}
