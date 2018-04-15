package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalClients.getTypicalClosedStudents;
import static seedu.address.testutil.TypicalClients.getTypicalStudents;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Client;

public class PersonListPanelTest extends GuiUnitTest {
    private static final ObservableList<Client> TYPICAL_CLIENTS =
            FXCollections.observableList(getTypicalStudents());
    private static final ObservableList<Client> TYPICAL_CLOSE_CLIENTS =
            FXCollections.observableList(getTypicalClosedStudents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private PersonListPanelHandle personListPanelHandle;

    @Before
    public void setUp() {
        StudentListPanel studentListPanel = new StudentListPanel(TYPICAL_CLIENTS, TYPICAL_CLOSE_CLIENTS);
        uiPartRule.setUiPart(studentListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(studentListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_CLIENTS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_CLIENTS.get(i));
            Client expectedPerson = TYPICAL_CLIENTS.get(i);
            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {

        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PersonCardHandle expectedCard = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedCard = personListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
