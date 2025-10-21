package data_acces_layer;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XmlPersister {

    private String path;
    private static XmlPersister theInstance;

    public static XmlPersister instance() {
        if (theInstance == null)
            theInstance = new XmlPersister("data.xml");
        return theInstance;
    }

    public XmlPersister(String path) {
        this.path = path;
    }

    public Data load() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        try (FileInputStream is = new FileInputStream(path)) {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Data) unmarshaller.unmarshal(is);
        }
    }

    public void store(Data data) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        try (FileOutputStream os = new FileOutputStream(path)) {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(data, os);
        }
    }
}
