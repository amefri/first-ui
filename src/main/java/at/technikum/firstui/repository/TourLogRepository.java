package at.technikum.firstui.repository;

import at.technikum.firstui.entity.TourLog;

import java.util.List;

public interface TourLogRepository {
    List<TourLog> findAll();
    TourLog save(TourLog entity);
    List<TourLog> findByTourId(Long tourId);
    List<TourLog> findByTourName(String tourName);
    TourLog findById(Long id);
    void deleteTourLog(TourLog tourLog);
}