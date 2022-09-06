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
@JsonSerialize(using = Reference.ReferenceSerializer.class)
@JsonDeserialize(using = Reference.ReferenceDeserializer.class)
public class Reference {

    private final String reference;

    public Reference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    static class ReferenceSerializer extends StdSerializer<Reference> {

        public ReferenceSerializer() {
            super(Reference.class);
        }

        @Override
        public void serialize(Reference reference, JsonGenerator jg, SerializerProvider sp) throws IOException {
            jg.writeString(reference.getReference());
        }

    }

    static class ReferenceDeserializer extends StdDeserializer<Reference> {

        public ReferenceDeserializer() {
            super(Reference.class);
        }

        @Override
        public Reference deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
            return new Reference(jp.getValueAsString());
        }
    }
}
