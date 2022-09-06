/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jmjcf.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

/**
 *
 * @author peter
 */
public enum Flag implements MJCFEnum {

    Disable("disable"), Enable("enable");

    private final String key;

    Flag(String key) {
        this.key = key;
    }

    @JsonCreator
    public static Flag fromString(String key) {
        return Stream.of(values()).filter(value -> value.getKey().equals(key)).findFirst().orElse(null);
    }

    @JsonValue
    @Override
    public String getKey() {
        return key;
    }
}
