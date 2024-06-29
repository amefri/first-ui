package at.technikum.firstui.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SearchTermTest {

    private SearchTerm searchTerm;

    @BeforeEach
    public void setUp() {
        searchTerm = new SearchTerm("example");
    }

    @Test
    public void testGettersAndSetters() {
        // Test initial values
        assertEquals("example", searchTerm.getTerm());

        // Test setting new values
        searchTerm.setTerm("new term");
        assertEquals("new term", searchTerm.getTerm());

        UUID uuid = UUID.randomUUID();
        searchTerm.setId(uuid);
        assertEquals(uuid, searchTerm.getId());
    }

    @Test
    public void testConstructor() {
        SearchTerm newSearchTerm = new SearchTerm("test term");
        assertEquals("test term", newSearchTerm.getTerm());
        assertNull(newSearchTerm.getId());
    }

    @Test
    public void testDefaultConstructor() {
        SearchTerm newSearchTerm = new SearchTerm();
        assertNull(newSearchTerm.getTerm());
        assertNull(newSearchTerm.getId());
    }
}
