package at.technikum.firstui.repository;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TourLogDatabaseRepository implements TourLogRepository {

    private static final Logger logger = LogManager.getLogger(TourLogDatabaseRepository.class);

    private final EntityManagerFactory entityManagerFactory;

    public TourLogDatabaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    @Override
    public List<TourLog> findAll() {
        logger.debug("Attempting to find all TourLogs");
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
        Root<TourLog> root = criteriaQuery.from(TourLog.class);
        CriteriaQuery<TourLog> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<TourLog> tourLogs = entityManager.createQuery(all).getResultList();
            logger.info("Found {} TourLogs", tourLogs.size());
            return tourLogs;
        } catch (Exception e) {
            logger.error("Error finding all TourLogs", e);
            throw e;
        }
    }

    @Override
    public TourLog save(TourLog entity) {
        logger.debug("Attempting to save TourLog: {}", entity);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(entity);
                transaction.commit();
                logger.info("Saved TourLog: {}", entity);
                return entity;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                logger.error("Error saving TourLog: {}", entity, e);
                throw e;
            }
        }
    }

    @Override
    public List<TourLog> findByTourId(Long tourId) {
        logger.debug("Attempting to find TourLogs by Tour ID: {}", tourId);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("id"), tourId));
            List<TourLog> tourLogs = entityManager.createQuery(criteriaQuery).getResultList();
            logger.info("Found {} TourLogs for Tour ID: {}", tourLogs.size(), tourId);
            return tourLogs;
        } catch (Exception e) {
            logger.error("Error finding TourLogs by Tour ID: {}", tourId, e);
            throw e;
        }
    }

    @Override
    public List<TourLog> findByTourName(String tourName) {
        logger.debug("Attempting to find TourLogs by Tour name: {}", tourName);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("name"), tourName));
            List<TourLog> tourLogs = entityManager.createQuery(criteriaQuery).getResultList();
            logger.info("Found {} TourLogs for Tour name: {}", tourLogs.size(), tourName);
            return tourLogs;
        } catch (Exception e) {
            logger.error("Error finding TourLogs by Tour name: {}", tourName, e);
            throw e;
        }
    }

    @Override
    public TourLog findById(Long id) {
        logger.debug("Attempting to find TourLog by ID: {}", id);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TourLog tourLog = entityManager.find(TourLog.class, id);
            if (tourLog != null) {
                logger.info("Found TourLog: {}", tourLog);
            } else {
                logger.warn("No TourLog found with ID: {}", id);
            }
            return tourLog;
        } catch (Exception e) {
            logger.error("Error finding TourLog by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deleteTourLog(TourLog tourLog) {
        logger.debug("Attempting to delete TourLog: {}", tourLog);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Finden und löschen des TourLog
            TourLog attachedTourLog = entityManager.find(TourLog.class, tourLog.getId());
            if (attachedTourLog != null) {
                // Entfernen des TourLog aus der Tour-Entität
                Tours tour = attachedTourLog.getTour();
                if (tour != null) {
                    tour.getTourLogs().remove(attachedTourLog);
                    entityManager.merge(tour);  // Speichern der Änderungen an der Tour
                    logger.debug("Removed TourLog from Tour: {}", tour);
                }
                entityManager.remove(attachedTourLog);
                logger.info("Deleted TourLog: {}", attachedTourLog);
            } else {
                logger.warn("No TourLog found to delete with ID: {}", tourLog.getId());
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error deleting TourLog: {}", tourLog, e);
            throw e;
        } finally {
            entityManager.close();
        }
    }
}