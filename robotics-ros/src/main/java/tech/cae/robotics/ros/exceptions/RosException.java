/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.ros.exceptions;

/**
 *
 * @author peter
 */
public class RosException extends Exception {

    public RosException(String message) {
        super(message);
    }

    public RosException(String message, Throwable cause) {
        super(message, cause);
    }

}
