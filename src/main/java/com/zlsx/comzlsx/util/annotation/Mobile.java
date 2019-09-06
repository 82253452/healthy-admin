package com.zlsx.comzlsx.util.annotation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * @author : houxm
 * @date : 2018/12/7 16:41
 * @description :
 */
@ConstraintComposition(CompositionType.OR)
@Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}")
@Null
@Length(min = 0, max = 0)
@Documented
@Constraint(validatedBy = {})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface Mobile {
    String message() default "请输入正确的手机号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
