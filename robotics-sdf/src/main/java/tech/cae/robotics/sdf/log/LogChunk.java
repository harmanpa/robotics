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
package tech.cae.robotics.sdf.log;

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
 * @author Peter Harman peter.harman@cae.tech
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
