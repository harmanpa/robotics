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
package tech.cae.robotics.ros;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import tech.cae.robotics.ros.exceptions.RosException;
import tech.cae.robotics.ros.pkg.DependencyType;
import tech.cae.robotics.ros.pkg.LicenseType;
import tech.cae.robotics.ros.pkg.ObjectFactory;
import tech.cae.robotics.ros.pkg.PersonWithEmailType;
import tech.cae.robotics.ros.pkg.PersonWithOptionalEmailType;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
public class RosPackageFile {

    private final File packageDirectory;
    private final tech.cae.robotics.ros.pkg.Package pkg;

    public RosPackageFile(File packageDirectory) {
        this.packageDirectory = packageDirectory;
        ObjectFactory factory = new ObjectFactory();
        this.pkg = factory.createPackage();
        this.pkg.setExport(factory.createExportType());
        this.pkg.setName(packageDirectory.getName());
        this.pkg.setVersion("0.0.0");
        this.pkg.setFormat("2");
        this.pkg.setDescription(factory.createDescriptionType());
        DependencyType catkinDependency = factory.createDependencyType();
        catkinDependency.setValue("catkin");
        this.pkg.getBuildDependOrBuildExportDependOrBuildtoolDepend().add(factory.createPackageBuildtoolDepend(catkinDependency));
    }

    public void setVersion(String version) {
        this.pkg.setVersion(version);
    }

    public void addLicense(String license) {
        ObjectFactory factory = new ObjectFactory();
        LicenseType licenseObject = factory.createLicenseType();
        licenseObject.setValue(license);
        this.pkg.getLicense().add(licenseObject);
    }

    public void addDescription(String version) {
        this.pkg.getDescription().getContent().add(version);
    }

    public void addMaintainer(String name, String email) {
        ObjectFactory factory = new ObjectFactory();
        PersonWithEmailType person = factory.createPersonWithEmailType();
        person.setValue(name);
        person.setEmail(email);
        this.pkg.getMaintainer().add(person);
    }

    public void addAuthor(String name, String email) {
        ObjectFactory factory = new ObjectFactory();
        PersonWithOptionalEmailType person = factory.createPersonWithOptionalEmailType();
        person.setValue(name);
        person.setEmail(email);
        this.pkg.getAuthor().add(person);
    }

    public void addNode(String pkg) {
        ObjectFactory factory = new ObjectFactory();
        DependencyType pkgDependency = factory.createDependencyType();
        pkgDependency.setValue(pkg);
        this.pkg.getBuildDependOrBuildExportDependOrBuildtoolDepend().add(factory.createPackageExecDepend(pkgDependency));
    }

    public void write() throws IOException, RosException {
        boolean addedMaintainer = false;
        if (this.pkg.getMaintainer().isEmpty()) {
            addMaintainer("Anonymous", "anonymous@nowhere.tld");
            addedMaintainer = true;
        }
        boolean addedDescription = false;
        if (this.pkg.getDescription().getContent().isEmpty()) {
            addDescription(pkg.getName() + " " + pkg.getVersion());
            addedDescription = true;
        }
        boolean addedLicense = false;
        if (this.pkg.getLicense().isEmpty()) {
            addLicense("None");
            addedLicense = true;
        }
        try ( FileOutputStream fos = new FileOutputStream(new File(packageDirectory, "package.xml"))) {
            Marshaller m = JAXBContext.newInstance(ObjectFactory.class)
                    .createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(pkg, fos);
        } catch (JAXBException ex) {
            throw new RosException("Serialization failed", ex);
        }
        if (addedMaintainer) {
            this.pkg.getMaintainer().clear();
        }
        if (addedLicense) {
            this.pkg.getLicense().clear();
        }
        if (addedDescription) {
            this.pkg.getDescription().getContent().clear();
        }
    }
}
