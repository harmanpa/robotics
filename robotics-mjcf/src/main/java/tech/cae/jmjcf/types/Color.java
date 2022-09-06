/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
