/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 * @author peter
 */
@JsonSerialize(as = SDFArrayType.class)
@JsonDeserialize(as = SDFArrayType.class)
public class Vector3D extends SDFArrayType {

    public Vector3D(double... data) {
        super(data);
    }

    public Vector3D(String str) {
        super(str);
    }

}
