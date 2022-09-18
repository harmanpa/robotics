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
package tech.cae.robotics.sdf.types;

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
 * @author Peter Harman peter.harman@cae.tech
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
