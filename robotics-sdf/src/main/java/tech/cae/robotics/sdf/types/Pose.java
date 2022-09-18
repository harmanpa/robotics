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
package tech.cae.robotics.sdf.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;
import tech.cae.robotics.sdf.types.SDFArrayType.SDFArrayDeserializer;
import tech.cae.robotics.sdf.types.SDFArrayType.SDFArraySerializer;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
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
