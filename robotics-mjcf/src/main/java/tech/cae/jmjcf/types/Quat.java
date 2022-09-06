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
@JsonSerialize(using = Quat.QuatSerializer.class)
@JsonDeserialize(using = Quat.QuatDeserializer.class)
public class Quat extends Rotation {

    public Quat(String str) {
        super(str);
    }

    public Quat(float... data) {
        super(data);
    }

    public Quat(double... data) {
        super(data);
    }

    static class QuatSerializer extends MJCFArraySerializer<Quat> {

        public QuatSerializer() {
            super(Quat.class);
        }

    }

    static class QuatDeserializer extends MJCFArrayDeserializer<Quat> {

        public QuatDeserializer() {
            super(Quat.class);
        }
    }
}
