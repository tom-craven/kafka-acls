package com.tom.kafka.acls.dto;

import com.tom.kafka.acls.dto.validators.ACLOperationTypeSubset;
import com.tom.kafka.acls.dto.validators.ACLPermissionTypeSubset;
import lombok.Builder;
import lombok.Data;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AclRequest implements IAclRequest {

    @NotEmpty
    private String topicName;

    @ACLPermissionTypeSubset(anyOf = {
            AclPermissionType.ALLOW,
            AclPermissionType.DENY,
            AclPermissionType.ANY
    })
    private AclPermissionType permissionType;

    @ACLOperationTypeSubset(anyOf = {
            AclOperation.ALL,
            AclOperation.ALTER,
            AclOperation.ANY,
            AclOperation.CREATE,
            AclOperation.DELETE
    })
    private AclOperation aclOperation;

    @NotEmpty
    private String hostname;

    @NotEmpty
    private String principle;

}
