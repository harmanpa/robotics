///*
// * The MIT License
// *
// * Copyright 2022- CAE Tech Limited
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
//package tech.cae.robotics.mjcf;
//
//import tech.cae.robotics.mjcf.MJCFIO;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import tech.cae.robotics.mjcf.Mujoco;
//
///**
// *
// * @author Peter Harman peter.harman@cae.tech
// */
//@RunWith(Parameterized.class)
//public class TestReadExamples {
//
//    private final File file;
//
//    public TestReadExamples(File file) {
//        this.file = file;
//    }
//
//    @Parameterized.Parameters
//    public static Collection<Object[]> getExamples() {
//        List<Object[]> out = new ArrayList<>();
//        File modelDir = new File(new File(System.getProperty("user.dir")), "target/examples/mujoco-main/model");
//        for (File content : modelDir.listFiles()) {
//            if (content.isDirectory()) {
//                for (File f : content.listFiles()) {
//                    if (f.isFile() && f.getName().endsWith(".xml")) {
//                        out.add(new Object[]{f});
//                    }
//                }
//            }
//        }
//        return out;
//    }
//
//    @Test
//    public void test() throws IOException {
//        System.out.println("Reading " + file);
//        Mujoco m = MJCFIO.load(file);
//        System.out.println("Done " + file);
//    }
//}
