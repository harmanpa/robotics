/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jmjcf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import tech.cae.jmjcf.types.MJCFType;

/**
 * https://mujoco.readthedocs.io/en/latest/XMLreference.html
 * @author peter
 */
public class MJCFIO {

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

    public static Mujoco load(String string) throws IOException {
        return load(string, Mujoco.class);
    }

    public static Mujoco load(File file) throws IOException {
        return load(Files.readAllLines(file.toPath()).stream().reduce("", (a, b) -> a + "\n" + b), Mujoco.class);
    }

    public static Mujoco load(InputStream is) throws IOException {
        return load(new String(ByteStreams.toByteArray(is), "UTF-8"), Mujoco.class);
    }

    public static <M extends MJCFType> M load(String string, Class<M> type) throws IOException {
        return getMapper().readValue(string, type);
    }

    public static <M extends MJCFType> M load(File file, Class<M> type) throws IOException {
        return getMapper().readValue(file, type);
    }

    public static <M extends MJCFType> M load(InputStream is, Class<M> type) throws IOException {
        return getMapper().readValue(is, type);
    }

    public static <M extends MJCFType> void save(M mjcf, File file) throws IOException {
        getMapper().writeValue(file, mjcf);
    }

    public static <M extends MJCFType> void save(M mjcf, OutputStream os) throws IOException {
        getMapper().writeValue(os, mjcf);
    }

    public static String toString(MJCFType obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean equals(MJCFType a, Object b) {
        if (b instanceof MJCFType) {
            return toString(a).equals(toString((MJCFType) b));
        }
        return false;
    }
}
