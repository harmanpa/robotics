/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.log;

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
import tech.cae.jsdf.SDFIO;
import tech.cae.jsdf.types.SDFRootType;
import tech.cae.jsdf.types.SDFStateType;

/**
 *
 * @author peter
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
