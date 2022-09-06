/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jmjcf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author peter
 */
@RunWith(Parameterized.class)
public class TestReadExamples {

    private final File file;

    public TestReadExamples(File file) {
        this.file = file;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getExamples() {
        List<Object[]> out = new ArrayList<>();
        File modelDir = new File(new File(System.getProperty("user.dir")), "target/examples/mujoco-main/model");
        for (File content : modelDir.listFiles()) {
            if (content.isDirectory()) {
                for (File f : content.listFiles()) {
                    if (f.isFile() && f.getName().endsWith(".xml")) {
                        out.add(new Object[]{f});
                    }
                }
            }
        }
        return out;
    }

    @Test
    public void test() throws IOException {
        System.out.println("Reading " + file);
        Mujoco m = MJCFIO.load(file);
        System.out.println("Done " + file);
    }
}
