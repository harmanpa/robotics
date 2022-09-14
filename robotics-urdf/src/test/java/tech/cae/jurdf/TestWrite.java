/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jurdf;

import org.junit.Test;
import tech.cae.jurdf.exceptions.URDFException;

/**
 *
 * @author peter
 */
public class TestWrite {

    @Test
    public void test() throws URDFException {
        URDFIO.write(URDFIO.createRobot(), System.out);
    }
}
