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
@JsonSerialize(using = XYAxes.XYAxesSerializer.class)
@JsonDeserialize(using = XYAxes.XYAxesDeserializer.class)
public class XYAxes extends Rotation {

    public XYAxes(String str) {
        super(str);
    }

    public XYAxes(float... data) {
        super(data);
    }

    public XYAxes(double... data) {
        super(data);
    }

    static class XYAxesSerializer extends MJCFArraySerializer<XYAxes> {

        public XYAxesSerializer() {
            super(XYAxes.class);
        }

    }

    static class XYAxesDeserializer extends MJCFArrayDeserializer<XYAxes> {

        public XYAxesDeserializer() {
            super(XYAxes.class);
        }
    }
}
