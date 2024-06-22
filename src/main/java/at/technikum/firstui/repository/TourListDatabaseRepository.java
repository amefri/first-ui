package at.technikum.firstui.repository;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
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
import java.util.Optional;

public class TourListDatabaseRepository implements TourListRepository {

    private static final Logger logger = LogManager.getLogger(TourListDatabaseRepository.class);
    private final EntityManagerFactory entityManagerFactory;

    public TourListDatabaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    @Override
    public List<Tours> findAll() {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tours> criteriaQuery = criteriaBuilder.createQuery(Tours.class);
        Root<Tours> root = criteriaQuery.from(Tours.class);
        CriteriaQuery<Tours> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            logger.info("Fetching all tours");
            return entityManager.createQuery(all).getResultList();
        } catch (Exception e) {
            logger.error("Error fetching all tours", e);
            throw e;
        }
    }

    @Override
    public Tours save(Tours entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            logger.info("Saved tour: {}", entity.getName());
        } catch (Exception e) {
            logger.error("Error saving tour: {}", entity.getName(), e);
            throw e;
        }
        return entity;
    }

    @Override
    public Optional<Tours> findByName(String name) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tours> criteriaQuery = criteriaBuilder.createQuery(Tours.class);
            Root<Tours> root = criteriaQuery.from(Tours.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
            List<Tours> results = entityManager.createQuery(criteriaQuery).getResultList();
            logger.info("Finding tour by name: {}", name);
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            logger.error("Error finding tour by name: {}", name, e);
            throw e;
        }
    }

    @Override
    public Integer getTourIndexByName(String name) {
        return null;
    }

    @Override
    public Optional<Tours> findById(Long id) {
        if (id == null) {
            logger.warn("Tour ID is null.");
            return Optional.empty();
        }
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Tours tour = entityManager.find(Tours.class, id);
            logger.info("Finding tour by ID: {}", id);
            return Optional.ofNullable(tour);
        } catch (Exception e) {
            logger.error("Error finding tour by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void modify(Tours tour) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                logger.info("Modifying tour with ID: {}", tour.getId());
                Tours existingTour = entityManager.find(Tours.class, tour.getId());
                if (existingTour != null) {
                    existingTour.setName(tour.getName());
                    existingTour.setDescription(tour.getDescription());
                    existingTour.setFrom(tour.getFrom());
                    existingTour.setTo(tour.getTo());
                    existingTour.setTransportType(tour.getTransportType());
                    existingTour.setDistance(tour.getDistance());
                    existingTour.setEstimatedTime(tour.getEstimatedTime());
                    existingTour.setImagePath(tour.getImagePath());
                    entityManager.merge(existingTour);
                    logger.info("Modified tour: {}", existingTour.getName());
                } else {
                    logger.warn("Tour not found: {}", tour.getId());
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                logger.error("Error modifying tour: {}", tour.getId(), e);
                throw e;
            }
        }
    }

    @Override
    public void deleteByName(String name) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            // Löschen der zugehörigen TourLogs
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> logQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> logRoot = logQuery.from(TourLog.class);
            logQuery.select(logRoot).where(criteriaBuilder.equal(logRoot.get("tour").get("name"), name));
            List<TourLog> tourLogs = entityManager.createQuery(logQuery).getResultList();
            for (TourLog log : tourLogs) {
                entityManager.remove(log);
                logger.info("Deleted TourLog: {}", log);
            }

            // Löschen der Tour
            CriteriaQuery<Tours> tourQuery = criteriaBuilder.createQuery(Tours.class);
            Root<Tours> tourRoot = tourQuery.from(Tours.class);
            tourQuery.select(tourRoot).where(criteriaBuilder.equal(tourRoot.get("name"), name));
            List<Tours> tours = entityManager.createQuery(tourQuery).getResultList();
            if (!tours.isEmpty()) {
                entityManager.remove(tours.get(0));
                logger.info("Deleted tour: {}", tours.get(0));
            }

            transaction.commit();
        } catch (Exception e) {
            logger.error("Error deleting tour by name: {}", name, e);
            throw e;
        }
    }
}