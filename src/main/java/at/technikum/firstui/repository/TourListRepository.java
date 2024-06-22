package at.technikum.firstui.repository;

import at.technikum.firstui.entity.Tours;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface TourListRepository {
    List<Tours> findAll();
    Tours save(Tours entity);
    Optional<Tours> findByName(String name);
    Integer getTourIndexByName(String name);



    void deleteByName(String name);
    Optional<Tours> findById(Long id);



    void saveTourURL(Tours entity, URL url);

void saveTourDistance(Tours entity, double distance);
void saveTourDuration(Tours entity, double duration);
}
