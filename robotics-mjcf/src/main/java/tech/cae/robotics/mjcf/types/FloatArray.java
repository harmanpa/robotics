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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
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
