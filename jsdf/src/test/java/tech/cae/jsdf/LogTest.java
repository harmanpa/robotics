/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.jsdf;

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
import tech.cae.jsdf.log.GazeboLog;
import tech.cae.jsdf.log.LogState;

/**
 *
 * @author peter
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
