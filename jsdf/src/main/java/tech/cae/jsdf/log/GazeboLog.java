/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import tech.cae.jsdf.Sdf;
import tech.cae.jsdf.State;

/**
 *
 * @author peter
 */
@JsonInclude(Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "gazebo_log")
public class GazeboLog {

    private static final XmlMapper XMLMAPPER = new XmlMapper();

    public static GazeboLog load(File file) throws IOException {
        return XMLMAPPER.readValue(file, GazeboLog.class);
    }

    public static GazeboLog load(InputStream is) throws IOException {
        return XMLMAPPER.readValue(is, GazeboLog.class);
    }

    public static void save(GazeboLog log, File file) throws IOException {
        XMLMAPPER.writeValue(file, log);
    }

    public static void save(GazeboLog log, OutputStream os) throws IOException {
        XMLMAPPER.writeValue(os, log);
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
    public Sdf getSDF() throws IOException {
        if (chunks.isEmpty()) {
            return null;
        }
        return XMLMAPPER.readValue(chunks.get(0).decompress(), Sdf.class);
    }

    @JsonIgnore
    public List<State> getStates() throws IOException {
        List<State> states = new ArrayList<>();
        for (LogChunk chunk : getChunks().subList(1, getChunks().size())) {
            states.addAll(XMLMAPPER.readValue(chunk.decompress(), LogState.class).getStates());
        }
        return states;
    }

}