/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.mjcf.generator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import tech.cae.jmjcf.generator.Generator;
import tech.cae.jmjcf.generator.schema.Element;

/**
 *
 * @author peter
 */
public class TestReadSchema {

    @Test
    public void test() throws IOException {
        File schemaFile = new File(new File(System.getProperty("user.dir")), "target/schema/schema.xml");
        XmlMapper xmlMapper = new XmlMapper();
        Element mujoco = xmlMapper.readValue(schemaFile, Element.class);
        Set<String> nonUniqueNames = new HashSet<>();
        Map<String, Set<String>> childNames = new HashMap<>();
        new Generator().findNonUniqueNames(mujoco, nonUniqueNames, childNames);
        nonUniqueNames.forEach(nun -> System.out.println(nun));
    }

    @Test
    public void testAttributes() throws IOException {
        File schemaFile = new File(new File(System.getProperty("user.dir")), "target/schema/schema.xml");
        XmlMapper xmlMapper = new XmlMapper();
        Element mujoco = xmlMapper.readValue(schemaFile, Element.class);
        Set<String> attributeTypes = new HashSet<>();
        Set<String> keywordSets = new HashSet<>();
        Set<String> arrayTypes = new HashSet<>();
        getAttributeTypes(mujoco, attributeTypes);
        getKeywordSets(mujoco, keywordSets);
        getArrayTypes(mujoco, arrayTypes);
        attributeTypes.forEach(nun -> System.out.println(nun));
        keywordSets.forEach(nun -> System.out.println(nun));
        arrayTypes.forEach(nun -> System.out.println(nun));
    }

    public void getAttributeTypes(Element e, Set<String> types) {
        e.getAttributes().forEach(a -> types.add(a.getType()));
        e.getElements().forEach(element -> getAttributeTypes(element, types));
    }

    public void getKeywordSets(Element e, Set<String> types) {
        e.getAttributes().forEach(a -> {
            if (a.getValidValues() != null) {
                types.add(a.getValidValues());
            }
        });
        e.getElements().forEach(element -> getKeywordSets(element, types));
    }

    public void getArrayTypes(Element e, Set<String> types) {
        e.getAttributes().forEach(a -> {
            if (a.getArrayType() != null) {
                String arrayType = a.getArrayType();
                if (a.getArraySize() != null) {
                    types.add(arrayType + ":" + a.getArraySize());
                } else {
                    types.add(arrayType + ":*");
                }
            }
        });
        e.getElements().forEach(element -> getArrayTypes(element, types));
    }
}
