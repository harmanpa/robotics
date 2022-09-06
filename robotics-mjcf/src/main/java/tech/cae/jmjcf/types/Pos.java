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
@JsonSerialize(using = Pos.PosSerializer.class)
@JsonDeserialize(using = Pos.PosDeserializer.class)
public class Pos extends FloatArray {

    public Pos(String str) {
        super(str);
    }

    public Pos(float... data) {
        super(data);
    }

    public Pos(double... data) {
        super(data);
    }

    static class PosSerializer extends MJCFArrayType.MJCFArraySerializer<Pos> {

        public PosSerializer() {
            super(Pos.class);
        }

    }

    static class PosDeserializer extends MJCFArrayType.MJCFArrayDeserializer<Pos> {

        public PosDeserializer() {
            super(Pos.class);
        }
    }
}
