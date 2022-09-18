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
package tech.cae.robotics.mjcf.types;

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
 * @author Peter Harman peter.harman@cae.tech
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
