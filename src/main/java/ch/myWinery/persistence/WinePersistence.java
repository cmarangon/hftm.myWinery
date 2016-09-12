package ch.myWinery.persistence;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ch.myWinery.model.Wine;

public class WinePersistence {

    private static WinePersistence singleton;
    private EntityManager entityManager;

    private WinePersistence() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myWinery");
        entityManager = emf.createEntityManager();
    }

    public static WinePersistence getInstance() {
        if (singleton == null) {
            singleton = new WinePersistence();
        }
        return singleton;
    }

    public boolean saveWine(Wine wine) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(wine);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e);
            return false;
        }

        entityManager.getTransaction().commit();
        return true;
    }

    public List<Wine> getAllWines() {
        return entityManager.createNamedQuery(Wine.FIND_ALL).getResultList();
    }

    public Wine getWineById(int id) {
        Wine wine = null;
        Query wineQuery = entityManager.createNamedQuery(Wine.FIND_BY_ID);
        try {
            wine = (Wine) wineQuery.setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {}

        return wine;
    }

    public List<Wine> searchWines(String name, Set<String> countries, Set<String> colors) {
        Query q = entityManager.createNamedQuery(Wine.FIND_BY_NAME_COUNTRY_COLOR);
        q.setParameter("name", "%" + name + "%");
        q.setParameter("nameInactive", name.isEmpty() ? 1 : 0);

        int countriesInactive = 0;
        if (countries.isEmpty()) {
            countriesInactive = 1;
            countries.add("");
        }
        q.setParameter("countriesInactive", countriesInactive);
        q.setParameter("countries", countries);

        int colorsInactive = 0;
        if (colors.isEmpty()) {
            colorsInactive = 1;
            colors.add("");
        }
        q.setParameter("colors", colors);
        q.setParameter("colorsInactive", colorsInactive);
        return q.getResultList();
    }
}
