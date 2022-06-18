/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author peter
 */
@Schema(type = "string", pattern = "")
@JsonSerialize(using = Vector2D.Vector2DSerializer.class)
@JsonDeserialize(using = Vector2D.Vector2DDeserializer.class)
public class Vector2D extends SDFArrayType {

    public Vector2D(double... data) {
        super(data);
    }

    public Vector2D(String str) {
        super(str);
    }

    public static Vector2D fromCommonsMath(org.apache.commons.math3.geometry.euclidean.twod.Vector2D v2d) {
        return new Vector2D(v2d.toArray());
    }

    public org.apache.commons.math3.geometry.euclidean.twod.Vector2D toCommonsMath() {
        return new org.apache.commons.math3.geometry.euclidean.twod.Vector2D(getDoubleData());
    }
    static class Vector2DSerializer extends SDFArraySerializer<Vector2D> {

        public Vector2DSerializer() {
            super(Vector2D.class);
        }

    }

    static class Vector2DDeserializer extends SDFArrayDeserializer<Vector2D> {

        public Vector2DDeserializer() {
            super(Vector2D.class);
        }
    }
}
