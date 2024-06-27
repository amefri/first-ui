package at.technikum.firstui.repository;

import at.technikum.firstui.entity.FavPlaces;
import at.technikum.firstui.entity.TourLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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

    public FavPlaceDatabaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    public void save(FavPlaces place) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(place);
        em.getTransaction().commit();
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
}
