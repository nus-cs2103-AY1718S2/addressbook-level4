package seedu.progresschecker.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.progresschecker.ui.testutil.GuiTestAssert.assertProfileDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.ProfilePanelHandle;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.testutil.PersonBuilder;

//@@author Livian1107
public class ProfilePanelTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        ProfilePanel profilePanel = new ProfilePanel();
        profilePanel.loadPerson(personWithNoTags);
        uiPartRule.setUiPart(profilePanel);
        assertProfileDisplay(profilePanel, personWithNoTags);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        profilePanel = new ProfilePanel();
        profilePanel.loadPerson(personWithTags);
        uiPartRule.setUiPart(profilePanel);
        assertProfileDisplay(profilePanel, personWithTags);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        ProfilePanel profilePanel = new ProfilePanel();
        profilePanel.loadPerson(person);

        // same object -> returns true
        assertTrue(profilePanel.equals(profilePanel));

        // null -> returns false
        assertFalse(profilePanel.equals(null));

        // different types -> returns false
        assertFalse(profilePanel.equals(0));
    }

    /**
     * Asserts that {@code personProfile} displays the details of {@code expectedPerson} correctly.
     */
    private void assertProfileDisplay(ProfilePanel personProfile, Person expectedPerson) {
        guiRobot.pauseForHuman();

        ProfilePanelHandle profilePanelHandle = new ProfilePanelHandle(personProfile.getRoot());

        // verify person details are displayed correctly
        assertProfileDisplaysPerson(expectedPerson, profilePanelHandle);
    }
}
