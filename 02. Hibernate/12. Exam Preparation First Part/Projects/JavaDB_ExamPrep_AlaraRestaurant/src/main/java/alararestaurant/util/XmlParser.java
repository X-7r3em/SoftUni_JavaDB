package alararestaurant.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    public <T> String toXml(T entity) throws JAXBException;

    public <T> T fromXml(Class<T> entityClass, String path) throws JAXBException;
}
