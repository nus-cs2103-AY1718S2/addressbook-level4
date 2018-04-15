package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDetailsDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonDetailsCardHandle;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonDetailsCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonDetailsCard personDetailsCard = new PersonDetailsCard(personWithNoTags, 1);
        uiPartRule.setUiPart(personDetailsCard);
        assertDetailsCardDisplay(personDetailsCard, personWithNoTags);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personDetailsCard = new PersonDetailsCard(personWithTags, 0);
        uiPartRule.setUiPart(personDetailsCard);
        assertDetailsCardDisplay(personDetailsCard, personWithTags);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonDetailsCard personDetailsCard = new PersonDetailsCard(person, 0);

        // same person, same index -> returns true
        PersonDetailsCard copy = new PersonDetailsCard(person, 0);
        assertTrue(personDetailsCard.equals(copy));

        // same object -> returns true
        assertTrue(personDetailsCard.equals(personDetailsCard));

        // null -> returns false
        assertFalse(personDetailsCard == null);

        // different types -> returns false
        assertFalse(personDetailsCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personDetailsCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personDetailsCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that {@code personDetailsCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertDetailsCardDisplay(PersonDetailsCard personDetailsCard, Person expectedPerson) {
        guiRobot.pauseForHuman();

        PersonDetailsCardHandle personDetailsCardHandle = new PersonDetailsCardHandle(personDetailsCard.getRoot());

        // verify person details are displayed correctly
        assertCardDetailsDisplaysPerson(expectedPerson, personDetailsCardHandle);
    }
}
