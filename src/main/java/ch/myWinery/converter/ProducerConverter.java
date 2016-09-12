package ch.myWinery.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ch.myWinery.model.Producer;
import ch.myWinery.persistence.ProducerPersistence;

@FacesConverter("ProducerConverter")
public class ProducerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        return ProducerPersistence.getInstance().getProducerById(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        return String.valueOf(((Producer) value).getId());
    }
}
