/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.ros;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author peter
 */
public class CMakeFile {

    private final File packageDirectory;

    public CMakeFile(File packageDirectory) {
        this.packageDirectory = packageDirectory;
    }

    public Collection<String> getLines() {
        List<String> out = new ArrayList<>();
        out.add("cmake_minimum_required(VERSION 2.8.3)");
        out.add("project(" + packageDirectory.getName() + ")");
        out.add("");
        out.add("find_package(catkin REQUIRED)");
        out.add("");
        out.add("catkin_package()");
        out.add("");
        for (File sub : packageDirectory.listFiles((File file) -> file.isDirectory())) {
            out.add("install(DIRECTORY " + sub.getName());
            out.add("  DESTINATION ${CATKIN_PACKAGE_SHARE_DESTINATION})");
            out.add("");
        }
        return out;
    }

    public void write() throws IOException {
        Files.write(new File(packageDirectory, "CMakeLists.txt").toPath(), getLines(), StandardOpenOption.CREATE);
    }
}
