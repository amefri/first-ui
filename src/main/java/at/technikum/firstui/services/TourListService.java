package at.technikum.firstui.services;

import at.technikum.firstui.entities.Tours;

import java.util.ArrayList;
import java.util.List;

public class TourListService {
    private List<Tours> tours = new ArrayList<>();

    public void addTour(Tours tour) {
        tours.add(tour);
    }

    public List<Tours> getTours() {
        return tours;
    }
}