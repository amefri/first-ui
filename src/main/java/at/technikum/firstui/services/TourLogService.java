package at.technikum.firstui.services;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.TourListRepository;
import at.technikum.firstui.repository.TourLogRepository;

import java.util.List;
import java.util.Optional;

public class TourLogService {

    private final TourLogRepository tourLogRepository;

    private boolean isSelected = false;
    private TourLog currentlySelected;


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

    public boolean deleteTourById(Long tourLogId) {
        TourLog tourLog = tourLogRepository.findById(tourLogId);
        if (tourLog != null) {
            tourLogRepository.deleteTourLog(tourLog);
            System.out.println("TourLog deleted: " + tourLog.getName());
            return true;
        }
        System.out.println("TourLog not found with ID: " + tourLogId);
        return false;
    }


    public void setIsSelected(Boolean isSelected){this.isSelected = isSelected; }

    public boolean isSelected() {return isSelected;}





    public List<TourLog> getTourLogsByTourName(String tourName) {
        return tourLogRepository.findByTourName(tourName);
    }

    public List<TourLog> getAllTourLogs() {
        return tourLogRepository.findAll();
    }

    public List<TourLog> getTourLogsByTourId(Long tourId) {
        return tourLogRepository.findByTourId(tourId);
    }


    public void modifyTourLog(TourLog newTourLog) {
        System.out.println("Tourlog modified: " + newTourLog.getName());
        tourLogRepository.modify(newTourLog);
    }

    public void setCurrentlySelected(TourLog currentlySelected) {
        this.currentlySelected = currentlySelected;
    }

    public TourLog getCurrentlySelected() {
        return currentlySelected;
    }


}



