//@@author AJZ1995
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonPanelHandle;
import seedu.address.model.person.Person;

public class PersonPanelTest extends GuiUnitTest {

    private PersonPanel personPanel;
    private PersonPanelHandle personPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> personPanel = new PersonPanel());
        uiPartRule.setUiPart(personPanel);
        personPanelHandle = new PersonPanelHandle(personPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        personPanelHandle.loadPersonPage(ALICE);
        assertPersonIsDisplayed(ALICE, personPanelHandle);
        personPanelHandle.loadPersonPage(BOB);
        assertPersonIsDisplayed(BOB, personPanelHandle);
    }

    /**
     * Asserts that {@code personPanelHandle} displays the details of {@code expectedPerson} correctly
     */
    private void assertPersonIsDisplayed(Person expectedPerson, PersonPanelHandle personPanelHandle) {
        guiRobot.pauseForHuman();
        assertEquals(expectedPerson.getName().toString(), personPanelHandle.getName());
        assertEquals(expectedPerson.getPhone().toString(), personPanelHandle.getPhone());
        assertEquals(expectedPerson.getEmail().toString(), personPanelHandle.getEmail());
        assertEquals(expectedPerson.getAddress().toString(), personPanelHandle.getAddress());
    }

    /*
     * Asserts that {@code personPanelHandle} displays the groups of {@code expectedPerson} correctly
     */
    private void assertGroupsAreDisplayed(Person expectedPerson, PersonPanelHandle personPanelHandle) {
        assertEquals(expectedPerson.getGroupTags().stream()
                        .map(groupLabels -> groupLabels.tagName).collect(Collectors.toList()),
                personPanelHandle.getGroups());
    }

    /*
     * Asserts that {@code personPanelHandle} displays the preferences of {@code expectedPerson} correctly
     */
    private void assertPreferencesAreDisplayed(Person expectedPerson, PersonPanelHandle personPanelHandle) {
        assertEquals(expectedPerson.getPreferenceTags().stream()
                        .map(preferenceLabels -> preferenceLabels.tagName).collect(Collectors.toList()),
                personPanelHandle.getPreferences());
    }
}
