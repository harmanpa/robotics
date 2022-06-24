package tech.cae.jurdf;

import org.testng.annotations.Test;
import tech.cae.jurdf.URDFIO;
import tech.cae.jurdf.exceptions.URDFException;
import tech.cae.robotics.urdf.Robot;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFromXmlFIle {
    @Test
    public void testRead() {
        String filePath = "/home/futurum/Desktop/URDF_Files/qdh.urdf";
        try (InputStream inputstream = Files.newInputStream(Paths.get(filePath))) {
            Robot robot = URDFIO.readRobot(inputstream);
            System.out.println(robot.getName());
            System.out.println(robot.getVersion());
            System.out.println(robot.getJointsAndLinksAndMaterials());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URDFException e) {
            throw new RuntimeException(e);
        }
    }

}
