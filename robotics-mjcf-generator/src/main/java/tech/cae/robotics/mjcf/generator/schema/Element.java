/*
 * The MIT License
 *
 * Copyright 2022- CAE Tech Limited
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.cae.robotics.mjcf.generator.schema;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
public class Element extends AbstractElementOrAttribute {

    @JacksonXmlProperty(localName = "repeated", isAttribute = true)
    private boolean repeated;
    @JacksonXmlProperty(localName = "recursive", isAttribute = true)
    private boolean recursive;
    @JacksonXmlProperty(localName = "on_demand", isAttribute = true)
    private boolean onDemand;
    @JacksonXmlProperty(localName = "namespace", isAttribute = true)
    private String namespace;
    @JacksonXmlProperty(localName = "attribute")
    @JacksonXmlElementWrapper(useWrapping = true, localName = "attributes")
    private final List<Attribute> attributes = new ArrayList<>();
    @JacksonXmlProperty(localName = "element")
    @JacksonXmlElementWrapper(useWrapping = true, localName = "children")
    private final List<Element> elements = new ArrayList<>();

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public boolean isOnDemand() {
        return onDemand;
    }

    public void setOnDemand(boolean onDemand) {
        this.onDemand = onDemand;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.repeated ? 1 : 0);
        hash = 11 * hash + (this.recursive ? 1 : 0);
        hash = 11 * hash + (this.onDemand ? 1 : 0);
        hash = 11 * hash + Objects.hashCode(this.namespace);
        hash = 11 * hash + Objects.hashCode(this.attributes);
        hash = 11 * hash + Objects.hashCode(this.elements);
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
        final Element other = (Element) obj;
        if (this.repeated != other.repeated) {
            return false;
        }
        if (this.recursive != other.recursive) {
            return false;
        }
        if (this.onDemand != other.onDemand) {
            return false;
        }
        if (!Objects.equals(this.namespace, other.namespace)) {
            return false;
        }
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        return Objects.equals(this.elements, other.elements);
    }

}
