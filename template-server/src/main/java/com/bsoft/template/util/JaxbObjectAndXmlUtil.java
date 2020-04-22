package com.bsoft.template.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Jaxb 处理Xml与Object转换
 * @author artolia
 */
public class JaxbObjectAndXmlUtil {

    /**
     * object转xml
     */
    public static String object2Xml(Class<?> clazz, Object object) {
        String result = "";
        try (StringWriter writer = new StringWriter()) {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(object, writer);
            result = new String(writer.getBuffer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * xml转object
     * @param clazz xml对应的实体类
     * @param xml xml字符串
     * @return object
     */
    public static Object xml2Object(Class<?> clazz, String xml) {
        Object xmlObject = null;
        try (Reader reader = new StringReader(xml)) {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            xmlObject = unmarshaller.unmarshal(reader);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }
}
