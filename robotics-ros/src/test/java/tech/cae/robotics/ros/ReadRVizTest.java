/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.ros;

import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author peter
 */
public class ReadRVizTest {
    
    @Test
    public void test() throws IOException {
        new RVizConfigFile().read(Thread.currentThread().getContextClassLoader().getResourceAsStream("tech/cae/robotics/ros/default.rviz"));
    }
}
