package at.technikum.firstui.services;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.TourListRepository;

import java.util.List;
import java.util.Optional;

public class TourListService {
    private final TourListRepository tourRepository;

    private Tours currentlySelected;

    private boolean isSelected = false;


    public TourListService(TourListRepository tourRepository) {
        this.tourRepository = tourRepository;
    }


    public void addTour(Tours tour) {
        System.out.println("Tour added: " + tour.getName());
        tourRepository.save(tour);
    }

    public void modifyTour(Tours tour) {
        System.out.println("Tour modified: " + tour.getName());
        tourRepository.modify(tour);
    }

    public List<Tours> getTours() {
        return tourRepository.findAll();
    }

    public boolean deleteTourByName(String tourName) {
        if (tourRepository.findByName(tourName).isPresent()) {
            tourRepository.deleteByName(tourName);
            System.out.println("Tour removed by name: " + tourName);
            return true;
        }
        System.out.println("Tour not found by name: " + tourName);
        return false;
    }

    public void setIsSelected(Boolean isSelected){this.isSelected = isSelected; }

    public boolean isSelected() {return isSelected;}


    public void setCurrentlySelected(Tours currentlySelected) {
        this.currentlySelected = currentlySelected;
    }

    public Tours getCurrentlySelected() {
        return currentlySelected;
    }

    public Tours getTourByName(String name) {
        return tourRepository.findByName(name).orElse(null);
    }



}