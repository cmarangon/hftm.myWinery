package ch.myWinery.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ch.myWinery.model.Producer;
import ch.myWinery.model.Wine;

/**
 *
 * @author dominik
 */
public class WineImporter08 extends DefaultHandler {

    private Wine wine;
    private Producer producer;
    private String elementValue;
    private ArrayList<Wine> wines = new ArrayList<Wine>();
    private ArrayList<Producer> producers = new ArrayList<Producer>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

    private static final String FILEPATH = "./src/main/resources/data/catalog.xml";

    private static EntityManagerFactory emf;
    private static EntityManager em;

    /*
     * When the parser encounters plain text (not XML elements), it calls(this
     * method, which accumulates them in a string buffer
     */
    @Override
    public void characters(char[] buffer, int start, int length) {
        elementValue = new String(buffer, start, length);
    }

    /*
     * Every time the parser encounters the beginning of a new element, it calls
     * this method, which resets the string buffer
     */
    @Override
    public void startElement(String uri, String localName, String elementName, Attributes attributes)
            throws SAXException {
        elementValue = "";
        if (elementName.equalsIgnoreCase("wine")) {
            wine = new Wine();
            this.producer = new Producer();
        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    @Override
    public void endElement(String uri, String localName, String elementName) throws SAXException {

        if (elementName.equalsIgnoreCase("wine")) {
            // add it to the list
            wines.add(wine);

        } else if (elementName.equalsIgnoreCase("name")) {
            wine.setName(elementValue);
        } else if (elementName.equalsIgnoreCase("color")) {
            wine.setColor(elementValue);
        } else if (elementName.equalsIgnoreCase("country")) {
            wine.setCountry(elementValue);
        } else if (elementName.equalsIgnoreCase("grape")) {
            wine.setGrape(elementValue);
        } else if (elementName.equalsIgnoreCase("year")) {
            try {
                wine.setYear(sdf.parse(elementValue));
            } catch (ParseException ex) {
                Logger.getLogger(WineImporter08.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (elementName.equalsIgnoreCase("character")) {
            wine.setCharacter(elementValue);
        } else if (elementName.equalsIgnoreCase("drinkingtemperature")) {
            wine.setDrinkingtemperature(Integer.parseInt(elementValue));
        } else if (elementName.equalsIgnoreCase("alcohol")) {
            wine.setAlcohol(Double.parseDouble(elementValue));
        } else if (elementName.equalsIgnoreCase("qualification")) {
            wine.setQualification(elementValue);
        } else if (elementName.equalsIgnoreCase("storagestability")) {
            wine.setStoragestability(elementValue);
        } else if (elementName.equalsIgnoreCase("price")) {
            wine.setPrice(new BigDecimal(elementValue));
        } else if (elementName.equalsIgnoreCase("producer")) {
            boolean newProducer = true;

            if (producers.size() == 0) {
                this.producer.setName(elementValue);
            } else {
                for (Producer producer : producers) {
                    if (producer.getName().compareTo(elementValue) == 0) {
                        newProducer = false;
                        this.producer = producer;
                    }
                }
            }
            if (newProducer) {
                this.producer.setName(elementValue);
                producers.add(this.producer);
            }

        } else if (elementName.equalsIgnoreCase("origin")) {
            this.producer.setOrigin(elementValue);

        }

        wine.setProducer(this.producer);
    }

    public ArrayList<Wine> getWines() {
        return wines;
    }

    public ArrayList<Producer> getProducers() {
        return producers;
    }

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("myWinery");
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Create a "parser factory" for creating SAX parsers
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

            // Now use the parser factory to create a SAXParser object
            SAXParser saxParser = saxParserFactory.newSAXParser();

            // Create an instance of this class; it defines all the handler
            // methods
            WineImporter08 wineImporter = new WineImporter08();

            // Finally, tell the parser to parse the input and notify the
            // handler
            saxParser.parse(FILEPATH, wineImporter);

            ArrayList<Wine> wines = wineImporter.getWines();
            ArrayList<Producer> producers = wineImporter.getProducers();

            for (Producer producer : producers) {
                em.persist(producer);
            }

            for (Wine wine : wines) {
                System.out.println(wine.getName());
                em.persist(wine);
            }

            em.getTransaction().commit();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}