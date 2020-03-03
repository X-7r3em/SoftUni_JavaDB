package app.ccb.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <T> T fromXml(Class<T> entityClass, String path) throws JAXBException;
}
