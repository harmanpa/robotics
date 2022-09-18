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
package tech.cae.robotics.urdf;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import tech.cae.robotics.urdf.exceptions.URDFException;
import tech.cae.robotics.urdf.ObjectFactory;
import tech.cae.robotics.urdf.Robot;

/**
 *
 * @author Peter Harman peter.harman@cae.tech
 */
public class URDFIO {

    public static Robot readRobot(InputStream is) throws URDFException {
        return read(is, Robot.class);
    }

    public static <T> T read(InputStream is, Class<T> type) throws URDFException {
        try {
            return JAXBContext.newInstance(ObjectFactory.class)
                    .createUnmarshaller()
                    .unmarshal(XMLInputFactory.newInstance()
                            .createXMLStreamReader(is), type).getValue();
        } catch (JAXBException | XMLStreamException ex) {
            throw new URDFException("Deserialization failed", ex);
        }
    }

    public static <T> void write(T object, OutputStream os) throws URDFException {
        try {
            Marshaller m = JAXBContext.newInstance(ObjectFactory.class)
                    .createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(object, os);
        } catch (JAXBException ex) {
            throw new URDFException("Serialization failed", ex);
        }
    }

    public static Robot createRobot() {
        return getObjectFactory().createRobot();
    }

    public static ObjectFactory getObjectFactory() {
        return new ObjectFactory();
    }
}
