/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 * @author peter
 */
@JsonSerialize(using = Color.ColorSerializer.class)
@JsonDeserialize(using = Color.ColorDeserializer.class)
public class Color extends SDFArrayType {

    public Color(double... data) {
        super(data);
    }

    public Color(String str) {
        super(str);
    }

    public static Color fromAWT(java.awt.Color awt) {
        float[] rgba = awt.getRGBComponents(null);
        return new Color((double) rgba[0], (double) rgba[1], (double) rgba[2], (double) rgba[3]);
    }

    public java.awt.Color toAWT() {
        return new java.awt.Color((float) getData()[0], (float) getData()[1], (float) getData()[2], (float) getData()[3]);
    }

    static class ColorSerializer extends SDFArraySerializer<Color> {

        public ColorSerializer() {
            super(Color.class);
        }

    }

    static class ColorDeserializer extends SDFArrayDeserializer<Color> {

        public ColorDeserializer() {
            super(Color.class);
        }
    }
}
