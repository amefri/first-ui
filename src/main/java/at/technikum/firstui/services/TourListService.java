package at.technikum.firstui.services;

import at.technikum.firstui.entity.Tours;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TourListService {
    private Set<Tours> tours = new HashSet<>();

    public void addTour(Tours tour) {
        System.out.println("Tour added: " + tour.getName());
        tours.add(tour);
    }

    public Set<Tours> getTours() {
        return tours;
    }

    public boolean deleteTourByName(String tourName) {
        Iterator<Tours> iterator = tours.iterator();
        while (iterator.hasNext()) {
            Tours tour = iterator.next();
            if (tour.getName().equals(tourName)) {
                iterator.remove();  // Remove the tour from the set
                System.out.println("Tour removed by name: " + tourName);
                return true;  // Return true if the tour was successfully removed
            }
        }
        System.out.println("Tour not found by name: " + tourName);
        return false;  // Return false if no tour with the specified name was found
    }

}