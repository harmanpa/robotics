/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.crcl;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.util.List;

/**
 *
 * @author peter
 */
public class Commands {

    private final ScanResult scanResult;

    public Commands() {
        scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages("tech.cae.robotics.crcl")
                .scan();
    }

    public <T> List<Class<T>> getTypes(Class<T> base) {
        return scanResult.getSubclasses(base).loadClasses(base);
    }

}
