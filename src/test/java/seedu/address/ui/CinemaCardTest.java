package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCinema;

import org.junit.Test;

import guitests.guihandles.CinemaCardHandle;
import seedu.address.model.cinema.Cinema;
import seedu.address.testutil.CinemaBuilder;

public class CinemaCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Cinema cinema = new CinemaBuilder().build();
        CinemaCard cinemaCard = new CinemaCard(cinema, 1);
        uiPartRule.setUiPart(cinemaCard);
        assertCardDisplay(cinemaCard, cinema, 1);
    }

    @Test
    public void equals() {
        Cinema cinema = new CinemaBuilder().build();
        CinemaCard cinemaCard = new CinemaCard(cinema, 0);

        // same cinema, same index -> returns true
        CinemaCard copy = new CinemaCard(cinema, 0);
        assertTrue(cinemaCard.equals(copy));

        // same object -> returns true
        assertTrue(cinemaCard.equals(cinemaCard));

        // null -> returns false
        assertFalse(cinemaCard.equals(null));

        // different types -> returns false
        assertFalse(cinemaCard.equals(0));

        // different cinema, same index -> returns false
        Cinema differentCinema = new CinemaBuilder().withName("differentName").build();
        assertFalse(cinemaCard.equals(new CinemaCard(differentCinema, 0)));

        // same cinema, different index -> returns false
        assertFalse(cinemaCard.equals(new CinemaCard(cinema, 1)));
    }

    /**
     * Asserts that {@code cinemaCard} displays the details of {@code expectedCinema} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CinemaCard cinemaCard, Cinema expectedCinema, int expectedId) {
        guiRobot.pauseForHuman();

        CinemaCardHandle cinemaCardHandle = new CinemaCardHandle(cinemaCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", cinemaCardHandle.getId());

        // verify cinema details are displayed correctly
        assertCardDisplaysCinema(expectedCinema, cinemaCardHandle);
    }
}
