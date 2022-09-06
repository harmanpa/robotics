/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.crcl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tech.cae.robotics.crcl.exceptions.CRCLException;

/**
 *
 * @author peter
 */
@RunWith(Parameterized.class)
public class ReadTest {

    @Parameterized.Parameters
    public static Collection<Object[]> files() {
        File examples = new File(new File(System.getProperty("user.dir")), "target/examples");
        List<Object[]> out = new ArrayList<>();
        for (File file : examples.listFiles((f) -> f.isFile() && f.getName().endsWith(".xml"))) {
            out.add(new Object[]{file});
        }
        return out;
    }
    private final File file;

    public ReadTest(File file) {
        this.file = file;
    }

    @Test
    public void test() throws CRCLException, IOException {
        try ( FileInputStream fis = new FileInputStream(file)) {
            CRCLProgram program = CRCLIO.readProgram(fis);
            for(MiddleCommandType command : program.getMiddleCommands()) {
                System.out.println(new ObjectMapper().writeValueAsString(command));
            }
        }
    }

}
