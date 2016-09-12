package ch.myWinery.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ch.myWinery.model.StockItem;
import ch.myWinery.model.Wine;

public class StockItemPersistence {

    private static StockItemPersistence singleton;
    private EntityManager entityManager;

    private StockItemPersistence() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myWinery");
        entityManager = emf.createEntityManager();
    }

    public static StockItemPersistence getInstance() {
        if (singleton == null) {
            singleton = new StockItemPersistence();
        }
        return singleton;
    }

    public List<StockItem> getAllStockItems() {
        return entityManager.createNamedQuery(StockItem.FIND_ALL).getResultList();
    }

    public StockItem getStockItemByWine(Wine wine) {
        StockItem stockItem = null;
        Query stockItemQuery = entityManager.createNamedQuery(StockItem.FIND_BY_WINE);
        try {
            stockItem = (StockItem) stockItemQuery.setParameter("wine", wine).getSingleResult();
        } catch (NoResultException e) {}

        return stockItem;
    }

    public boolean saveStockItem(StockItem stockItem) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(stockItem);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean removeStockItem(StockItem stockItem) {
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(stockItem);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }
        entityManager.getTransaction().commit();
        return true;
    }
}
