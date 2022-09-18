/*
 * The MIT License
 *
 * Copyright 2022- CAE Tech Limited
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 * @author Peter Harman peter.harman@cae.tech
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
            for (MiddleCommandType command : program.getMiddleCommands()) {
                System.out.println(new ObjectMapper().writeValueAsString(command));
            }
        }
    }

}
