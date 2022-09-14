/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.ros;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;
import tech.cae.robotics.ros.exceptions.RosException;
import tech.cae.robotics.ros.launch.Arg;
import tech.cae.robotics.ros.launch.Launch;
import tech.cae.robotics.ros.launch.Node;
import tech.cae.robotics.ros.launch.ObjectFactory;
import tech.cae.robotics.ros.launch.Param;

/**
 *
 * @author peter
 */
public class RosLaunchFile {

    private final File packageDirectory;
    private final Launch launch;

    public RosLaunchFile(File packageDirectory) {
        this.packageDirectory = packageDirectory;
        this.launch = new ObjectFactory().createLaunch();
    }

    public void addParam(String name, File file) {
        Param param = new ObjectFactory().createParam();
        param.setTextfile(relativize(file));
        this.launch.getNodeOrMachineOrInclude().add(param);
    }

    public String addArg(String name, File file) {
        Arg arg = new ObjectFactory().createArg();
        arg.setDefault(relativize(file));
        this.launch.getNodeOrMachineOrInclude().add(arg);
        return "$(arg " + name + ")";
    }

    public void addNode(String name, String pkg, String type, String... args) {
        Node node = new ObjectFactory().createNode();
        node.setName(name);
        node.setPkg(pkg);
        node.setType(type);
        Stream.of(args).reduce((a, b) -> a + " " + b).ifPresent(argString -> node.setArgs(argString));
        this.launch.getNodeOrMachineOrInclude().add(node);
    }

    public String relativize(File f) {
        return "$(find " + packageDirectory.getName() + ")/" + packageDirectory.toPath().relativize(f.toPath()).toString().replace('\\', '/');
    }

    public void write() throws IOException, RosException {
        File launchDirectory = new File(packageDirectory, "launch");
        launchDirectory.mkdir();
        try ( FileOutputStream fos = new FileOutputStream(new File(launchDirectory, packageDirectory.getName() + ".launch"))) {
            Marshaller m = JAXBContext.newInstance(ObjectFactory.class)
                    .createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(launch, fos);
        } catch (JAXBException ex) {
            throw new RosException("Serialization failed", ex);
        }
    }
}
