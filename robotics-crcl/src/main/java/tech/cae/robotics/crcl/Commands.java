/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.crcl;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public List<Class<MiddleCommandType>> getCommandTypes() {
        return getNonAbstractTypes(MiddleCommandType.class);
    }

    public <T> List<Class<T>> getTypes(Class<T> base) {
        return scanResult.getSubclasses(base).loadClasses(base);
    }

    public <T> List<Class<T>> getTypes(Class<T> base, Predicate<Class<T>> filter) {
        return getTypes(base).stream().filter(filter).collect(Collectors.toList());
    }

    public <T> List<Class<T>> getNonAbstractTypes(Class<T> base) {
        return getTypes(base, (Class<T> type) -> !Modifier.isAbstract(type.getModifiers()));
    }

}
