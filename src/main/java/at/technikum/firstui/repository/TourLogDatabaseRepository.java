package at.technikum.firstui.repository;

import at.technikum.firstui.entity.TourLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class TourLogDatabaseRepository implements TourLogRepository {

    private final EntityManagerFactory entityManagerFactory;

    public TourLogDatabaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    @Override
    public List<TourLog> findAll() {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
        Root<TourLog> root = criteriaQuery.from(TourLog.class);
        CriteriaQuery<TourLog> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(all).getResultList();
        }
    }

    @Override
    public TourLog save(TourLog entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public List<TourLog> findByTourId(Long tourId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("id"), tourId));
            return entityManager.createQuery(criteriaQuery).getResultList();
        }
    }




    public List<TourLog> findByTourName(String tourName) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("name"), tourName));
            return entityManager.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void deleteTourLog(String tourName) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("name"), tourName));

            List<TourLog> tourLogs = entityManager.createQuery(criteriaQuery).getResultList();
            for (TourLog tourLog : tourLogs) {
                if (!entityManager.contains(tourLog)) {
                    tourLog = entityManager.merge(tourLog);
                }
                entityManager.remove(tourLog);
            }

            transaction.commit();
        }
    }
}
