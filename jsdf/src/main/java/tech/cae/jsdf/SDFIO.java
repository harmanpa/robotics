/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static Sdf load(String string) throws IOException {
        return getMapper().readValue(string, Sdf.class);
    }

    public static Sdf load(File file) throws IOException {
        return getMapper().readValue(file, Sdf.class);
    }

    public static Sdf load(InputStream is) throws IOException {
        return getMapper().readValue(is, Sdf.class);
    }

    public static void save(Sdf sdf, File file) throws IOException {
        getMapper().writeValue(file, sdf);
    }

    public static void save(Sdf sdf, OutputStream os) throws IOException {
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
