package ch.myWinery.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ch.myWinery.model.Producer;

public class ProducerPersistence {

    private static ProducerPersistence singleton;
    private EntityManager entityManager;

    public ProducerPersistence() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myWinery");
        entityManager = emf.createEntityManager();
    }

    public static ProducerPersistence getInstance() {
        if (singleton == null) {
            singleton = new ProducerPersistence();
        }
        return singleton;
    }

    public boolean saveProducer(Producer producer) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(producer);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e);
            return false;
        }

        entityManager.getTransaction().commit();
        return true;
    }

    public List<Producer> getAllProducers() {
        return entityManager.createNamedQuery(Producer.FIND_ALL).getResultList();
    }

    public Producer getProducerById(int id) {
        Producer producer = null;
        Query producerQuery = entityManager.createNamedQuery(Producer.FIND_BY_ID);
        try {
            producer = (Producer) producerQuery.setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {}

        return producer;
    }
}
