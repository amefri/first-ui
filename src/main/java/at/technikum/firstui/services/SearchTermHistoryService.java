package at.technikum.firstui.services;

import at.technikum.firstui.entity.SearchTerm;
import at.technikum.firstui.repository.SearchTermRepository;
import at.technikum.firstui.viewmodel.SearchHistoryViewModel;

import java.util.List;
import java.util.Optional;

public class SearchTermHistoryService {

    private final SearchTermRepository searchTermRepository;
    private String currentlySelectedSearchTerm;

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

    public String getCurrentlySelectedSearchTerm() {
       return currentlySelectedSearchTerm ;
    }

    public void setCurrentlySelectedSearchTerm(String term) {
        this.currentlySelectedSearchTerm = term;
    }
}
