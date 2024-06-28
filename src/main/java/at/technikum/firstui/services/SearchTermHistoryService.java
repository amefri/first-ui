package at.technikum.firstui.services;

import at.technikum.firstui.entity.SearchTerm;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.SearchTermRepository;
import at.technikum.firstui.viewmodel.AddRouteLogViewModel;
import at.technikum.firstui.viewmodel.SearchHistoryViewModel;
import at.technikum.firstui.viewmodel.SearchViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class SearchTermHistoryService {
    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);


    private final SearchTermRepository searchTermRepository;
    private Tours currentlySelectedSearchTerm;

    public SearchTermHistoryService(SearchTermRepository searchTermRepository) {
        this.searchTermRepository = searchTermRepository;

    }

    public void add(String term) {
        Optional<SearchTerm> searchTerm = searchTermRepository.findByTerm(term);

        if (searchTerm.isPresent()) {
            return;
        }

        searchTermRepository.save(new SearchTerm(term));
    }

    public List<String> findAll() {
        return searchTermRepository.findAll().stream()
                .map(SearchTerm::getTerm)
                .toList();
    }

    public Tours getCurrentlySelectedSearchTerm() {
       return currentlySelectedSearchTerm ;
    }

    public void setCurrentlySelectedSearchTerm(Tours currentlySelectedSearchTerm) {
        this.currentlySelectedSearchTerm = currentlySelectedSearchTerm;
    }

    public boolean deleteTerm(String term) {
        if (searchTermRepository.findByTerm(term).isPresent()) {
            searchTermRepository.deleteTerm(term);
            return true;
        }
        logger.warn("Term not found by name: " + term);
        return false;
    }
}
