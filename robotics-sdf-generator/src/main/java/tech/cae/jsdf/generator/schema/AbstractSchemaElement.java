/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.generator.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 *
 * @author peter
 */
public abstract class AbstractSchemaElement {

    @JacksonXmlProperty(localName = "required", isAttribute = true)
    private String required;
    @JacksonXmlProperty(localName = "description", isAttribute = false)
    private Description description;

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @JsonIgnore
    public boolean isCollection() {
        return "+".equals(required) || "*".equals(required);
    }

    @JsonIgnore
    public boolean isRequired() {
        return "+".equals(required) || "1".equals(required);
    }
}
