package app.ccb.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserImpl implements XmlParser {
    public <T> T fromXml(Class<T> entityClass, String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new File(path));
    }
}
