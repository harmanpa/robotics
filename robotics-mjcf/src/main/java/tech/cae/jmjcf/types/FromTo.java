/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jmjcf.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author peter
 */
@Schema(type = "string", pattern = "")
@JsonSerialize(using = FromTo.FromToSerializer.class)
@JsonDeserialize(using = FromTo.FromToDeserializer.class)
public class FromTo extends FloatArray {

    public FromTo(String str) {
        super(str);
    }

    public FromTo(float... data) {
        super(data);
    }

    public FromTo(double... data) {
        super(data);
    }

    static class FromToSerializer extends MJCFArrayType.MJCFArraySerializer<FromTo> {

        public FromToSerializer() {
            super(FromTo.class);
        }

    }

    static class FromToDeserializer extends MJCFArrayType.MJCFArrayDeserializer<FromTo> {

        public FromToDeserializer() {
            super(FromTo.class);
        }
    }
}
