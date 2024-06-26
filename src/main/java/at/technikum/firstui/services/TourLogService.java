package at.technikum.firstui.services;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.TourListRepository;
import at.technikum.firstui.repository.TourLogRepository;
import at.technikum.firstui.viewmodel.AddRouteLogViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class TourLogService {

    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);

    private final TourLogRepository tourLogRepository;
    private final TourListRepository tourListRepository;


    private boolean isSelected = false;
    private TourLog currentlySelected;




    public TourLogService(TourLogRepository tourLogRepository, TourListRepository tourListRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourListRepository = tourListRepository;
    }

    //---------FUNCTIONS-------------

    public void addTourLog(TourLog tourLog) {
        logger.info("TourLog added: " + tourLog.getName());
        tourLogRepository.save(tourLog);
    }

    public boolean deleteTourById(Long tourLogId) {
        TourLog tourLog = tourLogRepository.findById(tourLogId);
        if (tourLog != null) {
            tourLogRepository.deleteTourLog(tourLog);
            logger.info("TourLog deleted: " + tourLog.getName());
            return true;
        }
        logger.warn("TourLog not found with ID: " + tourLogId);
        return false;
    }

    public void modifyTourLog(TourLog newTourLog) {
        logger.info("Tourlog modified: " + newTourLog.getName());
        tourLogRepository.modify(newTourLog);
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

    public void setCurrentlySelected(TourLog currentlySelected) {
        this.currentlySelected = currentlySelected;
    }

    public TourLog getCurrentlySelected() {
        return currentlySelected;
    }


}



