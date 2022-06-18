/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import tech.cae.jsdf.types.SDFRootType;
import tech.cae.jsdf.types.SDFType;

/**
 *
 * @author peter
 */
public class SDFIO {

    private static XmlMapper XMLMAPPER;

    public static XmlMapper getMapper() {
        if (XMLMAPPER == null) {
            XMLMAPPER = new XmlMapper();
            XMLMAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
            XMLMAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
            XMLMAPPER.getFactory().getXMLOutputFactory().setProperty(com.ctc.wstx.api.WstxOutputProperties.P_USE_DOUBLE_QUOTES_IN_XML_DECL, false);
        }
        return XMLMAPPER;
    }

    public static String sniffVersion(String string) throws JsonProcessingException {
        return sniffVersion(getMapper().readTree(string));
    }

    public static String sniffVersion(File file) throws IOException {
        return sniffVersion(getMapper().readTree(file));
    }

    public static String sniffVersion(InputStream is) throws IOException {
        return sniffVersion(getMapper().readTree(is));
    }

    private static String sniffVersion(JsonNode tree) {
        return tree == null ? null : tree.get("version").asText();
    }

    public static Map<String, Class<? extends SDFRootType>> rootTypes() {
        Map<String, Class<? extends SDFRootType>> types = new HashMap<>();
        ServiceLoader.load(SDFRootType.class).forEach(roottype -> types.put(roottype.getVersion(), roottype.getClass()));
        return types;
    }

    public static Set<String> supportedVersions() {
        return rootTypes().keySet();
    }

    private static SDFRootType mapToType(String string) throws IOException {
        String version = sniffVersion(string);
        if (version == null) {
            throw new IOException("No version specified in source");
        }
        Map<String, Class<? extends SDFRootType>> rootTypes = rootTypes();
        if (!rootTypes.containsKey(version)) {
            throw new IOException("Version " + version + " not supported");
        }
        return getMapper().readValue(string, rootTypes.get(version));
    }

    public static SDFRootType load(String string) throws IOException {
        return mapToType(string);
    }

    public static SDFRootType load(File file) throws IOException {
        return mapToType(Files.readAllLines(file.toPath()).stream().reduce("", (a, b) -> a + "\n" + b));
    }

    public static SDFRootType load(InputStream is) throws IOException {
        return mapToType(new String(ByteStreams.toByteArray(is), "UTF-8"));
    }

    public static <S extends SDFRootType> S load(String string, Class<S> type) throws IOException {
        return getMapper().readValue(string, type);
    }

    public static <S extends SDFRootType> S load(File file, Class<S> type) throws IOException {
        return getMapper().readValue(file, type);
    }

    public static <S extends SDFRootType> S load(InputStream is, Class<S> type) throws IOException {
        return getMapper().readValue(is, type);
    }

    public static <S extends SDFRootType> void save(S sdf, File file) throws IOException {
        getMapper().writeValue(file, sdf);
    }

    public static <S extends SDFRootType> void save(S sdf, OutputStream os) throws IOException {
        getMapper().writeValue(os, sdf);
    }

    public static String toString(SDFType obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean equals(SDFType a, Object b) {
        if (b instanceof SDFType) {
            return toString(a).equals(toString((SDFType) b));
        }
        return false;
    }
}
