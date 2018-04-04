package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalCinemas.getTypicalCinemas;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CINEMA;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCinema;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CinemaCardHandle;
import guitests.guihandles.CinemaListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.cinema.Cinema;

public class CinemaListPanelTest extends GuiUnitTest {
    private static final ObservableList<Cinema> TYPICAL_CINEMAS =
            FXCollections.observableList(getTypicalCinemas());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_CINEMA);

    private CinemaListPanelHandle cinemaListPanelHandle;

    @Before
    public void setUp() {
        CinemaListPanel cinemaListPanel = new CinemaListPanel(TYPICAL_CINEMAS);
        uiPartRule.setUiPart(cinemaListPanel);

        cinemaListPanelHandle = new CinemaListPanelHandle(getChildNode(cinemaListPanel.getRoot(),
                CinemaListPanelHandle.CINEMA_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_CINEMAS.size(); i++) {
            cinemaListPanelHandle.navigateToCard(TYPICAL_CINEMAS.get(i));
            Cinema expectedCinema = TYPICAL_CINEMAS.get(i);
            CinemaCardHandle actualCard = cinemaListPanelHandle.getCinemaCardHandle(i);

            assertCardDisplaysCinema(expectedCinema, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        CinemaCardHandle expectedCard = cinemaListPanelHandle.getCinemaCardHandle(INDEX_SECOND_CINEMA.getZeroBased());
        CinemaCardHandle selectedCard = cinemaListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
