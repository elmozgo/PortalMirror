package org.portalmirror.deviceuser.portlet.validators;

import org.portalmirror.deviceuser.portlet.beans.DummyObject;
import org.portalmirror.deviceuser.portlet.keys.ErrorMessageKeys;
import org.portalmirror.deviceuser.portlet.keys.ParameterKeys;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * validator class used to validate user input
 */
public class DemoValidator implements Validator {

    /**
     * Validator supports method
     *
     * @param aClass object to check
     * @return result of support validation
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return DummyObject.class.equals(aClass);
    }

    /**
     * Validate a DummyObject
     *
     * @param o DummyObject object to be validated
     * @param errors errors object to add errors to
     */
    @Override
    public void validate(Object o, Errors errors) {
        DummyObject dummyObject = (DummyObject) o;

        if (!dummyObject.getRandomString().matches("wee\\d{2}")) {
            errors.rejectValue(ParameterKeys.RANDOMSTRING, "NotValid", ErrorMessageKeys.ILLEGAL_DUMMYTEXT);
        }
    }
}
