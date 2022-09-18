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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import tech.cae.robotics.sdf.SDFIO;
import tech.cae.robotics.sdf.types.SDFRootType;
import tech.cae.robotics.sdf.types.SDFStateType;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
@JsonInclude(Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "gazebo_log")
public class GazeboLog {

    public static GazeboLog load(File file) throws IOException {
        return SDFIO.getMapper().readValue(file, GazeboLog.class);
    }

    public static GazeboLog load(InputStream is) throws IOException {
        return SDFIO.getMapper().readValue(is, GazeboLog.class);
    }

    public static void save(GazeboLog log, File file) throws IOException {
        SDFIO.getMapper().writeValue(file, log);
    }

    public static void save(GazeboLog log, OutputStream os) throws IOException {
        SDFIO.getMapper().writeValue(os, log);
    }

    @JacksonXmlProperty(isAttribute = false, localName = "header")
    private LogHeader header;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(isAttribute = false, localName = "chunk")
    private final List<LogChunk> chunks = new ArrayList<>();

    public LogHeader getHeader() {
        return header;
    }

    public void setHeader(LogHeader header) {
        this.header = header;
    }

    public List<LogChunk> getChunks() {
        return chunks;
    }

    @JsonIgnore
    public SDFRootType getSDF() throws IOException {
        if (chunks.isEmpty()) {
            return null;
        }
        return SDFIO.load(chunks.get(0).decompress());
    }

    public <S extends SDFRootType> S getSDF(Class<S> type) throws IOException {
        if (chunks.isEmpty()) {
            return null;
        }
        return SDFIO.getMapper().readValue(chunks.get(0).decompress(), type);
    }

    @JsonIgnore
    public <S extends SDFStateType> List<S> getStates() throws IOException {
        List<S> states = new ArrayList<>();
        for (LogChunk chunk : getChunks().subList(1, getChunks().size())) {
            states.addAll(SDFIO.getMapper().readValue(chunk.decompress(), LogState.class).getStates());
        }
        return states;
    }

}
