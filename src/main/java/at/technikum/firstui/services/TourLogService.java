package at.technikum.firstui.services;

import at.technikum.firstui.entity.TourLog;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TourLogService {

    private Set<TourLog> tourLogs = new HashSet<>();

    public void addTourLog(TourLog tourLog) {
        System.out.println("TourLog added: " + tourLog.getName());
        tourLogs.add(tourLog);
    }

    public Set<TourLog> getTours() {
        return tourLogs;
    }

    public boolean deleteTourByName(String tourName) {
        Iterator<TourLog> iterator = tourLogs.iterator();
        while (iterator.hasNext()) {
            TourLog tourLog = iterator.next();
            if (tourLog.getName().equals(tourName)) {
                iterator.remove();  // Remove the tour from the set
                System.out.println("TourLog removed by name: " + tourName);
                return true;  // Return true if the tour was successfully removed
            }
        }
        System.out.println("TourLog not found by name: " + tourName);
        return false;  // Return false if no tour with the specified name was found
    }

}
