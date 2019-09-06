package com.zlsx.comzlsx.util.util;


import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.ResultCode;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author houxm
 * @version 1.01 2018/5/30 15:13
 * @description
 */
public final class ValidateUtils {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static <T> List<String> validate(T t) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }

    public static <T> void validateThrowsForeseenException(T t) throws ForeseenException {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations == null || constraintViolations.isEmpty())
            return;
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            throw new ForeseenException(ResultCode.PARAMS_ERROR, constraintViolation.getMessage());
        }
    }
}
