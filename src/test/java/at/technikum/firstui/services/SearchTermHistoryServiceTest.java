package at.technikum.firstui.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import at.technikum.firstui.entity.SearchTerm;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.SearchTermRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class SearchTermHistoryServiceTest {

    @Mock
    private SearchTermRepository searchTermRepository;

    @InjectMocks
    private SearchTermHistoryService searchTermHistoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewTerm() {
        String term = "example";
        when(searchTermRepository.findByTerm(term)).thenReturn(Optional.empty());

        searchTermHistoryService.add(term);

        verify(searchTermRepository, times(1)).save(any(SearchTerm.class));
    }

    @Test
    public void testAddExistingTerm() {
        String term = "example";
        when(searchTermRepository.findByTerm(term)).thenReturn(Optional.of(new SearchTerm(term)));

        searchTermHistoryService.add(term);

        verify(searchTermRepository, never()).save(any(SearchTerm.class));
    }

    @Test
    public void testFindAll() {
        List<SearchTerm> searchTerms = List.of(new SearchTerm("term1"), new SearchTerm("term2"));
        when(searchTermRepository.findAll()).thenReturn(searchTerms);

        List<String> result = searchTermHistoryService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains("term1"));
        assertTrue(result.contains("term2"));
    }

    @Test
    public void testGetAndSetCurrentlySelectedSearchTerm() {
        Tours tour = new Tours();
        searchTermHistoryService.setCurrentlySelectedSearchTerm(tour);

        assertEquals(tour, searchTermHistoryService.getCurrentlySelectedSearchTerm());
    }

    @Test
    public void testDeleteTermExisting() {
        String term = "example";
        when(searchTermRepository.findByTerm(term)).thenReturn(Optional.of(new SearchTerm(term)));

        boolean result = searchTermHistoryService.deleteTerm(term);

        assertTrue(result);
        verify(searchTermRepository, times(1)).deleteTerm(term);
    }

    @Test
    public void testDeleteTermNonExisting() {
        String term = "example";
        when(searchTermRepository.findByTerm(term)).thenReturn(Optional.empty());

        boolean result = searchTermHistoryService.deleteTerm(term);

        assertFalse(result);
        verify(searchTermRepository, never()).deleteTerm(term);
    }
}
