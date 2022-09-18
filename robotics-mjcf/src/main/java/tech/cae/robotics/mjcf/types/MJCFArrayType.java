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
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.StringTokenizer;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
@JsonSerialize(using = MJCFArrayType.MJCFArraySerializer.class)
@JsonDeserialize(using = MJCFArrayType.MJCFArrayDeserializer.class)
class MJCFArrayType implements MJCFType {

    private final BigDecimal[] data;

    protected MJCFArrayType(float... data) {
        this.data = new BigDecimal[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = new BigDecimal((double) data[i]);
        }
    }

    protected MJCFArrayType(int... data) {
        this.data = new BigDecimal[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = new BigDecimal(data[i]);
        }
    }

    protected MJCFArrayType(long... data) {
        this.data = new BigDecimal[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = new BigDecimal(data[i]);
        }
    }

    protected MJCFArrayType(double... data) {
        this.data = new BigDecimal[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = new BigDecimal(data[i]);
        }
    }

    protected MJCFArrayType(BigDecimal... data) {
        this.data = data;
    }

    protected MJCFArrayType(String str) {
        this(parse(str));
    }

    static BigDecimal[] parse(String str) {
        System.out.println("Parsing " + str);
        StringTokenizer tokenizer = new StringTokenizer(str);
        BigDecimal[] out = new BigDecimal[tokenizer.countTokens()];
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            out[i] = new BigDecimal(tokenizer.nextToken());
            i++;
        }
        return out;
    }

    protected BigDecimal[] getData() {
        return data;
    }

    protected double[] getDoubleData() {
        double[] out = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            out[i] = data[i].doubleValue();
        }
        return out;
    }

    protected float[] getFloatData() {
        float[] out = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            out[i] = data[i].floatValue();
        }
        return out;
    }

    protected int[] getIntData() {
        int[] out = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            out[i] = data[i].intValue();
        }
        return out;
    }

    protected long[] getLongData() {
        long[] out = new long[data.length];
        for (int i = 0; i < data.length; i++) {
            out[i] = data[i].longValue();
        }
        return out;
    }

    public boolean isEmpty() {
        for (BigDecimal bd : data) {
            if (bd.compareTo(BigDecimal.ZERO) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (data.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(data[0].toPlainString());
        for (int i = 1; i < data.length; i++) {
            sb.append(' ').append(data[i]);
        }
        return sb.toString();
    }

    static class MJCFArraySerializer<T extends MJCFArrayType> extends StdSerializer<T> {

        public MJCFArraySerializer(Class<T> c) {
            super(c);
        }

        @Override
        public void serialize(T t, JsonGenerator jg, SerializerProvider sp) throws IOException {
            jg.writeString(t.toString());
        }

        @Override
        public boolean isEmpty(SerializerProvider provider, T value) {
            return value.isEmpty();
        }

    }

    static class MJCFArrayDeserializer<T extends MJCFArrayType> extends StdDeserializer<T> {

        public MJCFArrayDeserializer(Class<T> c) {
            super(c);
        }

        @Override
        public T deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
            try {
                return ((Class<T>) getValueClass()).getConstructor(String.class).newInstance(jp.getValueAsString());
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new IOException("Failed to deserialize " + jp.getText() + " to type " + getValueClass().toString(), ex);
            }
        }

    }

}
