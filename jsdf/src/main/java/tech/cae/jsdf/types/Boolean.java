/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.types;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@Schema(type = "string", pattern = "^(0|1)$")
@JsonSerialize(using = Boolean.BooleanSerializer.class)
@JsonDeserialize(using = Boolean.BooleanDeserializer.class)
public class Boolean {

    private final boolean state;

    public Boolean(boolean state) {
        this.state = state;
    }

    public Boolean(String str) {
        this("1".equals(str.trim()));
    }

    public java.lang.Boolean state() {
        return state;
    }

    @Override
    public String toString() {
        return state ? "1" : "0";
    }

    static class BooleanSerializer extends StdSerializer<Boolean> {

        public BooleanSerializer() {
            super(Boolean.class);
        }

        @Override
        public void serialize(Boolean t, JsonGenerator jg, SerializerProvider sp) throws IOException {
            jg.writeString(t.toString());
        }

        @Override
        public boolean isEmpty(SerializerProvider provider, Boolean value) {
            return !value.state;
        }
    }

    static class BooleanDeserializer extends StdDeserializer<Boolean> {

        public BooleanDeserializer() {
            super(Boolean.class);
        }

        @Override
        public Boolean deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
            return new Boolean(jp.getValueAsString());
        }

    }
}
