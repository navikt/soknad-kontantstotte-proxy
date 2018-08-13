package no.nav.kontantstotte.proxy.dokumentinnsending.dokmot.conversion;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

public final class Jaxb {

    private Jaxb() {

    }

    public static JAXBContext context(Class<?> clazz) {
        try {
            return JAXBContext.newInstance(clazz);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String marshall(JAXBContext context, Object model) {
        return marshall(context, model, true);
    }

    static String marshall(JAXBContext context, Object model, boolean isFragment) {
        try {
            StringWriter sw = new StringWriter();
            marshaller(context, isFragment).marshal(model, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static Marshaller marshaller(JAXBContext context, boolean isFragment) {
        try {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isFragment);
            return marshaller;
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
