/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.crcl.exceptions;

/**
 *
 * @author peter
 */
public class CRCLException extends Exception {

    public CRCLException(String message) {
        super(message);
    }

    public CRCLException(String message, Throwable cause) {
        super(message, cause);
    }

}
