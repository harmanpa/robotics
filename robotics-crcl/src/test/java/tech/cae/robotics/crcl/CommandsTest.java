/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.crcl;

import org.junit.Test;

/**
 *
 * @author peter
 */
public class CommandsTest {
    
    @Test
    public void test() {
        new Commands().getCommandTypes().forEach(type -> System.out.println(type));
    }
}
