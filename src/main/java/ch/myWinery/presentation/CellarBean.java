package ch.myWinery.presentation;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.myWinery.model.StockItem;
import ch.myWinery.model.Wine;
import ch.myWinery.persistence.StockItemPersistence;

@ManagedBean(name = "cellarBean")
@SessionScoped
public class CellarBean {
    private List<StockItem> stockItems;

    /**
     * @return the stock_items
     */
    public List<StockItem> getStockItems() {
        return StockItemPersistence.getInstance().getAllStockItems();
    }

    /**
     * @param stock_items the stock_items to set
     */
    public void setStockItems(List<StockItem> stockItems) {
        this.stockItems = stockItems;
    }

    public String addStockItem(Wine wine) {
        StockItem stockItem = StockItemPersistence.getInstance().getStockItemByWine(wine);
        if (stockItem.getAmount() > 0) {
            StockItemPersistence.getInstance().saveStockItem(stockItem);
        } else {
            StockItemPersistence.getInstance().removeStockItem(stockItem);
        }

        return null;
    }

}
