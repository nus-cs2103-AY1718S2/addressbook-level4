package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no groups
        Person personWithNoGroups = new PersonBuilder().withGroups(new String[0]).build();
        PersonCard personCard = new PersonCard(personWithNoGroups, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoGroups, 1);

        // with groups
        Person personWithGroups = new PersonBuilder().build();
        personCard = new PersonCard(personWithGroups, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithGroups, 2);

        // no preference
        Person personWithNoPref = new PersonBuilder().withPreferences(new String[0]).build();
        personCard = new PersonCard(personWithNoPref, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoPref, 1);

        // with preferences
        Person personWithPrefs = new PersonBuilder().build();
        personCard = new PersonCard(personWithPrefs, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithPrefs, 2);



    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
