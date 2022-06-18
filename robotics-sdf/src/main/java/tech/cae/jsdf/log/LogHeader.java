/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.log;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 *
 * @author peter
 */
public class LogHeader {

    @JacksonXmlProperty(isAttribute = false, localName = "log_version")
    private String logVersion;
    @JacksonXmlProperty(isAttribute = false, localName = "gazebo_version")
    private String gazeboVersion;
    @JacksonXmlProperty(isAttribute = false, localName = "rand_seed")
    private String randomSeed;

    public String getLogVersion() {
        return logVersion;
    }

    public void setLogVersion(String logVersion) {
        this.logVersion = logVersion;
    }

    public String getGazeboVersion() {
        return gazeboVersion;
    }

    public void setGazeboVersion(String gazeboVersion) {
        this.gazeboVersion = gazeboVersion;
    }

    public String getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(String randomSeed) {
        this.randomSeed = randomSeed;
    }

}
