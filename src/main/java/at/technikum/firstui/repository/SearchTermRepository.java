package at.technikum.firstui.repository;

import at.technikum.firstui.entity.SearchTerm;

import java.util.List;
import java.util.Optional;

public interface SearchTermRepository {

    List<SearchTerm> findAll();

    SearchTerm save(SearchTerm entity);

    Optional<SearchTerm> findByTerm(String term);
}