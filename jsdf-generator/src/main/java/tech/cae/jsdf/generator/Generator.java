/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.generator;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Modifier;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringEscapeUtils;
import tech.cae.javabard.BuilderSpec;
import tech.cae.javabard.GetterSpec;
import tech.cae.javabard.SetterSpec;
import tech.cae.jsdf.generator.schema.Attribute;
import tech.cae.jsdf.generator.schema.Element;
import tech.cae.jsdf.generator.schema.SchemaException;

/**
 *
 * @author peter
 */
public class Generator {

    private String pkg = "tech.cae.jsdf";
    private final Map<String, TypeSpec.Builder> typeSpecs = new HashMap<>();

    public static void main(String[] args) {
        try {
            Generator g = new Generator();
            g.generate(new File(args[0]), new File(args[1]), args[2]);
        } catch (Exception ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generate(File sdfDirectory, File outDirectory, String version) throws SchemaException, IOException {
        outDirectory.mkdir();
        this.pkg = this.pkg + ".v" + version.replace(".", "_");
        File worldFile = new File(sdfDirectory, "root.sdf");
        XmlMapper xmlMapper = new XmlMapper();
        Element world = xmlMapper.readValue(worldFile, Element.class);
        world.resolve(sdfDirectory);
        Set<String> nonUniqueNames = new HashSet<>();
        findNonUniqueNames(world, nonUniqueNames, new HashMap<>());
        handleElement(world, new ArrayDeque<>(), nonUniqueNames);
        for (TypeSpec.Builder typeSpec : typeSpecs.values()) {
            TypeSpec finalSpec = BuilderSpec.forType(pkg,
                    SetterSpec.forType(
                            GetterSpec.forType(typeSpec).build())
                            .build())
                    .build().build();
            JavaFile.builder(pkg, finalSpec).skipJavaLangImports(true).build().writeTo(outDirectory);
        }
    }

    public ClassName handleElement(Element element, Deque<String> path, Set<String> nonUnique) {
        String typeName = getTypeName(element, path, nonUnique);
        ClassName className = ClassName.bestGuess(typeName);
        if (element.getType() == null && element.getName() != null) {
            if (!typeSpecs.containsKey(typeName)) {
                TypeSpec.Builder typeSpec = TypeSpec.classBuilder(ClassName.bestGuess(typeName))
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
                switch (element.getName()) {
                    case "sdf":
                        typeSpec.addSuperinterface(ClassName.bestGuess("tech.cae.jsdf.types.SDFRootType"));
                        typeSpec.addAnnotation(AnnotationSpec.builder(AutoService.class)
                                .addMember("value", "tech.cae.jsdf.types.SDFRootType.class")
                                .build());
                        break;
                    case "state":
                        typeSpec.addSuperinterface(ClassName.bestGuess("tech.cae.jsdf.types.SDFStateType"));
                        typeSpec.addAnnotation(AnnotationSpec.builder(AutoService.class)
                                .addMember("value", "tech.cae.jsdf.types.SDFStateType.class")
                                .build());
                        break;
                    case "include":
                        typeSpec.addSuperinterface(ClassName.bestGuess("tech.cae.jsdf.types.SDFIncludeType"));
                        typeSpec.addAnnotation(AnnotationSpec.builder(AutoService.class)
                                .addMember("value", "tech.cae.jsdf.types.SDFIncludeType.class")
                                .build());
                        break;
                    default:
                        typeSpec.addSuperinterface(ClassName.bestGuess("tech.cae.jsdf.types.SDFType"));
                }
                if (!(element.getAttributes().isEmpty() && element.getElements().isEmpty())) {
                    typeSpec.addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build());
                }
                typeSpec.addAnnotation(AnnotationSpec.builder(JsonInclude.class).addMember("value", "JsonInclude.Include.NON_EMPTY").build());
                typeSpec.addAnnotation(AnnotationSpec.builder(JacksonXmlRootElement.class).addMember("localName", "\"" + element.getName() + "\"").build());
                if (element.getDescription() != null && element.getDescription().getDescriptionString() != null) {
                    typeSpec.addAnnotation(AnnotationSpec.builder(JsonClassDescription.class).addMember("value", "\"" + element.getDescription().getDescriptionString() + "\"").build());
                    typeSpec.addJavadoc(StringEscapeUtils.escapeHtml(element.getDescription().getDescriptionString()));
                }
                path.addLast(element.getName());
                element.getAttributes().forEach(a -> {
                    ClassName attributeType = handleAttribute(a, path);
                    FieldSpec.Builder attributeField;
                    if (a.isCollection()) {
                        attributeField = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), attributeType),
                                toCamelCase(a.getName()), Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(JsonProperty.class)
                                        .addMember("value", "\"" + a.getName() + "\"").build())
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                        .addMember("localName", "\"" + a.getName() + "\"").addMember("isAttribute", "true").build());
                        if (a.isRequired()) {
                            attributeField.addAnnotation(NotEmpty.class);
                        }
                        attributeField.initializer("new $T<>()", ArrayList.class);
                    } else {
                        attributeField = FieldSpec.builder(attributeType, toCamelCase(a.getName()), Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(JsonProperty.class)
                                        .addMember("value", "\"" + a.getName() + "\"").build())
                                .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                        .addMember("localName", "\"" + a.getName() + "\"").addMember("isAttribute", "true").build());
                        if (a.isRequired()) {
                            attributeField.addAnnotation(NotNull.class);
                        }
                        if (a.getDefaultValue() != null) {
                            switch (attributeType.toString()) {
                                case "java.lang.String":
                                    attributeField.initializer("\"" + a.getDefaultValue() + "\"");
                                    break;
                                case "java.lang.Double":
                                    attributeField.initializer(a.getDefaultValue() + "d");
                                    break;
                                case "java.lang.Long":
                                    attributeField.initializer(a.getDefaultValue() + "L");
                                    break;
                                default:
                                    attributeField.initializer("new " + attributeType.toString() + "(\"" + a.getDefaultValue() + "\")");
                            }
                        }
                    }
                    if (a.getDescription() != null) {
                        attributeField.addJavadoc(escapeHTML(a.getDescription().getDescriptionString()));
                        attributeField.addAnnotation(AnnotationSpec.builder(JsonPropertyDescription.class)
                                .addMember("value", "\"" + a.getDescription().getDescriptionString() + "\"").build());
                    }
                    typeSpec.addField(attributeField.build());
                });
                element.getElements().forEach(e -> {
                    if (e.getName() != null) {
                        ClassName elementType = handleElement(e, path, nonUnique);
                        FieldSpec.Builder elementField;
                        if (e.isCollection()) {
                            elementField = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), elementType),
                                    toCamelCase(e.getName()), Modifier.PRIVATE)
                                    .addAnnotation(AnnotationSpec.builder(JacksonXmlElementWrapper.class)
                                            .addMember("useWrapping", "false").build())
                                    .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                            .addMember("localName", "\"" + e.getName() + "\"").addMember("isAttribute", "false").build());
                            if (e.isRequired()) {
                                elementField.addAnnotation(NotEmpty.class);
                            }
                            elementField.initializer("new $T<>()", ArrayList.class);
                        } else {
                            elementField = FieldSpec.builder(elementType, toCamelCase(e.getName()), Modifier.PRIVATE)
                                    .addAnnotation(AnnotationSpec.builder(JacksonXmlProperty.class)
                                            .addMember("localName", "\"" + e.getName() + "\"").addMember("isAttribute", "false").build());
                            if (e.isRequired()) {
                                elementField.addAnnotation(NotNull.class);
                            }
                            if (e.getDefaultValue() != null) {
                                switch (elementType.toString()) {
                                    case "java.lang.String":
                                        elementField.initializer("\"" + e.getDefaultValue() + "\"");
                                        break;
                                    case "java.lang.Double":
                                        elementField.initializer(e.getDefaultValue() + "d");
                                        break;
                                    case "java.lang.Long":
                                        elementField.initializer(e.getDefaultValue() + "L");
                                        break;
                                    default:
                                        elementField.initializer("new " + elementType.toString() + "(\"" + e.getDefaultValue() + "\")");
                                }
                            }
                        }
                        if (e.getDescription() != null) {
                            elementField.addJavadoc(escapeHTML(e.getDescription().getDescriptionString()));
                            elementField.addAnnotation(AnnotationSpec.builder(JsonPropertyDescription.class)
                                    .addMember("value", "\"" + e.getDescription().getDescriptionString() + "\"").build());
                        }
                        typeSpec.addField(elementField.build());
                    }
                });
                path.removeLast();
                typeSpec.addMethod(MethodSpec.methodBuilder("toString")
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .returns(String.class)
                        .addAnnotation(Override.class)
                        .addCode("return tech.cae.jsdf.SDFIO.toString(this);\n").build());
                typeSpec.addMethod(MethodSpec.methodBuilder("equals")
                        .returns(TypeName.BOOLEAN)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addParameter(Object.class, "other")
                        .addAnnotation(Override.class)
                        .addCode("return tech.cae.jsdf.SDFIO.equals(this, other);\n").build());
                typeSpecs.put(typeName, typeSpec);
            }
        }
        return className;
    }

    public String getTypeName(Element element, Deque<String> path, Set<String> nonUnique) {
        if (element.getType() != null) {
            switch (element.getType()) {
                case "vector2d":
                    return "tech.cae.jsdf.types.Vector2D";
                case "vector3":
                    return "tech.cae.jsdf.types.Vector3D";
                case "pose":
                    return "tech.cae.jsdf.types.Pose";
                case "time":
                    return "tech.cae.jsdf.types.Time";
                case "string":
                    return "java.lang.String";
                case "bool":
                    return "tech.cae.jsdf.types.Boolean";
                case "int":
                case "unsigned int":
                    return "java.lang.Long";
                case "double":
                    return "java.lang.Double";
                case "color":
                    return "tech.cae.jsdf.types.Color";
            }
        }
        if ("true".equals(element.getCopyData())) {
            return "tech.cae.jsdf.types.CopyData";
        }
        if (element.getReference() != null) {
            if (nonUnique.contains(element.getReference())) {
                return pkg + "." + toUpperCamelCase(path.peekLast()) + toUpperCamelCase(element.getReference());
            }
            return pkg + "." + toUpperCamelCase(element.getReference());
        }
        String out;
        switch (element.getName()) {
            case "x":
            case "y":
            case "z":
                out = "XYZ";
                break;
            default:
                out = toUpperCamelCase(element.getName());
        }
        if (nonUnique.contains(element.getName())) {
            switch (path.peekLast()) {
                case "x":
                case "y":
                case "z":
                    out = "XYZ" + out;
                    break;
                default:
                    out = toUpperCamelCase(path.peekLast()) + out;
            }
        }
        return pkg + "." + out;
    }

    public String getTypeName(Attribute attribute) {
        if (attribute.getType() != null) {
            switch (attribute.getType()) {
                case "vector2d":
                    return "tech.cae.jsdf.types.Vector2D";
                case "vector3":
                    return "tech.cae.jsdf.types.Vector3D";
                case "pose":
                    return "tech.cae.jsdf.types.Pose";
                case "time":
                    return "tech.cae.jsdf.types.Time";
                case "string":
                    return "java.lang.String";
                case "bool":
                    return "tech.cae.jsdf.types.Boolean";
                case "int":
                case "unsigned int":
                    return "java.lang.Long";
                case "double":
                    return "java.lang.Double";
                case "color":
                    return "tech.cae.jsdf.types.Color";
            }
        }
        return "?";
    }

    public void findNonUniqueNames(Element element, Set<String> nonUniqueNames, Map<String, Set<String>> childNames) {
        String name = element.getName();
        String type = element.getType();
        String ref = element.getReference();
        if (name != null && type == null && ref == null) {
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
    }

    public Set<String> elementChildren(Element element) {
        Set<String> children = new HashSet<>();
        element.getAttributes().forEach(a -> children.add(a.getName()));
        element.getElements().forEach(e -> children.add(e.getName()));
        return children;
    }

    public ClassName handleAttribute(Attribute attribute, Deque<String> path) {
        String typeName = getTypeName(attribute);
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
