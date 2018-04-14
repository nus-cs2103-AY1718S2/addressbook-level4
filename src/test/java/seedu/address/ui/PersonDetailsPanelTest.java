//@@author jaronchan
package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertTableDisplaysPerson;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonDetailsPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

public class PersonDetailsPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private PersonDetailsPanel personDetailsPanel;
    private PersonDetailsPanelHandle personDetailsPanelHandle;


    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> personDetailsPanel = new PersonDetailsPanel());
        uiPartRule.setUiPart(personDetailsPanel);

        personDetailsPanelHandle = new PersonDetailsPanelHandle(personDetailsPanel.getRoot());
    }

    @Test
    public void display() {
        //default
        assertTableDisplay(personDetailsPanel, null);

        // associated table of a person
        postNow(selectionChangedEventStub);
        assertTableDisplay(personDetailsPanel, selectionChangedEventStub.getNewSelection().person);

    }

    /**
     * Asserts that {@code personDetailsPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertTableDisplay(PersonDetailsPanel personDetailsPanel, Person expectedPerson) {
        guiRobot.pauseForHuman();

        PersonDetailsPanelHandle personDetailsPanelHandle = new PersonDetailsPanelHandle(personDetailsPanel.getRoot());

        // verify person details are displayed correctly
        assertTableDisplaysPerson(expectedPerson, personDetailsPanelHandle);
    }
}
