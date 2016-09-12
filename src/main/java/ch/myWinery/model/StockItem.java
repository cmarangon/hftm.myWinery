package ch.myWinery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = StockItem.FIND_ALL, query = "SELECT si FROM StockItem si"),
        @NamedQuery(name = StockItem.FIND_BY_WINE, query = "SELECT si FROM StockItem si WHERE si.wine = :wine")
})
public class StockItem {

    public final static String FIND_ALL = "StockItem.findAll";
    public final static String FIND_BY_WINE = "StockItem.findByWine";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "stock_item_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "wine_id")
    private Wine wine;

    @Column(name = "amount")
    private int amount;

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

    /**
     * @return the wine
     */
    public Wine getWine() {
        return wine;
    }

    /**
     * @param wine the wine to set
     */
    public void setWine(Wine wine) {
        this.wine = wine;
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
}
