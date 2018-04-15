package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertPanelDisplaysDetails;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.DetailsPanelHandle;

import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

//@@author sherlynng
public class DetailsPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStubStudent;
    private PersonPanelSelectionChangedEvent selectionChangedEventStubTutor;

    private DetailsPanel detailsPanel;
    private DetailsPanelHandle detailsPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStubStudent = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        selectionChangedEventStubTutor = new PersonPanelSelectionChangedEvent(new PersonCard(BENSON, 0));

        guiRobot.interact(() -> detailsPanel = new DetailsPanel());
        uiPartRule.setUiPart(detailsPanel);

        detailsPanelHandle = new DetailsPanelHandle(detailsPanel.getRoot());
    }

    @Test
    public void display() {
        // student
        Person student = ALICE;
        postNow(selectionChangedEventStubStudent);
        assertDetailsDisplay(student);

        // tutor
        Person tutor = BENSON;
        postNow(selectionChangedEventStubTutor);
        assertDetailsDisplay(tutor);
    }

    /**
     * Asserts that {@code detailsPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertDetailsDisplay(Person expectedPerson) {
        guiRobot.pauseForHuman();

        // verify person details are displayed correctly
        assertPanelDisplaysDetails(expectedPerson, detailsPanelHandle);
    }
}
