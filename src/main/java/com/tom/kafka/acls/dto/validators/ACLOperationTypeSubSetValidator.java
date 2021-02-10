package com.tom.kafka.acls.dto.validators;

import org.apache.kafka.common.acl.AclOperation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ACLOperationTypeSubSetValidator implements ConstraintValidator<ACLOperationTypeSubset, AclOperation> {
    private AclOperation[] subset;

    @Override
    public void initialize(ACLOperationTypeSubset constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(AclOperation value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset)
                .contains(value);
    }
}
