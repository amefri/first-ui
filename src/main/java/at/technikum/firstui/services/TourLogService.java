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

    private List<Tours> tours;

    public TourLogService(TourLogRepository tourLogRepository, TourListRepository tourListRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourListRepository = tourListRepository;
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

    public List<TourLog> getTourLogsByTourId(Long tourId) {
        return tourLogRepository.findByTourId(tourId);
    }

    public void deleteTourByName(String name) {
        tourLogRepository.deleteTourLog(name);
    }

    public void addTourLogToTour(Long tourId, String logName, String date, String duration, String distance) {
        if (tourId == null) {
            System.out.println("Tour ID is null.");
            return;
        }

        Optional<Tours> toursOptional = tourListRepository.findById(tourId);

        if (toursOptional.isEmpty()) {
            System.out.println("Tour not found for ID: " + tourId);
            return;
        }

        Tours tour = toursOptional.get();

        TourLog tourLog = new TourLog(logName, date, duration, distance);
        tourLog.setTour(tour); // Set the Tour object in the TourLog
        tour.addTourLog(tourLog); // Maintain the bidirectional relationship

        addTourLog(tourLog); // Save the TourLog
    }
}
