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
package tech.cae.robotics.crcl;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
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
