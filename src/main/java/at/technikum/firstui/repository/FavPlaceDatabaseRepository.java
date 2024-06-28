package at.technikum.firstui.repository;

import at.technikum.firstui.entity.FavPlaces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FavPlaceDatabaseRepository {
    private static final Logger logger = LogManager.getLogger(TourLogDatabaseRepository.class);
    private final EntityManagerFactory entityManagerFactory;

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
    private FavPlaces currentlySelectedPlace;

    public FavPlaceDatabaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    public void save(FavPlaces place) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(place);
        transaction.commit();
        em.close();
    }

    public List<FavPlaces> getAll() {
        logger.debug("Attempting to find all FavPlaces");
        CriteriaBuilder criteriaBuilder = emf.getCriteriaBuilder();
        CriteriaQuery<FavPlaces> criteriaQuery = criteriaBuilder.createQuery(FavPlaces.class);
        Root<FavPlaces> root = criteriaQuery.from(FavPlaces.class);
        CriteriaQuery<FavPlaces> all = criteriaQuery.select(root);

        try (EntityManager entityManager = emf.createEntityManager()) {
            List<FavPlaces> favPlaces = entityManager.createQuery(all).getResultList();
            logger.info("Found {} FavPlaces", favPlaces.size());
            return favPlaces;
        } catch (Exception e) {
            logger.error("Error finding all FavPlaces", e);
            throw e;
        }
    }

    public FavPlaces getCurrentlySelected() {
        return currentlySelectedPlace;
    }

    public void setCurrentlySelected(FavPlaces place) {
        this.currentlySelectedPlace = place;
    }

    public boolean deleteTerm(String term) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FavPlaces> criteriaQuery = criteriaBuilder.createQuery(FavPlaces.class);
            Root<FavPlaces> root = criteriaQuery.from(FavPlaces.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("nameFav"), term));

            List<FavPlaces> resultList = em.createQuery(criteriaQuery).getResultList();
            if (!resultList.isEmpty()) {
                FavPlaces place = resultList.get(0);
                em.remove(place);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            logger.error("Error deleting FavPlace with term: " + term, e);
            transaction.rollback();
            return false;
        } finally {
            em.close();
        }
    }
}
