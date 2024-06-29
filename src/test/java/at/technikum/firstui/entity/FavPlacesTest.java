package at.technikum.firstui.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FavPlacesTest {

    private FavPlaces favPlace;

    @BeforeEach
    public void setUp() {
        favPlace = new FavPlaces("Favorite Place", "A place to relax", "48.2082", "16.3738");
    }



    @Test
    public void testIdGetterAndSetter() {
        favPlace.setIdFav(1L);
        assertEquals(1L, favPlace.getIdFav());
    }



    @Test
    public void testConstructorWithNameAndDescription() {
        FavPlaces newFavPlace = new FavPlaces("Simple Place", "Just a simple place");
        assertEquals("Simple Place", newFavPlace.getNameFav());
        assertEquals("Just a simple place", newFavPlace.getDescription());
        assertNull(newFavPlace.getLatitude());
        assertNull(newFavPlace.getLongetude());
    }
}
