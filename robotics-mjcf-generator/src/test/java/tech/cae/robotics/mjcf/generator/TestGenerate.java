/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.mjcf.generator;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import tech.cae.jmjcf.generator.Generator;
import tech.cae.jmjcf.generator.schema.SchemaException;

/**
 *
 * @author peter
 */
public class TestGenerate {

    @Test
    public void test() throws IOException, SchemaException {
        File schemaFile = new File(new File(System.getProperty("user.dir")), "target/schema/schema.xml");
        File out = new File(new File(System.getProperty("user.dir")), "target/testgen");
        new Generator().generate(schemaFile, out);
    }
}
