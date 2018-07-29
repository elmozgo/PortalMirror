package org.portalmirror.deviceuser.portlet.beans;

/**
 * Class represents a bean used to keep track of the portlet settings
 */
public class Settings {
    private boolean randomSetting;
    
    public Settings() {
	this.randomSetting = false;
    }
    public Settings(boolean randomSetting) {
	this.randomSetting = randomSetting;
    }
    public boolean isRandomSetting() {
	return randomSetting;
    }
    public void setRandomSetting(boolean randomSetting) {
	this.randomSetting = randomSetting;
    }
}
