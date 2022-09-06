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
@JsonSerialize(using = Euler.EulerSerializer.class)
@JsonDeserialize(using = Euler.EulerDeserializer.class)
public class Euler extends Rotation {

    public Euler(String str) {
        super(str);
    }

    public Euler(float... data) {
        super(data);
    }

    public Euler(double... data) {
        super(data);
    }

    static class EulerSerializer extends MJCFArraySerializer<Euler> {

        public EulerSerializer() {
            super(Euler.class);
        }

    }

    static class EulerDeserializer extends MJCFArrayDeserializer<Euler> {

        public EulerDeserializer() {
            super(Euler.class);
        }
    }
}
