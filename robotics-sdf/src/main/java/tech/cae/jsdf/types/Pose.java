/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;
import tech.cae.jsdf.types.SDFArrayType.SDFArrayDeserializer;
import tech.cae.jsdf.types.SDFArrayType.SDFArraySerializer;

/**
 *
 * @author peter
 */
public class Pose {

    @JacksonXmlProperty(isAttribute = true, localName = "frame")
    private PoseContent frame;

    @JacksonXmlText
    private PoseContent content;

    public Pose() {

    }

    public Pose(double... data) {
        this.content = new PoseContent(data);
    }

    public Pose(String str) {
        this.content = new PoseContent(str);
    }

    public static Pose fromCommonsMath(org.apache.commons.math3.geometry.euclidean.threed.Vector3D v3d,
            org.apache.commons.math3.geometry.euclidean.threed.Rotation rotation) {
        double[] a6 = new double[6];
        System.arraycopy(v3d.toArray(), 0, a6, 0, 3);
        System.arraycopy(rotation.getAngles(RotationOrder.XYZ, RotationConvention.FRAME_TRANSFORM), 0, a6, 3, 3);
        return new Pose(a6);
    }

    @Schema(type = "string", pattern = "")
    @JsonSerialize(using = Pose.PoseSerializer.class)
    @JsonDeserialize(using = Pose.PoseDeserializer.class)
    public static class PoseContent extends SDFArrayType {

        public PoseContent(double... data) {
            super(data);
        }

        public PoseContent(String str) {
            super(str);
        }
    }

    static class PoseSerializer extends SDFArraySerializer<PoseContent> {

        public PoseSerializer() {
            super(PoseContent.class);
        }

    }

    static class PoseDeserializer extends SDFArrayDeserializer<PoseContent> {

        public PoseDeserializer() {
            super(PoseContent.class);
        }

    }

}
