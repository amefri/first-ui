package at.technikum.firstui.services;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.TourListRepository;

import java.util.List;

public class TourListService {
    private final TourListRepository tourRepository;

    public TourListService(TourListRepository tourRepository) {
        this.tourRepository = tourRepository;
    }


    public void addTour(Tours tour) {
        System.out.println("Tour added: " + tour.getName());
        tourRepository.save(tour);
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



}