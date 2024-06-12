package at.technikum.firstui.services;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.TourListRepository;
import at.technikum.firstui.repository.TourLogRepository;

import java.util.List;
import java.util.Optional;

public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourListRepository tourListRepository;


    public TourLogService(TourLogRepository tourLogRepository, TourListRepository tourListRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourListRepository = tourListRepository;
    }

    //---------FUNCTIONS-------------

    public void addTourLog(TourLog tourLog) {
        System.out.println("TourLog added: " + tourLog.getName());
        tourLogRepository.save(tourLog);
    }

    public void deleteTourByName(String name) {
        tourLogRepository.deleteTourLog(name);
    }

    public List<TourLog> getTourLogsByTourName(String tourName) {
        return tourLogRepository.findByTourName(tourName);
    }

    public List<TourLog> getAllTourLogs() {
        return tourLogRepository.findAll();
    }

    public List<TourLog> getTourLogsByTourId(Long tourId) {
        return tourLogRepository.findByTourId(tourId);
    }




}
