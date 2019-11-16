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
import java.io.IOException;

/**
 *
 * @author peter
 */
@JsonSerialize(using = SDFArrayType.SDFArraySerializer.class)
@JsonDeserialize(using = SDFArrayType.SDFArrayDeserializer.class)
class SDFArrayType {

    private final double[] data;

    protected SDFArrayType(double... data) {
        this.data = data;
    }

    protected SDFArrayType(String str) {
        this(parse(str));
    }

    static double[] parse(String str) {
        String[] strs = str.trim().split("[\\w]+");
        double[] out = new double[strs.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = Double.parseDouble(strs[i]);
        }
        return out;
    }

    protected double[] getData() {
        return data;
    }

    @Override
    public String toString() {
        if (data.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(Double.toString(data[0]));
        for (int i = 1; i < data.length; i++) {
            sb.append(' ').append(data[i]);
        }
        return sb.toString();
    }

    static class SDFArraySerializer extends StdSerializer<SDFArrayType> {

        public SDFArraySerializer() {
            super(SDFArrayType.class);
        }

        @Override
        public void serialize(SDFArrayType t, JsonGenerator jg, SerializerProvider sp) throws IOException {
            jg.writeRaw(t.toString());
        }

    }

    static class SDFArrayDeserializer extends StdDeserializer<SDFArrayType> {

        public SDFArrayDeserializer() {
            super(SDFArrayType.class);
        }

        @Override
        public SDFArrayType deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
            return new SDFArrayType(jp.getText());
        }

    }

}
