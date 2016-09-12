package ch.myWinery.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

/**
 * The class Wine contains the data of a Wine.
 *
 * @author Dominik Tschumi
 * @version 1.0
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Wine.FIND_ALL, query = "SELECT w FROM Wine w"),
        @NamedQuery(name = Wine.FIND_BY_ID, query = "SELECT w FROM Wine w WHERE w.id = :id"),
        @NamedQuery(name = Wine.FIND_BY_NAME_COUNTRY_COLOR, query = "SELECT w FROM Wine w WHERE (lower(w.name) LIKE lower(:name) OR 1 = :nameInactive) AND (lower(w.country) IN :countries OR 1 = :countriesInactive) AND (lower(w.color) IN :colors OR 1 = :colorsInactive)")
})
public class Wine implements Serializable {
    private static final long serialVersionUID = -1245488681054593862L;

    public final static String FIND_ALL = "Wine.findAll";
    public final static String FIND_BY_ID = "Wine.findById";
    public final static String FIND_BY_NAME_COUNTRY_COLOR = "Wine.findByNameCountryColor";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "wine_id")
    private int id;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^.{3,}$", message = "{ch.myWinery.message.WINE_NAME_SHORT}")
    @NotNull(message = "{ch.myWinery.message.WINE_NAME_EMPTY}")
    private String name;

    @Column(name = "color", nullable = false)
    @Pattern(regexp = "^.{3,}$", message = "{ch.myWinery.message.WINE_COLOR_SHORT}")
    @NotNull(message = "{ch.myWinery.message.WINE_COLOR_EMPTY}")
    private String color;

    @Column(name = "country", nullable = false)
    @Pattern(regexp = "^.{3,}$", message = "{ch.myWinery.message.WINE_COUNTRY_SHORT}")
    @NotNull(message = "{ch.myWinery.message.WINE_COUNTRY_EMPTY}")
    private String country;

    @Column(name = "year", nullable = false)
    @Temporal(TemporalType.DATE)
    @Past(message = "{ch.myWinery.message.WINE_YEAR_LATE}")
    @NotNull(message = "{ch.myWinery.message.WINE_YEAR_EMPTY}")
    private Date year;

    @Column(name = "grape", nullable = false)
    @NotNull(message = "{ch.myWinery.message.WINE_GRAPE_EMPTY}")
    private String grape;

    @ManyToOne
    @JoinColumn(name = "producer_id", nullable = false)
    @NotNull(message = "{ch.myWinery.message.WINE_PRODUCER_EMPTY}")
    private Producer producer;

    @Column(name = "character")
    private String character;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "storagestability", nullable = false)
    @NotNull(message = "{ch.myWinery.message.WINE_STORAGESTABILITY_EMPTY}")
    @Pattern(regexp = "^\\d{4}\\s-\\s\\d{4}$", message = "{ch.myWinery.message.WINE_STORAGESTABILITY_PATTERN}")
    private String storagestability;

    @Column(name = "drinkingtemperature", nullable = false)
    @NotNull(message = "{ch.myWinery.message.WINE_DRINKINGTEMPERATURE_EMPTY}")
    @Min(value = 0, message = "{ch.myWinery.message.WINE_DRINKINGTEMPERATURE_TO_LOW}")
    @Max(value = 30, message = "{ch.myWinery.message.WINE_DRINKINGTEMPERATURE_TO_HIGH}")
    private int drinkingtemperature;

    @Column(name = "alcohol", nullable = false)
    @NotNull(message = "{ch.myWinery.message.WINE_ALCOHOL_EMPTY}")
    @DecimalMin(value = "0.0", message = "{ch.myWinery.message.WINE_ALCOHOL_TO_LOW}")
    @DecimalMax(value = "20.0", message = "{ch.myWinery.message.WINE_ALCOHOL_TO_HIGH}")
    private double alcohol;

    @Column(name = "price", nullable = false)
    @NotNull(message = "{ch.myWinery.message.WINE_PRICE_EMPTY}")
    @DecimalMin(value = "0.0", message = "{ch.myWinery.message.WINE_PRICE_TO_LOW}")
    private BigDecimal price;

    @Transient
    private int amount = 0;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getGrape() {
        return grape;
    }

    public void setGrape(String grape) {
        this.grape = grape;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getStoragestability() {
        return storagestability;
    }

    public void setStoragestability(String storagestability) {
        this.storagestability = storagestability;
    }

    public int getDrinkingtemperature() {
        return drinkingtemperature;
    }

    public void setDrinkingtemperature(int drinkingtemperature) {
        this.drinkingtemperature = drinkingtemperature;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getIdAsString() {
        System.out.println(id);
        return String.valueOf(id);
    }
}
