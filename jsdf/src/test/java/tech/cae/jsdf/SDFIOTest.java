/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf;

import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author peter
 */
public class SDFIOTest {

    @Test
    public void test() throws IOException {
        Sdf sdf = SDFIO.load(ClassLoader.getSystemClassLoader().getResourceAsStream("model.sdf"));
    }
}
