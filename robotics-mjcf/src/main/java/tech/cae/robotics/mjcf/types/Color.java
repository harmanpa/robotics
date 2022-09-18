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
@JsonSerialize(using = Color.ColorSerializer.class)
@JsonDeserialize(using = Color.ColorDeserializer.class)
public class Color extends MJCFArrayType {

    public Color(float... data) {
        super(data);
    }

    public Color(double... data) {
        super(data);
    }

    public Color(String str) {
        super(str);
    }

    public static Color fromAWT(java.awt.Color awt) {
        float[] rgba = awt.getRGBComponents(null);
        return new Color(rgba);
    }

    public java.awt.Color toAWT() {
        float[] fd = getFloatData();
        return new java.awt.Color(fd[0], fd[1], fd[2], fd[3]);
    }

    static class ColorSerializer extends MJCFArraySerializer<Color> {

        public ColorSerializer() {
            super(Color.class);
        }

    }

    static class ColorDeserializer extends MJCFArrayDeserializer<Color> {

        public ColorDeserializer() {
            super(Color.class);
        }
    }
}
