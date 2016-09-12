package ch.myWinery.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import ch.myWinery.exception.MissingSearchCriteriaException;
import ch.myWinery.model.Producer;
import ch.myWinery.model.Wine;
import ch.myWinery.persistence.ProducerPersistence;
import ch.myWinery.persistence.WinePersistence;

/**
 * The class Winery implements a Winery.
 *
 * @author Dominik Tschumi
 * @version 1.0
 */
public class Winery {
    private static final Logger logger = Logger.getLogger(Winery.class.getName());
    private static Winery instance;
    private List<Wine> wines = new ArrayList<>();

    public static Winery getInstance() {
        if (instance == null) {
            instance = new Winery();
        }
        return instance;
    }

    private Winery() {
        try {
            wines = WinePersistence.getInstance().getAllWines();
        } catch (Exception e) {
            logger.severe(e.toString());
        }
    }

    public synchronized List<Wine> searchWines(String name, Set<String> countries, Set<String> colors)
            throws MissingSearchCriteriaException {

        if (name.isEmpty() && countries.isEmpty() && colors.isEmpty()) {
            throw new MissingSearchCriteriaException();
        }

        name = name.toLowerCase();

        Set<String> countries_lower = new HashSet<>();
        for (String country : countries) {
            countries_lower.add(country.toLowerCase());
        }

        Set<String> colors_lower = new HashSet<>();
        for (String color : colors) {
            colors_lower.add(color.toLowerCase());
        }

        return WinePersistence.getInstance().searchWines(name, countries_lower, colors_lower);
    }

    public synchronized Set<String> getAllColors() {
        Set<String> colors = new HashSet<>();
        for (Wine wine : wines) {
            colors.add(wine.getColor());
        }
        return colors;
    }

    public Set<String> getAllCountries() {
        Set<String> countries = new HashSet<>();
        for (Wine wine : wines) {
            countries.add(wine.getCountry());
        }
        return countries;
    }

    public List<Producer> getAllProducers() {
        return ProducerPersistence.getInstance().getAllProducers();
    }

    public boolean saveNewWine(Wine wine) {
        return WinePersistence.getInstance().saveWine(wine);
    }

    public boolean saveNewProducer(Producer producer) {
        return ProducerPersistence.getInstance().saveProducer(producer);
    }

    @SuppressWarnings("unchecked")
    private <T> T clone(T object) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            new ObjectOutputStream(os).writeObject(object);
            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            return (T) new ObjectInputStream(is).readObject();
        } catch (Exception e) {
            logger.severe(e.toString());
            return object;
        }
    }
}
