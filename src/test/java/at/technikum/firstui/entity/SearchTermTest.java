package at.technikum.firstui.entity;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class SearchTermTest {

    @Test
    void testDefaultConstructor() {
        SearchTerm searchTerm = new SearchTerm();
        assertNull(searchTerm.getId());
        assertNull(searchTerm.getTerm());
    }

    @Test
    void testParameterizedConstructor() {
        String term = "sample term";
        SearchTerm searchTerm = new SearchTerm(term);
        assertNull(searchTerm.getId());
        assertEquals(term, searchTerm.getTerm());
    }

    @Test
    void testGetAndSetId() {
        SearchTerm searchTerm = new SearchTerm();
        UUID id = UUID.randomUUID();
        searchTerm.setId(id);
        assertEquals(id, searchTerm.getId());
    }

    @Test
    void testGetAndSetTerm() {
        SearchTerm searchTerm = new SearchTerm();
        String term = "test term";
        searchTerm.setTerm(term);
        assertEquals(term, searchTerm.getTerm());
    }


}
