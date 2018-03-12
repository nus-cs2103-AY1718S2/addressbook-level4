package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Activity;
import seedu.address.testutil.PersonBuilder;

public class ActivityCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Activity activityWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(activityWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, activityWithNoTags, 1);

        // with tags
        Activity activityWithTags = new PersonBuilder().build();
        personCard = new PersonCard(activityWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, activityWithTags, 2);
    }

    @Test
    public void equals() {
        Activity activity = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(activity, 0);

        // same activity, same index -> returns true
        PersonCard copy = new PersonCard(activity, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different activity, same index -> returns false
        Activity differentActivity = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentActivity, 0)));

        // same activity, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(activity, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedActivity} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Activity expectedActivity, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify activity details are displayed correctly
        assertCardDisplaysPerson(expectedActivity, personCardHandle);
    }
}
