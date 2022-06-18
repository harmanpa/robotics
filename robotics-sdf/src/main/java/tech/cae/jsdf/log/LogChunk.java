/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.zip.DeflaterInputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author peter
 */
public class LogChunk {
//[txt, zlib, or bz2]

    @JacksonXmlProperty(isAttribute = true, localName = "encoding")
    private String encoding;
    @JacksonXmlCData
    @JacksonXmlText
    private String data;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getData() {
        return data;
    }

    @JsonIgnore
    public String decompress() throws IOException {
        switch (getEncoding()) {
            case "zlib":
                try (InputStream is = new DeflaterInputStream(new ByteArrayInputStream(Base64.getMimeDecoder().decode(getData())));
                        ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                IOUtils.copy(is, os);
                return os.toString();
            }
            case "bz2":
                throw new IOException("Unsupported encoding");
            default:
                return new String(Base64.getMimeDecoder().decode(data));
        }
    }

}
