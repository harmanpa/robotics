/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf;

import java.io.IOException;
import org.junit.Test;
import tech.cae.jsdf.types.SDFRootType;
import tech.cae.jsdf.v1_6.Sdf;

/**
 *
 * @author peter
 */
public class SDFIOTest {

    @Test
    public void testSpecificVersion() throws IOException {
        Sdf sdf = SDFIO.load(ClassLoader.getSystemClassLoader().getResourceAsStream("model.sdf"), Sdf.class);
    }

    @Test
    public void testUnknownVersion() throws IOException {
        SDFRootType sdf = SDFIO.load(ClassLoader.getSystemClassLoader().getResourceAsStream("model.sdf"));
        System.out.println("Loaded as version " + sdf.getVersion());
    }

    @Test
    public void testSniffVersion() throws IOException {
        String version = SDFIO.sniffVersion(ClassLoader.getSystemClassLoader().getResourceAsStream("model.sdf"));
    }

    @Test
    public void testAvailableVersions() throws IOException {
        SDFIO.supportedVersions().forEach(version -> System.out.println(version));
    }
}
