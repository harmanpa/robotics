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
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
@Schema(type = "string", pattern = "")
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
