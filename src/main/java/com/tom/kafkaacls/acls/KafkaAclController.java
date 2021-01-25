package com.tom.kafkaacls.acls;

import com.tom.kafkaacls.acls.dto.IAclRequest;
import org.apache.kafka.clients.admin.CreateAclsResult;
import org.apache.kafka.clients.admin.DeleteAclsResult;
import org.apache.kafka.common.acl.AclBinding;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/topic/acl")
public class KafkaAclController {

    private final KafkaAclService kafkaAclService;

    public KafkaAclController(KafkaAclService kafkaAclService) {
        this.kafkaAclService = kafkaAclService;
    }

    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateAclsResult createTopicAclRequest(@RequestBody @Valid IAclRequest createTopicAclRequest) {
        return kafkaAclService.createACL(createTopicAclRequest);
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AclBinding> getAcls() throws ExecutionException, InterruptedException {
        return kafkaAclService.describeAcls();
    }

    @ResponseBody
    @DeleteMapping
    public DeleteAclsResult deleteAcl(@RequestBody @Valid IAclRequest deleteTopicAclRequest) {
        return kafkaAclService.deleteAcls(deleteTopicAclRequest);
    }

}
