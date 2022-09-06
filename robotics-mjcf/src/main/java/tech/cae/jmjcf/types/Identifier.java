/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jmjcf.types;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;

/**
 *
 * @author peter
 */
@Schema(type = "string", pattern = "")
@JsonSerialize(using = Identifier.IdentifierSerializer.class)
@JsonDeserialize(using = Identifier.IdentifierDeserializer.class)
public class Identifier {
    
    private final String identifier;
    
    public Identifier(String identifier) {
        this.identifier = identifier;
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    static class IdentifierSerializer extends StdSerializer<Identifier> {
        
        public IdentifierSerializer() {
            super(Identifier.class);
        }
        
        @Override
        public void serialize(Identifier identifier, JsonGenerator jg, SerializerProvider sp) throws IOException {
            jg.writeString(identifier.getIdentifier());
        }
        
    }
    
    static class IdentifierDeserializer extends StdDeserializer<Identifier> {
        
        public IdentifierDeserializer() {
            super(Identifier.class);
        }
        
        @Override
        public Identifier deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
            return new Identifier(jp.getValueAsString());
        }
    }
}
