/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author peter
 */
@Schema(type = "string", pattern = "")
@JsonSerialize(using = Time.TimeSerializer.class)
@JsonDeserialize(using = Time.TimeDeserializer.class)
public class Time extends SDFArrayType {

    public Time(double... data) {
        super(data);
    }

    public Time(String str) {
        super(str);
    }
    static class TimeSerializer extends SDFArraySerializer<Time> {

        public TimeSerializer() {
            super(Time.class);
        }

    }

    static class TimeDeserializer extends SDFArrayDeserializer<Time> {

        public TimeDeserializer() {
            super(Time.class);
        }
    }
}
