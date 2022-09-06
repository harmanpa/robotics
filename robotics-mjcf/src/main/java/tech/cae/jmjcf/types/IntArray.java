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
@JsonSerialize(using = IntArray.IntArraySerializer.class)
@JsonDeserialize(using = IntArray.IntArrayDeserializer.class)
public class IntArray extends MJCFArrayType {

    public IntArray(String str) {
        super(str);
    }

    public IntArray(int... data) {
        super(data);
    }

    public IntArray(long... data) {
        super(data);
    }

    @Override
    public long[] getLongData() {
        return super.getLongData();
    }

    @Override
    public int[] getIntData() {
        return super.getIntData();
    }

    static class IntArraySerializer extends MJCFArraySerializer<IntArray> {

        public IntArraySerializer() {
            super(IntArray.class);
        }

    }

    static class IntArrayDeserializer extends MJCFArrayDeserializer<IntArray> {

        public IntArrayDeserializer() {
            super(IntArray.class);
        }
    }
}
