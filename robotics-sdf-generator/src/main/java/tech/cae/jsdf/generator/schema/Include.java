/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.generator.schema;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author peter
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
