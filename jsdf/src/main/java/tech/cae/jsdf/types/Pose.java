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
public class Pose extends SDFArrayType {

    public Pose(double... data) {
        super(data);
    }

    public Pose(String str) {
        super(str);
    }
    
}
