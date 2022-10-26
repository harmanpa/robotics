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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.base.Splitter;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Modifier;
import tech.cae.javabard.BuilderSpec;
import tech.cae.javabard.GetterSpec;
import tech.cae.javabard.SetterSpec;
import tech.cae.robotics.mjcf.generator.schema.Attribute;
import tech.cae.robotics.mjcf.generator.schema.Element;
import tech.cae.robotics.mjcf.generator.schema.SchemaException;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
public class Generator {

    private String pkg = "tech.cae.robotics.mjcf";
    private final Map<String, TypeSpec.Builder> typeSpecs = new HashMap<>();
    private final Map<String, String> keywordTypes = new HashMap<>();

    @SuppressWarnings("UseSpecificCatch")
    public static void main(String[] args) {
        try {
            Generator g = new Generator();
            g.generate(new File(args[0]), new File(args[1]));
        } catch (Throwable ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

    public void generate(File schemaFile, File outDirectory) throws SchemaException, IOException {
        outDirectory.mkdir();
        XmlMapper xmlMapper = new XmlMapper();
        Element mujoco = xmlMapper.readValue(schemaFile, Element.class);
        Element mujocoX = xmlMapper.readValue(getClass().getClassLoader().getResourceAsStream("extras.xml"), Element.class);
        mujoco = mujoco.merge(mujocoX);
        Set<String> nonUniqueNames = new HashSet<>();
        findNonUniqueNames(mujoco, nonUniqueNames, new HashMap<>());
        handleElement(mujoco, new ArrayDeque<>(), nonUniqueNames);
        for (TypeSpec.Builder typeSpec : typeSpecs.values()) {
             TypeSpec finalSpec;
            if(typeSpec.build().kind==TypeSpec.Kind.ENUM) {
                finalSpec = typeSpec.build();
            } else {
                finalSpec = BuilderSpec.forType(pkg,
                    SetterSpec.forType(
                            GetterSpec.forType(typeSpec).build())
                            .build())
                    .build().build();
            }
            JavaFile.builder(pkg, finalSpec).skipJavaLangImports(true).build().writeTo(outDirectory);
        }
    }

    public ClassName handleElement(Element element, Deque<String> path, Set<String> nonUnique) throws SchemaException {
        String typeName = getTypeName(element, path, nonUnique);
        ClassName className = ClassName.bestGuess(typeName);
        if (!typeSpecs.containsKey(typeName)) {
            TypeSpec.Builder typeSpec = TypeSpec.classBuilder(ClassName.bestGuess(typeName))
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
            typeSpec.addSuperinterface(ClassName.bestGuess("tech.cae.robotics.mjcf.types.MJCFType"));
            if (!(element.getAttributes().isEmpty() && element.getElements().isEmpty())) {
                typeSpec.addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build());
            }
            typeSpec.addAnnotation(AnnotationSpec.builder(JsonInclude.class).addMember("value", "JsonInclude.Include.NON_EMPTY").build());
            typeSpec.addAnnotation(AnnotationSpec.builder(JacksonXmlRootElement.class).addMember("localName", "\"" + element.getName() + "\"").build());
            path.addLast(element.getName());
            for(Attribute a : element.getAttributes()) {
                ClassName attributeType = handleAttribute(a, path);
                FieldSpec.Builder attributeField = FieldSpec.builder(attributeType, toCamelCase(safeName(a.getName(), path)), Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(JsonProperty.class)
                                        .addMember("value", "\"" + a.getName() + "\"").build())
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                        .addMember("localName", "\"" + a.getName() + "\"").addMember("isAttribute", "true").build());
                        if (a.isRequired()) {
                            attributeField.addAnnotation(NotNull.class);
                        }
                    typeSpec.addField(attributeField.build());
            }
            for(Element e : element.getElements()) {
                if (e.getName() != null) {
                    ClassName elementType = handleElement(e, path, nonUnique);
                    FieldSpec.Builder elementField;
                    if (e.isRepeated()) {
                        elementField = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), elementType),
                                toCamelCase(e.getName()), Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlElementWrapper.class)
                                        .addMember("useWrapping", "false").build())
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                        .addMember("localName", "\"" + e.getName() + "\"").addMember("isAttribute", "false").build());
                        elementField.initializer("new $T<>()", ArrayList.class);
                    } else {
                        elementField = FieldSpec.builder(elementType, toCamelCase(e.getName()), Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                        .addMember("localName", "\"" + e.getName() + "\"").addMember("isAttribute", "false").build());
                    }
                    typeSpec.addField(elementField.build());
                }
            }
            if(element.isRecursive()) {
                FieldSpec.Builder elementField;
                    if (element.isRepeated()) {
                        elementField = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), className),
                                toCamelCase(element.getName()), Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlElementWrapper.class)
                                        .addMember("useWrapping", "false").build())
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                        .addMember("localName", "\"" + element.getName() + "\"").addMember("isAttribute", "false").build());
                        elementField.initializer("new $T<>()", ArrayList.class);
                    } else {
                        elementField = FieldSpec.builder(className, toCamelCase(element.getName()), Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                        .addMember("localName", "\"" + element.getName() + "\"").addMember("isAttribute", "false").build());
                    }
                    typeSpec.addField(elementField.build());
            }
            // Add include element
            typeSpec.addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), ClassName.bestGuess("tech.cae.robotics.mjcf.types.Include")),
                                "includes", Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlElementWrapper.class)
                                        .addMember("useWrapping", "false").build())
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                        .addMember("localName", "\"include\"").addMember("isAttribute", "false").build())
                                .initializer("new $T<>()", ArrayList.class).build());
            path.removeLast();
            typeSpec.addMethod(MethodSpec.methodBuilder("toString")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .returns(String.class)
                    .addAnnotation(Override.class)
                    .addCode("return tech.cae.robotics.mjcf.MJCFIO.toString(this);\n").build());
            typeSpec.addMethod(MethodSpec.methodBuilder("equals")
                    .returns(TypeName.BOOLEAN)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addParameter(Object.class, "other")
                    .addAnnotation(Override.class)
                    .addCode("return tech.cae.robotics.mjcf.MJCFIO.equals(this, other);\n").build());
            typeSpecs.put(typeName, typeSpec);
        }
        return className;
    }
    
    private static final List<String> KEYWORDS = Arrays.asList("abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "extends", "false",
                "final", "finally", "float", "for", "goto", "if", "implements",
                "import", "instanceof", "int", "interface", "long", "native",
                "new", "null", "package", "private", "protected", "public",
                "return", "short", "static", "strictfp", "super", "switch",
                "synchronized", "this", "throw", "throws", "transient", "true",
                "try", "void", "volatile", "while");
    
    public String safeName(String name, Deque<String> path) {
        if(KEYWORDS.contains(name) || Character.isDigit(name.charAt(0))) {
            return path.peekLast() + "_" + name;
        }
        return name;
    }

    public String getTypeName(Element element, Deque<String> path, Set<String> nonUnique) {
        String out = toUpperCamelCase(element.getName());
        if (nonUnique.contains(element.getName())) {
            out = toUpperCamelCase(path.peekLast()) + out;
        }
        return pkg + "." + out;
    }

    public String getTypeName(Attribute attribute, Deque<String> path) throws SchemaException {
        switch (attribute.getType()) {
            case "reference":
                return "tech.cae.robotics.mjcf.types.Reference";
            case "identifier":
                return "tech.cae.robotics.mjcf.types.Identifier";
            case "basepath":
            case "file":
                return "java.nio.file.Path";
            case "string":
                return "java.lang.String";
            case "array":
                return getArrayTypeName(attribute, path);
            case "float":
                return "java.lang.Double";
            case "keyword":
                return getKeywordTypeName(attribute, path);
            case "int":
                return "java.lang.Long";
        }
        throw new SchemaException("Could not decide what to do with type " + attribute.getType());
    }

    public String getKeywordTypeName(Attribute attribute, Deque<String> path) throws SchemaException {
        switch(attribute.getValidValues()) {
            case "false true":
                return "java.lang.Boolean";
            case "disable enable":
                return "tech.cae.robotics.mjcf.types.Flag";
            default:
        }
        if(keywordTypes.containsKey(attribute.getValidValues())) {
            return keywordTypes.get(attribute.getValidValues());
        }
        String typeName = pkg + "." + toUpperCamelCase(safeName(attribute.getName(), path));
        if(!typeSpecs.containsKey(typeName)) {
            // Make the type
            TypeSpec.Builder typeSpec = TypeSpec.enumBuilder(ClassName.bestGuess(typeName))
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ClassName.bestGuess("tech.cae.robotics.mjcf.types.MJCFEnum"))
                    .addMethod(MethodSpec.constructorBuilder()
                            .addParameter(String.class, "key")
                            .addCode("this.key = key;\n")
                            .build())
                    .addField(FieldSpec.builder(String.class, "key", Modifier.PRIVATE, Modifier.FINAL).build())
                    .addMethod(MethodSpec.methodBuilder("getKey")
                            .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                            .returns(String.class)
                            .addCode("return this.key;\n")
                            .addAnnotation(Override.class)
                            .addAnnotation(JsonValue.class)
                            .build())
                    .addMethod(MethodSpec.methodBuilder("fromString")
                            .addModifiers(Modifier.FINAL, Modifier.PUBLIC, Modifier.STATIC)
                            .returns(ClassName.bestGuess(typeName))
                            .addParameter(String.class, "key")
                            .addCode("return java.util.stream.Stream.of(values()).filter(value -> value.getKey().equals(key)).findFirst().orElse(null);\n")
                            .addAnnotation(JsonCreator.class)
                            .build());
            List<String> values = Splitter.on(' ').trimResults().omitEmptyStrings().splitToList(attribute.getValidValues());
            for(String value : values) {
                typeSpec.addEnumConstant(toUpperCamelCase(safeName(value, path)), TypeSpec.anonymousClassBuilder("$S", value).build());
            }
            typeSpecs.put(typeName, typeSpec);
            keywordTypes.put(attribute.getValidValues(), typeName);
        }
        return typeName;
    }

    public String getArrayTypeName(Attribute attribute, Deque<String> path) throws SchemaException {
        String name = attribute.getName();
        String arrayType = attribute.getArrayType();
        Integer arraySize = attribute.getArraySize() == null
                ? null
                : Integer.parseInt(attribute.getArraySize());
        switch (path.peekLast()) {
            case "rgba":
                return "tech.cae.robotics.mjcf.types.Color";
            default:
        }
        switch (name) {
            case "rgba":
            case "rgb1":
            case "rgb2":
            case "ambient":
            case "diffuse":
            case "specular":
                return "tech.cae.robotics.mjcf.types.Color";
            case "fromto":
                return "tech.cae.robotics.mjcf.types.FromTo";
            case "pos":
                return "tech.cae.robotics.mjcf.types.Pos";
            case "quat":
                return "tech.cae.robotics.mjcf.types.Quat";
            case "axisangle":
                return "tech.cae.robotics.mjcf.types.AxisAngle";
            case "xyaxes":
                return "tech.cae.robotics.mjcf.types.XYAxes";
            case "zaxis":
                return "tech.cae.robotics.mjcf.types.ZAxis";
            case "euler":
                return "tech.cae.robotics.mjcf.types.Euler";
            default:
        }
        switch(arrayType) {
            case "float":
                return "tech.cae.robotics.mjcf.types.FloatArray";
            case "int":
                return "tech.cae.robotics.mjcf.types.IntArray";
        }
        throw new SchemaException("Could not decide what to do with array type " + attribute.getArrayType());
    }

    public void findNonUniqueNames(Element element, Set<String> nonUniqueNames, Map<String, Set<String>> childNames) {
        String name = element.getName();
        if (!nonUniqueNames.contains(name)) {
            Set<String> children = elementChildren(element);
            if (childNames.containsKey(name)) {
                if (!children.equals(childNames.get(name))) {
                    // TODO: Check parent path as may be non-unique by n steps also
                    nonUniqueNames.add(name);
                }
            } else {
                childNames.put(name, children);
            }
        }
        element.getElements().forEach(e -> findNonUniqueNames(e, nonUniqueNames, childNames));
    }

    public Set<String> elementChildren(Element element) {
        Set<String> children = new HashSet<>();
        element.getAttributes().forEach(a -> children.add(a.getName()));
        element.getElements().forEach(e -> children.add(e.getName()));
        return children;
    }

    public ClassName handleAttribute(Attribute attribute, Deque<String> path) throws SchemaException {
        String typeName = getTypeName(attribute, path);
        ClassName className = ClassName.bestGuess(typeName);
        return className;
    }

    public String toCamelCase(String name) {
        String[] words = name.split("_");
        StringBuilder out = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            out.append(words[i].substring(0, 1).toUpperCase()).append(words[i].substring(1));
        }
        String result = out.toString();
        switch (result) {
            case "static":
                return "isStatic";
            case "default":
                return "isDefault";
        }
        return result;
    }

    public String toUpperCamelCase(String name) {
        String[] words = name.split("_");
        StringBuilder out = new StringBuilder(words[0].substring(0, 1).toUpperCase()).append(words[0].substring(1));
        for (int i = 1; i < words.length; i++) {
            out.append(words[i].substring(0, 1).toUpperCase()).append(words[i].substring(1));
        }
        return out.toString();
    }

    public String escapeHTML(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

}
