/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.log;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;
import tech.cae.jsdf.State;

/**
 *
 * @author peter
 */
@JacksonXmlRootElement(localName = "sdf")
public class LogState {

    @JacksonXmlProperty(isAttribute = true, localName = "version")
    private String version;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(isAttribute = false, localName = "state")
    private final List<State> states = new ArrayList<>();

    public String getVersion() {
        return version;
    }

    public List<State> getStates() {
        return states;
    }

}
