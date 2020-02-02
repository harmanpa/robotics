/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;

/**
 *
 * @author peter
 */
@JsonSerialize(using = Pose.PoseSerializer.class)
@JsonDeserialize(using = Pose.PoseDeserializer.class)
public class Pose extends SDFArrayType {

    public Pose(double... data) {
        super(data);
    }

    public Pose(String str) {
        super(str);
    }

    public static Pose fromCommonsMath(org.apache.commons.math3.geometry.euclidean.threed.Vector3D v3d,
            org.apache.commons.math3.geometry.euclidean.threed.Rotation rotation) {
        double[] a6 = new double[6];
        System.arraycopy(v3d.toArray(), 0, a6, 0, 3);
        System.arraycopy(rotation.getAngles(RotationOrder.XYZ, RotationConvention.FRAME_TRANSFORM), 0, a6, 3, 3);
        return new Pose(a6);
    }
    
    static class PoseSerializer extends SDFArraySerializer<Pose> {

        public PoseSerializer() {
            super(Pose.class);
        }

    }

    static class PoseDeserializer extends SDFArrayDeserializer<Pose> {

        public PoseDeserializer() {
            super(Pose.class);
        }
    }

}
