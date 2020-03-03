package softuni.workshop.util;

import javax.xml.bind.*;
import java.io.File;

public class XmlParserImpl implements XmlParser {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromXml(Class<T> entity, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(entity);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new File(path));
    }

    @Override
    public <T> void toXml(T entity, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(entity.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(entity, new File(path));
    }
}
