/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jmjcf.types;

/**
 *
 * @author peter
 */
public abstract class Rotation extends FloatArray {

    public Rotation(String str) {
        super(str);
    }

    public Rotation(float... data) {
        super(data);
    }

    public Rotation(double... data) {
        super(data);
    }

}
