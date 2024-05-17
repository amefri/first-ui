package at.technikum.firstui.repository;

import at.technikum.firstui.entity.SearchTerm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchTermMemoryRepository implements SearchTermRepository {

    private final List<SearchTerm> searchTerms;

    public SearchTermMemoryRepository() {
        searchTerms = new ArrayList<>();
    }

    @Override
    public List<SearchTerm> findAll() {
        return searchTerms;
    }

    @Override
    public SearchTerm save(SearchTerm entity) {
        searchTerms.add(entity);

        return entity;
    }

    @Override
    public Optional<SearchTerm> findByTerm(String term) {
        for (SearchTerm searchTerm: searchTerms) {
            if (!searchTerm.getTerm().equals(term)) {
                continue;
            }

            return Optional.of(searchTerm);
        }

        return Optional.empty();
    }
}
