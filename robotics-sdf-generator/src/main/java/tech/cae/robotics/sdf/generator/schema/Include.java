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

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
public class Include extends AbstractSchemaElement {
    
    @JacksonXmlProperty(isAttribute = true)
    private String filename;
    
    public String getFilename() {
        return filename;
    }
    
    public void resolveInto(Element parent, File directory) throws SchemaException {
        File file = new File(directory, filename);
        XmlMapper xmlMapper = new XmlMapper();
        Element element;
        try {
            element = xmlMapper.readValue(file, Element.class);
        } catch (IOException ex) {
            throw new SchemaException("Error deserializing file " + filename, ex);
        }
        element.resolve(directory);
        element.setRequired(getRequired());
        parent.getElements().add(element);
    }
}
