package ch.myWinery.presentation;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

import ch.myWinery.business.Winery;
import ch.myWinery.model.Producer;
import ch.myWinery.model.Wine;
import ch.myWinery.persistence.ProducerPersistence;
import ch.myWinery.persistence.WinePersistence;

@ManagedBean
@RequestScoped
public class ManageBean {
    private Winery winery = Winery.getInstance();

    // Addparams
    private List<Producer> avaliableProducers;

    // Statusmessage
    private String message = "";

    // Translations
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;

    private Producer randomProducer = null;
    private Wine randomWine = null;

    private Wine newWine = new Wine();
    private Producer newProducer = new Producer();

    public ManageBean() {
        reloadForms();
    }

    /**
     * @param avaliableProducers the avaliableProducers to set
     */
    public void setAvaliableProducers(List<Producer> avaliableProducers) {
        this.avaliableProducers = avaliableProducers;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the i18n
     */
    public ResourceBundle getI18n() {
        return i18n;
    }

    /**
     * @param i18n the i18n to set
     */
    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    /**
     * @return
     */
    public Wine getNewWine() {
        return newWine;
    }

    /**
     * @param wine
     */
    public void setNewWine(Wine wine) {
        newWine = wine;
    }

    /**
     * @return the newProducer
     */
    public Producer getNewProducer() {
        return newProducer;
    }

    /**
     * @param newProducer the newProducer to set
     */
    public void setNewProducer(Producer newProducer) {
        this.newProducer = newProducer;
    }

    /**
     * @return the avaliableProducers
     */
    public List<Producer> getAvaliableProducers() {
        return winery.getAllProducers();
    }

    /**
     * @return
     */
    public void addWine() {
        if (winery.saveNewWine(newWine)) {
            message = i18n.getString("wine_success");
            newWine = new Wine();
        }
    }

    /**
     *
     */
    public void addProducer() {
        if (winery.saveNewProducer(newProducer)) {
            message = i18n.getString("producer_success");
            newProducer = new Producer();
        }
    }

    /**
     *
     */
    public void reloadForms() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        PartialViewContext partialViewContext = facesContext.getPartialViewContext();
        Collection<String> renderIds = partialViewContext.getRenderIds();

        for (String renderId : renderIds) {
            resetFields(FacesContext.getCurrentInstance().getViewRoot().findComponent(renderId));
        }

        message = "";
    }

    /**
     * @param component
     */
    private void resetFields(UIComponent component) {
        try {
            List<UIComponent> children = component.getChildren();
            if (children.size() > 0) {
                for (UIComponent child_component : children) {
                    resetFields(child_component);
                }
            }
            EditableValueHolder input = (EditableValueHolder) component;
            input.resetValue();
        } catch (Exception e) {}
        ;
    }

    /**
     * @return
     */
    public Wine getRandomWine() {
        if (randomWine == null) {
            List<Wine> allWines = WinePersistence.getInstance().getAllWines();
            if (!allWines.isEmpty())
                randomWine = allWines.get(new Random().nextInt(allWines.size()));
        }

        return randomWine;
    }

    /**
     * @param randomWine
     */
    public void setRandomWine(Wine randomWine) {
        this.randomWine = randomWine;
    }

    /**
     * @return
     */
    public Producer getRandomProducer() {
        if (randomProducer == null) {
            List<Producer> allProducers = ProducerPersistence.getInstance().getAllProducers();
            if (!allProducers.isEmpty())
                randomProducer = allProducers.get(new Random().nextInt(allProducers.size()));
        }

        return randomProducer;
    }

    /**
     * @param randomProducer the randomProducer to set
     */
    public void setRandomProducer(Producer randomProducer) {
        this.randomProducer = randomProducer;
    }

    public String trim(String text) {
        return text.trim();
    }
}
