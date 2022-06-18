/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.generator.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author peter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Description {

    @JacksonXmlCData
    @JacksonXmlText
    private String descriptionString;

    public String getDescriptionString() {
        return StringEscapeUtils.escapeJava(descriptionString);
    }

    public void setDescriptionString(String descriptionString) {
        this.descriptionString = descriptionString;
    }
}
