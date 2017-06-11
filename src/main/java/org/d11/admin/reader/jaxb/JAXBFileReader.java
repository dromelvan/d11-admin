package org.d11.admin.reader.jaxb;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.google.common.io.Resources;

public abstract class JAXBFileReader<T extends Object, U extends Object> {

    private Class<T> xmlRootClass;

    public JAXBFileReader(Class<T> xmlRootClass) {
        setXmlRootClass(xmlRootClass);
    }

    public Class<T> getXmlRootClass() {
        return xmlRootClass;
    }

    public void setXmlRootClass(Class<T> xmlRootClass) {
        this.xmlRootClass = xmlRootClass;
    }

    protected abstract List<U> buildResult(T rootElement);

    public List<U> read(File file) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL url = Resources.getResource(xmlRootClass, xmlRootClass.getSimpleName() + ".xsd");
            Schema schema = schemaFactory.newSchema(url);

            JAXBContext jaxbContext = JAXBContext.newInstance(xmlRootClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);

            JAXBElement<T> rootElement = unmarshaller.unmarshal(new StreamSource(file), xmlRootClass);
            return buildResult(rootElement.getValue());
        } catch(JAXBException e) {
            throw new RuntimeException(e);
        } catch(SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
