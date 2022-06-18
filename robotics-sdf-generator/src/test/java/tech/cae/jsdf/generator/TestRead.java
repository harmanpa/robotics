/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.generator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import tech.cae.jsdf.generator.schema.Element;

/**
 *
 * @author peter
 */
public class TestRead {
    
    @Test
    public void test() {
        File resources = new File(new File(System.getProperty("user.dir")), "/src/test/resources");
        for (File sdfFile : resources.listFiles(f -> f.getName().endsWith(".sdf"))) {
            XmlMapper xmlMapper = new XmlMapper();
            Element element;
            try {
                element = xmlMapper.readValue(sdfFile, Element.class);
            } catch (IOException ex) {
                ex.printStackTrace();
                Assert.fail(ex.getMessage());
            }
        }
    }
}
