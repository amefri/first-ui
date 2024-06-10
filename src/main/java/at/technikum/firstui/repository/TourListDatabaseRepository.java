package at.technikum.firstui.repository;

import at.technikum.firstui.entity.Tours;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class TourListDatabaseRepository implements TourListRepository {
    private final EntityManagerFactory entityManagerFactory;

    public TourListDatabaseRepository() {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("hibernate");
    }

    @Override
    public List<Tours> findAll() {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tours> criteriaQuery = criteriaBuilder.createQuery(Tours.class);
        Root<Tours> root = criteriaQuery.from(Tours.class);
        CriteriaQuery<Tours> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(all).getResultList();
        }
    }

    @Override
    public Tours save(Tours entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
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
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        }
    }

    @Override
    public Integer getTourIndexByName(String name) {
        return null;
    }

    @Override
    public void deleteByName(String name) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tours> criteriaQuery = criteriaBuilder.createQuery(Tours.class);
            Root<Tours> root = criteriaQuery.from(Tours.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
            List<Tours> results = entityManager.createQuery(criteriaQuery).getResultList();
            if (!results.isEmpty()) {
                entityManager.remove(results.get(0));
            }
            transaction.commit();
        }
    }
}
