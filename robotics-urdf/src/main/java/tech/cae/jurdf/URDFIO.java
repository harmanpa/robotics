/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.jurdf;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLInputFactory;
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
