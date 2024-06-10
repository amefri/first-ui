package at.technikum.firstui.repository;

import at.technikum.firstui.entity.Tours;

import java.util.List;
import java.util.Optional;

public interface TourListRepository {
    List<Tours> findAll();
    Tours save(Tours entity);
    Optional<Tours> findByName(String name);
    Integer getTourIndexByName(String name);
    void deleteByName(String name);
}
