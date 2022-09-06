/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jmjcf.generator.schema;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.Objects;

/**
 *
 * @author peter
 */
public class Attribute extends AbstractElementOrAttribute {

    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;
    @JacksonXmlProperty(localName = "reference_namespace", isAttribute = true)
    private String referenceNamespace;
    @JacksonXmlProperty(localName = "path_namespace", isAttribute = true)
    private String pathNamespace;
    @JacksonXmlProperty(localName = "array_type", isAttribute = true)
    private String arrayType;
    @JacksonXmlProperty(localName = "array_size", isAttribute = true)
    private String arraySize;
    @JacksonXmlProperty(localName = "valid_values", isAttribute = true)
    private String validValues;
    @JacksonXmlProperty(localName = "required", isAttribute = true)
    private boolean required;
    @JacksonXmlProperty(localName = "conflict_allowed", isAttribute = true)
    private boolean conflictAllowed;
    @JacksonXmlProperty(localName = "conflict_behavior", isAttribute = true)
    private String conflictBehaviour;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReferenceNamespace() {
        return referenceNamespace;
    }

    public void setReferenceNamespace(String referenceNamespace) {
        this.referenceNamespace = referenceNamespace;
    }

    public String getPathNamespace() {
        return pathNamespace;
    }

    public void setPathNamespace(String pathNamespace) {
        this.pathNamespace = pathNamespace;
    }

    public String getArrayType() {
        return arrayType;
    }

    public void setArrayType(String arrayType) {
        this.arrayType = arrayType;
    }

    public String getArraySize() {
        return arraySize;
    }

    public void setArraySize(String arraySize) {
        this.arraySize = arraySize;
    }

    public String getValidValues() {
        return validValues;
    }

    public void setValidValues(String validValues) {
        this.validValues = validValues;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isConflictAllowed() {
        return conflictAllowed;
    }

    public void setConflictAllowed(boolean conflictAllowed) {
        this.conflictAllowed = conflictAllowed;
    }

    public String getConflictBehaviour() {
        return conflictBehaviour;
    }

    public void setConflictBehaviour(String conflictBehaviour) {
        this.conflictBehaviour = conflictBehaviour;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.type);
        hash = 43 * hash + Objects.hashCode(this.referenceNamespace);
        hash = 43 * hash + Objects.hashCode(this.pathNamespace);
        hash = 43 * hash + Objects.hashCode(this.arrayType);
        hash = 43 * hash + Objects.hashCode(this.arraySize);
        hash = 43 * hash + Objects.hashCode(this.validValues);
        hash = 43 * hash + (this.required ? 1 : 0);
        hash = 43 * hash + (this.conflictAllowed ? 1 : 0);
        hash = 43 * hash + Objects.hashCode(this.conflictBehaviour);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attribute other = (Attribute) obj;
        if (this.required != other.required) {
            return false;
        }
        if (this.conflictAllowed != other.conflictAllowed) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.referenceNamespace, other.referenceNamespace)) {
            return false;
        }
        if (!Objects.equals(this.pathNamespace, other.pathNamespace)) {
            return false;
        }
        if (!Objects.equals(this.arrayType, other.arrayType)) {
            return false;
        }
        if (!Objects.equals(this.arraySize, other.arraySize)) {
            return false;
        }
        if (!Objects.equals(this.validValues, other.validValues)) {
            return false;
        }
        return Objects.equals(this.conflictBehaviour, other.conflictBehaviour);
    }

}
