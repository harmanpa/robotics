/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.ros;

import java.io.File;
import java.io.IOException;
import tech.cae.robotics.ros.exceptions.RosException;

/**
 *
 * @author peter
 */
public class RosPackage {

    private final File packageDirectory;
    private final RosPackageFile packageFile;
    private final CMakeFile cMakeFile;
    private final RosLaunchFile launchFile;

    public RosPackage(File packageDirectory) {
        this.packageDirectory = packageDirectory;
        this.packageDirectory.mkdir();
        this.packageFile = new RosPackageFile(packageDirectory);
        this.cMakeFile = new CMakeFile(packageDirectory);
        this.launchFile = new RosLaunchFile(packageDirectory);
    }

    public RosPackage addURDF(File urdfFile) {
        this.launchFile.addParam("robot_description", urdfFile);
        this.addNode("robot_state_publisher", "robot_state_publisher", "robot_state_publisher");
        this.addNode("joint_state_publisher", "joint_state_publisher", "joint_state_publisher");
        return this;
    }

    public RosPackage addRViz() throws IOException {
        File launchDir = new File(packageDirectory, "launch");
        launchDir.mkdir();
        File rvizConfigFile = new File(launchDir, packageDirectory.getName() + ".rviz");
        rvizConfigFile.createNewFile();
        return addRViz(rvizConfigFile);
    }

    public RosPackage addRViz(File rvizConfigFile) {
        this.addNode("rviz", "rviz", "rviz", "-d", this.launchFile.addArg("rviz", rvizConfigFile));
        return this;
    }

    public RosPackage addNode(String name, String pkg, String type, String... args) {
        this.launchFile.addNode(name, pkg, type, args);
        this.packageFile.addNode(pkg);
        return this;
    }

    public void write() throws IOException, RosException {
        packageFile.write();
        cMakeFile.write();
        launchFile.write();
    }
}
