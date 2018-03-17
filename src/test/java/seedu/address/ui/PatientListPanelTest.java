package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PatientCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.patient.Patient;

public class PatientListPanelTest extends GuiUnitTest {
    private static final ObservableList<Patient> TYPICAL_PATIENTS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private PersonListPanelHandle personListPanelHandle;

    @Before
    public void setUp() {
        PatientListPanel patientListPanel = new PatientListPanel(TYPICAL_PATIENTS);
        uiPartRule.setUiPart(patientListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(patientListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PATIENTS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_PATIENTS.get(i));
            Patient expectedPatient = TYPICAL_PATIENTS.get(i);
            PatientCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedPatient, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PatientCardHandle expectedCard = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PatientCardHandle selectedCard = personListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
