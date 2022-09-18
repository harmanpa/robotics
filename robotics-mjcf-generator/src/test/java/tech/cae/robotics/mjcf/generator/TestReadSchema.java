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
package tech.cae.robotics.mjcf.generator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import tech.cae.robotics.mjcf.generator.schema.Element;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
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
