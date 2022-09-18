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
package tech.cae.robotics.sdf.generator.schema;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tech.cae.robotics.sdf.generator.schema.Element.ElementDeserializer;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
@JsonDeserialize(using = ElementDeserializer.class)
@JacksonXmlRootElement(localName = "element")
public class Element extends AbstractElementOrAttribute {

    @JacksonXmlProperty(localName = "attribute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Attribute> attributes = new ArrayList<>();
    @JacksonXmlProperty(localName = "element")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Element> elements = new ArrayList<>();
    @JacksonXmlProperty(localName = "include")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Include> includes = new ArrayList<>();
    @JacksonXmlProperty(localName = "copy_data")
    private String copyData;
    @JacksonXmlProperty(localName = "ref")
    private String reference;

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Element> getElements() {
        return elements;
    }

    public List<Include> getIncludes() {
        return includes;
    }

    public String getCopyData() {
        return copyData;
    }

    public void setCopyData(String copyData) {
        this.copyData = copyData;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void resolve(File directory) throws SchemaException {
        try {
            for (Element child : getElements()) {
                child.resolve(directory);
            }
            for (Include include : getIncludes()) {
                include.resolveInto(this, directory);
            }
        } catch (SchemaException ex) {
            throw new SchemaException("Could not resolve include in element " + getName(), ex);
        }
        getIncludes().clear();
    }

    public static class ElementDeserializer extends StdDeserializer<Element> {

        protected ElementDeserializer() {
            super(Element.class);
        }

        @Override
        public Element deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
                throw new IOException("Invalid token, expected START_OBJECT");
            }
            Element element = new Element();

            while (jp.nextToken() != JsonToken.END_OBJECT) {
                final String key = jp.getCurrentName();
                jp.nextToken();
                switch (key) {
                    case "name":
                        element.setName(jp.readValueAs(String.class));
                        break;
                    case "type":
                        element.setType(jp.readValueAs(String.class));
                        break;
                    case "default":
                        element.setDefaultValue(jp.readValueAs(String.class));
                        break;
                    case "description":
                        element.setDescription(jp.readValueAs(Description.class));
                        break;
                    case "required":
                        element.setRequired(jp.readValueAs(String.class));
                        break;
                    case "element":
                        element.getElements().add(jp.readValueAs(Element.class));
                        break;
                    case "attribute":
                        element.getAttributes().add(jp.readValueAs(Attribute.class));
                        break;
                    case "include":
                        element.getIncludes().add(jp.readValueAs(Include.class));
                        break;
                    case "copy_data":
                        element.setCopyData(jp.readValueAs(String.class));
                        break;
                    case "ref":
                        element.setReference(jp.readValueAs(String.class));
                        break;
                    default:
                }
            }

            return element;
        }
    }
}
