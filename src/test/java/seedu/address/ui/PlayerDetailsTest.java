package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertPlayerDetailsDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PlayerDetailsHandle;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

//@@author Codee
public class PlayerDetailsTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PlayerDetails playerDetails = new PlayerDetails(personWithNoTags);
        uiPartRule.setUiPart(playerDetails);
        assertCardDisplay(playerDetails, personWithNoTags);

    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PlayerDetails playerDetails = new PlayerDetails(person);

        // same object -> returns true
        assertTrue(playerDetails.equals(playerDetails));

        // null -> returns false
        assertFalse(playerDetails.equals(null));

        // different types -> returns false
        assertFalse(playerDetails.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(playerDetails.equals(new PlayerDetails(differentPerson)));
    }

    /**
     * Asserts that {@code playerDetails} displays the details of {@code expectedPerson} correctly
     */
    private void assertCardDisplay(PlayerDetails playerDetails, Person expectedPerson) {
        guiRobot.pauseForHuman();

        PlayerDetailsHandle playerDetailsHandle = new PlayerDetailsHandle(playerDetails.getRoot());

        // verify player details are displayed correctly
        assertPlayerDetailsDisplaysPerson(expectedPerson, playerDetailsHandle);
    }
}
