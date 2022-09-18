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
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
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
