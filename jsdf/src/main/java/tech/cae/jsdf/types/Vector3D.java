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
@JsonSerialize(using = Vector3D.Vector3DSerializer.class)
@JsonDeserialize(using = Vector3D.Vector3DDeserializer.class)
public class Vector3D extends SDFArrayType {

    public Vector3D(double... data) {
        super(data);
    }

    public Vector3D(String str) {
        super(str);
    }

    public static Vector3D fromCommonsMath(org.apache.commons.math3.geometry.euclidean.threed.Vector3D v3d) {
        return new Vector3D(v3d.toArray());
    }

    public org.apache.commons.math3.geometry.euclidean.threed.Vector3D toCommonsMath() {
        return new org.apache.commons.math3.geometry.euclidean.threed.Vector3D(getDoubleData());
    }

    static class Vector3DSerializer extends SDFArraySerializer<Vector3D> {

        public Vector3DSerializer() {
            super(Vector3D.class);
        }

    }

    static class Vector3DDeserializer extends SDFArrayDeserializer<Vector3D> {

        public Vector3DDeserializer() {
            super(Vector3D.class);
        }
    }
}
