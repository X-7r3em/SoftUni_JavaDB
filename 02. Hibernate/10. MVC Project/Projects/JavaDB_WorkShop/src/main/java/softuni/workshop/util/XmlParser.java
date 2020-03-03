package softuni.workshop.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {
    <T> T fromXml(Class<T> entity, String path) throws JAXBException;

    <T> void toXml(T entity, String path) throws JAXBException;
}
