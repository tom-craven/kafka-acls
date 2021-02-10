package com.tom.kafka.acls.dto.validators;

import org.apache.kafka.common.acl.AclPermissionType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ACLPermissionTypeSubSetValidator implements ConstraintValidator<ACLPermissionTypeSubset, AclPermissionType> {
    private AclPermissionType[] subset;

    @Override
    public void initialize(ACLPermissionTypeSubset constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(AclPermissionType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset)
                .contains(value);
    }
}
