/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.cae.robotics.crcl;

import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import tech.cae.robotics.crcl.exceptions.CRCLException;

/**
 *
 * @author peter
 */
public class CRCLIO {

    public static CRCLProgram readProgram(InputStream is) throws CRCLException {
        return read(is, CRCLProgram.class);
    }

    public static <T extends DataThingType> T read(InputStream is, Class<T> type) throws CRCLException {
        try {
            return JAXBContext.newInstance(ObjectFactory.class)
                    .createUnmarshaller()
                    .unmarshal(XMLInputFactory.newInstance()
                            .createXMLStreamReader(is), type).getValue();
        } catch (JAXBException | XMLStreamException ex) {
            throw new CRCLException("Deserialization failed", ex);
        }
    }

    public static <T extends DataThingType> void write(T object, OutputStream os) throws CRCLException {
        try {
            Marshaller m = JAXBContext.newInstance(ObjectFactory.class)
                    .createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(object, XMLOutputFactory.newInstance().createXMLStreamWriter(os));
        } catch (JAXBException | XMLStreamException ex) {
            throw new CRCLException("Serialization failed", ex);
        }
    }
}
