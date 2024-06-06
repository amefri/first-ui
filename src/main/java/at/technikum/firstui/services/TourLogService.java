package at.technikum.firstui.services;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.TourLogRepository;

import java.util.List;

public class TourLogService {

    private final TourLogRepository tourLogRepository;

    public TourLogService(TourLogRepository tourLogRepository) {
        this.tourLogRepository = tourLogRepository;
    }

    public void addTourLog(TourLog tourLog) {
        System.out.println("TourLog added: " + tourLog.getName());
        tourLogRepository.save(tourLog);
    }

    public List<TourLog> getTourLogsByTourName(String tourName) {
        return tourLogRepository.findByTourName(tourName);
    }

    public List<TourLog> getAllTourLogs() {
        return tourLogRepository.findAll();
    }

    public void deleteTourLog(Long id) {
        tourLogRepository.deleteById(id);
    }




}
