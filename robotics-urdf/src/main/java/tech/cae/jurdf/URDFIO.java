/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jurdf;

import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import tech.cae.jurdf.exceptions.URDFException;
import tech.cae.robotics.urdf.ObjectFactory;
import tech.cae.robotics.urdf.Robot;

/**
 *
 * @author peter
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
            JAXBContext.newInstance(ObjectFactory.class)
                    .createMarshaller()
                    .marshal(object, XMLOutputFactory.newInstance().createXMLStreamWriter(os));
        } catch (JAXBException | XMLStreamException ex) {
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
