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
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import tech.cae.robotics.ros.exceptions.RosException;
import tech.cae.robotics.ros.pkg.DependencyType;
import tech.cae.robotics.ros.pkg.ObjectFactory;

/**
 *
 * @author peter
 */
public class RosPackageFile {

    private final File packageDirectory;
    private final tech.cae.robotics.ros.pkg.Package pkg;

    public RosPackageFile(File packageDirectory) {
        this.packageDirectory = packageDirectory;
        ObjectFactory factory = new ObjectFactory();
        this.pkg = factory.createPackage();
        this.pkg.setExport(factory.createExportType());
        DependencyType catkinDependency = factory.createDependencyType();
        catkinDependency.setValue("catkin");
        this.pkg.getBuildDependOrBuildExportDependOrBuildtoolDepend().add(factory.createPackageBuildtoolDepend(catkinDependency));
    }

    public void addNode(String pkg) {
        ObjectFactory factory = new ObjectFactory();
        DependencyType pkgDependency = factory.createDependencyType();
        pkgDependency.setValue(pkg);
        this.pkg.getBuildDependOrBuildExportDependOrBuildtoolDepend().add(factory.createPackageExecDepend(pkgDependency));
    }

    public void write() throws IOException, RosException {
        try ( FileOutputStream fos = new FileOutputStream(new File(packageDirectory, "package.xml"))) {
            Marshaller m = JAXBContext.newInstance(ObjectFactory.class)
                    .createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(pkg, XMLOutputFactory.newInstance().createXMLStreamWriter(fos));
        } catch (JAXBException | XMLStreamException ex) {
            throw new RosException("Serialization failed", ex);
        }
    }
}
