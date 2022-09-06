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
@JsonSerialize(using = FloatArray.FloatArraySerializer.class)
@JsonDeserialize(using = FloatArray.FloatArrayDeserializer.class)
public class FloatArray extends MJCFArrayType {

    public FloatArray(String str) {
        super(str);
    }

    public FloatArray(float... data) {
        super(data);
    }

    public FloatArray(double... data) {
        super(data);
    }

    @Override
    public float[] getFloatData() {
        return super.getFloatData();
    }

    @Override
    public double[] getDoubleData() {
        return super.getDoubleData();
    }

    static class FloatArraySerializer extends MJCFArraySerializer<FloatArray> {

        public FloatArraySerializer() {
            super(FloatArray.class);
        }

    }

    static class FloatArrayDeserializer extends MJCFArrayDeserializer<FloatArray> {

        public FloatArrayDeserializer() {
            super(FloatArray.class);
        }
    }
}
