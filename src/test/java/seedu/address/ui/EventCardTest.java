package seedu.address.ui;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import seedu.address.model.activity.Activity;
import seedu.address.testutil.EventBuilder;


//@@author jasmoon
public class EventCardTest extends GuiUnitTest {

    public void display() {
        // no tags
        Activity activityWithNoTags = new EventBuilder().withTags(new String[0]).build();
        EventCard eventCard = new EventCard(activityWithNoTags, 1);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, activityWithNoTags, 1);

        // with tags
        Activity activityWithTags = new EventBuilder().build();
        eventCard = new EventCard(activityWithTags, 2);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, activityWithTags, 2);
    }

    @Test
    public void equals() {
        Activity activity = new EventBuilder().build();
        EventCard eventCard = new EventCard(activity, 0);

        // same activity, same index -> returns true
        EventCard copy = new EventCard(activity, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different activity, same index -> returns false
        Activity differentActivity = new EventBuilder().withName("differentName").build();
        assertFalse(eventCard.equals(new EventCard(differentActivity, 0)));

        // same activity, different index -> returns false
        assertFalse(eventCard.equals(new EventCard(activity, 1)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedActivity} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, Activity expectedActivity, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify activity details are displayed correctly
        assertCardDisplaysEvent(expectedActivity, eventCardHandle);
    }

}

