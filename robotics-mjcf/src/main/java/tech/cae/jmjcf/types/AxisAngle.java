/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
@JsonSerialize(using = AxisAngle.AxisAngleSerializer.class)
@JsonDeserialize(using = AxisAngle.AxisAngleDeserializer.class)
public class AxisAngle extends Rotation {

    public AxisAngle(String str) {
        super(str);
    }

    public AxisAngle(float... data) {
        super(data);
    }

    public AxisAngle(double... data) {
        super(data);
    }

    static class AxisAngleSerializer extends MJCFArraySerializer<AxisAngle> {

        public AxisAngleSerializer() {
            super(AxisAngle.class);
        }

    }

    static class AxisAngleDeserializer extends MJCFArrayDeserializer<AxisAngle> {

        public AxisAngleDeserializer() {
            super(AxisAngle.class);
        }
    }
}
