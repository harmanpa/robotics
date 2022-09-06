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
@JsonSerialize(using = ZAxis.ZAxisSerializer.class)
@JsonDeserialize(using = ZAxis.ZAxisDeserializer.class)
public class ZAxis extends FloatArray {

    public ZAxis(String str) {
        super(str);
    }

    public ZAxis(float... data) {
        super(data);
    }

    public ZAxis(double... data) {
        super(data);
    }

    static class ZAxisSerializer extends MJCFArrayType.MJCFArraySerializer<ZAxis> {

        public ZAxisSerializer() {
            super(ZAxis.class);
        }

    }

    static class ZAxisDeserializer extends MJCFArrayType.MJCFArrayDeserializer<ZAxis> {

        public ZAxisDeserializer() {
            super(ZAxis.class);
        }
    }
}
