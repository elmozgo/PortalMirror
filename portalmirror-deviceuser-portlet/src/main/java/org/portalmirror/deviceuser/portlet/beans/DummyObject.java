package org.portalmirror.deviceuser.portlet.beans;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import org.portalmirror.deviceuser.portlet.keys.ErrorMessageKeys;

/**
 * Class represents a bean used as amodel object
 */
public class DummyObject implements Serializable{
    private static final long serialVersionUID = 1944819808522589560L;
    @NotBlank(message = ErrorMessageKeys.RANDOMSTRING_REQUIRED)
    private String randomString;

    public DummyObject() {
        this.randomString = "This is a default value";
    }
    public DummyObject(String randomString) {
        this.randomString = randomString;
    }
    public String getRandomString() {
        return randomString;
    }
    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }
}