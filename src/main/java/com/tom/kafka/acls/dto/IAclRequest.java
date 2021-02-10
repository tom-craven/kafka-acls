package com.tom.kafka.acls.dto;

import org.apache.kafka.common.acl.AclOperation;

public interface IAclRequest {

    String getHostname();

    String getPrinciple();

    org.apache.kafka.common.acl.AclPermissionType getPermissionType();

    String getTopicName();

    AclOperation getAclOperation();
}
