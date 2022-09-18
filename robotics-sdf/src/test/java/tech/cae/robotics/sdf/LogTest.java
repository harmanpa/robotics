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
package tech.cae.robotics.sdf;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import tech.cae.robotics.sdf.log.GazeboLog;
import tech.cae.robotics.sdf.log.LogState;
import tech.cae.robotics.sdf.v1_6.Sdf;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
public class LogTest {

    @Test
    public void test() throws IOException {
        GazeboLog log = GazeboLog.load(ClassLoader.getSystemClassLoader().getResourceAsStream("state.log"));
//
////        Sdf sdf = log.getSDF();
//        //List<State> states = log.getStates();
//        log.getChunks().forEach(chunk -> {
//            System.out.println(chunk.getData());
//            try (InputStream is = new InflaterInputStream(new ByteArrayInputStream(Base64.getMimeDecoder().decode(chunk.getData())));
//                    ByteArrayOutputStream os = new ByteArrayOutputStream()) {
//                IOUtils.copy(is, os);
////                Sdf sdf = SDFIO.load(is);
//                System.out.println(os.toString());
//            } catch (IOException ex) {
//                Logger.getLogger(LogTest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        });

        log.getChunks().subList(0, 1).forEach(chunk -> {
            //System.out.println(chunk.getData());
            try (InputStream is = new InflaterInputStream(new ByteArrayInputStream(Base64.getMimeDecoder().decode(chunk.getData())));
                    ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                IOUtils.copy(is, os);
//                Sdf sdf = SDFIO.load(is);
                System.out.println(os.toString());
                new XmlMapper().writeValue(System.out, new XmlMapper().readValue(os.toString(), Sdf.class));
//                System.out.println(os.toString());
            } catch (IOException ex) {
                Logger.getLogger(LogTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        log.getChunks().subList(1, log.getChunks().size()).forEach(chunk -> {
            //System.out.println(chunk.getData());
            try (InputStream is = new InflaterInputStream(new ByteArrayInputStream(Base64.getMimeDecoder().decode(chunk.getData())));
                    ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                IOUtils.copy(is, os);
//                Sdf sdf = SDFIO.load(is);
                System.out.println(os.toString());
                new XmlMapper().writeValue(System.out, new XmlMapper().readValue(os.toString(), LogState.class));
//                System.out.println(os.toString());
            } catch (IOException ex) {
                Logger.getLogger(LogTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
}
