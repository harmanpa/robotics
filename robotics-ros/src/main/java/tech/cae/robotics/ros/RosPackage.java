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

import java.io.File;
import java.io.IOException;
import tech.cae.robotics.ros.exceptions.RosException;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
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

    public void setVersion(String version) {
        this.packageFile.setVersion(version);
    }

    public RosPackage addURDF(File urdfFile) {
        this.launchFile.addParam("robot_description", urdfFile);
        this.addNode("robot_state_publisher", "robot_state_publisher", "robot_state_publisher");
        this.addNode("joint_state_publisher", "joint_state_publisher", "joint_state_publisher");
        return this;
    }

    public RosPackage addRViz() throws IOException {
        return addRViz(RVizConfigFile.getDefault());
    }

    public RosPackage addRViz(RVizConfigFile config) throws IOException {
        File launchDir = new File(packageDirectory, "launch");
        launchDir.mkdir();
        File rvizConfigFile = new File(launchDir, packageDirectory.getName() + ".rviz");
        config.write(rvizConfigFile);
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
