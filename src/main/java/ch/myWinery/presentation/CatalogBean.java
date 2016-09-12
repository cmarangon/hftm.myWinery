package ch.myWinery.presentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.myWinery.business.Winery;
import ch.myWinery.exception.MissingSearchCriteriaException;
import ch.myWinery.factories.MessageFactory;
import ch.myWinery.model.StockItem;
import ch.myWinery.model.Wine;
import ch.myWinery.persistence.StockItemPersistence;

@ManagedBean
@SessionScoped
public class CatalogBean {
    private Winery winery = Winery.getInstance();

    private String selectedForm = "wine";

    // Searchparams
    private String name = "";

    private Set<String> availableCountries = new HashSet<>();
    private Set<String> selectedCountries = new HashSet<>();

    private Set<String> availableColors = new HashSet<>();
    private Set<String> selectedColors = new HashSet<>();

    private List<Wine> foundWines = new ArrayList<>();
    private Wine selectedWine = null;

    private String referrer = "";

    /**
     * @return the selectedForm
     */
    public String getSelectedForm() {
        return selectedForm;
    }

    /**
     * @param selectedForm the selectedForm to set
     */
    public void setSelectedForm(String selectedForm) {
        this.selectedForm = selectedForm;
    }

    /**
     * @return the winery
     */
    public Winery getWinery() {
        return winery;
    }

    /**
     * @param winery the winery to set
     */
    public void setWinery(Winery winery) {
        this.winery = winery;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the avaliableCountries
     */
    public Set<String> getAvailableCountries() {
        availableCountries = winery.getAllCountries();
        return availableCountries;
    }

    /**
     * @param avaliableCountries the avaliableCountries to set
     */
    public void setAvailableCountries(Set<String> availableCountries) {
        this.availableCountries = availableCountries;
    }

    /**
     * @return the selectedCountries
     */
    public Set<String> getSelectedCountries() {
        return selectedCountries;
    }

    /**
     * @param selectedCountries the selectedCountries to set
     */
    public void setSelectedCountries(Set<String> selectedCountries) {
        this.selectedCountries = selectedCountries;
    }

    /**
     * @return the color
     */
    public Set<String> getSelectedColors() {
        return selectedColors;
    }

    /**
     * @param color the color to set
     */
    public void setSelectedColors(Set<String> colors) {
        this.selectedColors = colors;
    }

    /**
     * @return all colors
     */
    public Set<String> getAvailableColors() {
        availableColors = winery.getAllColors();
        return availableColors;
    }

    /**
     * @param colors
     */
    public void setAvailableColors(Set<String> colors) {
        this.availableColors = colors;
    }

    /**
     * @return the foundWine
     */
    public List<Wine> getFoundWines() {
        return foundWines;
    }

    /**
     * @param foundWine the foundWine to set
     */
    public void setFoundWines(List<Wine> foundWines) {
        this.foundWines = foundWines;
    }

    /**
     * @return
     */
    public Wine getSelectedWine() {
        return selectedWine;
    }

    /**
     * @param wine
     * @return
     */
    public String setSelectedWine(Wine wine, String referrer) {
        this.selectedWine = wine;
        this.referrer = referrer;

        return "details";
    }

    /**
     * @return the referrer
     */
    public String getReferrer() {
        return referrer;
    }

    /**
     * @param referrer the referrer to set
     */
    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    /**
     * @return
     */
    public void searchWines() {
        foundWines = null;

        if (name == null) {
            name = "";
        }

        if (name.isEmpty() && selectedCountries.isEmpty() && selectedColors.isEmpty())
            return;

        try {
            List<Wine> wines = winery.searchWines(name, selectedCountries, selectedColors);
            if (wines.isEmpty()) {
                MessageFactory.error("ch.myWinery.message.NO_WINES_FOUND");
            } else {
                foundWines = wines;
            }
        } catch (MissingSearchCriteriaException e) {
            MessageFactory.error("ch.myWinery.message.MISSING_SEARCH_CRITERIEA");
        }
    }

    public String addStockItem(Wine wine) {
        StockItem stockItem = StockItemPersistence.getInstance().getStockItemByWine(wine);
        if (stockItem == null) {
            stockItem = new StockItem();
            stockItem.setWine(wine);
        }
        int newAmount = stockItem.getAmount() + wine.getAmount();

        if (newAmount > 0) {
            stockItem.setAmount(stockItem.getAmount() + wine.getAmount());
            if (StockItemPersistence.getInstance().saveStockItem(stockItem))
                wine.setAmount(0);
        } else {
            MessageFactory.error("ch.myWinery.message.AMOUNT_ZERO");
        }

        return null;
    }

    public String back() {
        return referrer.isEmpty() ? "catalog" : referrer;
    }
}
