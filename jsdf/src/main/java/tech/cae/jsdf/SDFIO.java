/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author peter
 */
public class SDFIO {

    private static final XmlMapper XMLMAPPER = new XmlMapper();

    public static Sdf load(File file) throws IOException {
        return XMLMAPPER.readValue(file, Sdf.class);
    }

    public static Sdf load(InputStream is) throws IOException {
        return XMLMAPPER.readValue(is, Sdf.class);
    }

    public static void save(Sdf sdf, File file) throws IOException {
        XMLMAPPER.writeValue(file, sdf);
    }

    public static void save(Sdf sdf, OutputStream os) throws IOException {
        XMLMAPPER.writeValue(os, sdf);
    }
}
