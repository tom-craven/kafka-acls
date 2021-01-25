package com.tom.kafkaacls.acls;

import com.tom.kafkaacls.acls.dto.IAclRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateAclsResult;
import org.apache.kafka.clients.admin.DeleteAclsOptions;
import org.apache.kafka.clients.admin.DeleteAclsResult;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AccessControlEntryFilter;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourcePatternFilter;
import org.apache.kafka.common.resource.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class KafkaAclService {

    private final KafkaAdmin admin;
    private AdminClient client;
    private static final ResourceType TOPIC_RESOURCE_TYPE = ResourceType.TOPIC;
    private static final PatternType LITERAL_PATTERN = PatternType.LITERAL;

    @Autowired
    public KafkaAclService(KafkaAdmin admin) {
        this.admin = admin;
    }

    @PostConstruct
    public void init() {
        client = AdminClient.create(admin.getConfigurationProperties());
    }

    public CreateAclsResult createACL(@Valid IAclRequest createTopicAclRequest) {
        AclBinding aclBinding = getAclBinding(createTopicAclRequest);

        return client.createAcls(Collections.singleton(aclBinding));
    }

    private AclBinding getAclBinding(IAclRequest createTopicAclRequest) {
        ResourcePattern pattern = new ResourcePattern(TOPIC_RESOURCE_TYPE, createTopicAclRequest.getTopicName(), LITERAL_PATTERN);
        AccessControlEntry entry = new AccessControlEntry(createTopicAclRequest.getPrinciple()
                , createTopicAclRequest.getHostname(), createTopicAclRequest.getAclOperation()
                , createTopicAclRequest.getPermissionType());

        return new AclBinding(pattern, entry);
    }

    public Collection<AclBinding> describeAcls() throws ExecutionException, InterruptedException {
        Collection<AclBinding> aclBindings = client.describeAcls(AclBindingFilter.ANY).values().get();
        log.info("ACLS: {}", aclBindings.toString());

        return aclBindings;
    }

    public DeleteAclsResult deleteAcls(IAclRequest iAclRequest) {

        AclBinding aclBinding = getAclBinding(iAclRequest);

        ResourcePatternFilter resourcePatternFilter = new ResourcePatternFilter(
                aclBinding.pattern().resourceType()
                , aclBinding.pattern().name()
                , aclBinding.pattern().patternType());

        AccessControlEntryFilter accessControlEntryFilter = new AccessControlEntryFilter(
                aclBinding.entry().principal()
                , aclBinding.entry().host()
                , aclBinding.entry().operation()
                , aclBinding.entry().permissionType()
        );

        AclBindingFilter aclBindingFilter = new AclBindingFilter(resourcePatternFilter, accessControlEntryFilter);

        DeleteAclsOptions options = new DeleteAclsOptions();

        return client.deleteAcls(Collections.singleton(aclBindingFilter), options);
    }

    @PreDestroy
    public void cleanup() {
        client.close();
    }
}
