/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jmjcf.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.nio.file.Path;

/**
 *
 * @author peter
 */
public class Include implements MJCFType {

    public Include() {
    }

    public Include(Path file) {
        this.file = file;
    }

    @JsonProperty("file")
    @JacksonXmlProperty(
            localName = "file",
            isAttribute = true
    )
    private Path file;

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

}
