package com.innometrics.integration.app.ml.recommender.utils;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author andrew, Innometrics
 */
public class BeanValidationUtils {
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory();


    public static Set<ConstraintViolation<Object>> validate(Object toValidate) {
        Validator validator = VALIDATOR_FACTORY.getValidator();
        return validator.validate(toValidate);
    }

}
