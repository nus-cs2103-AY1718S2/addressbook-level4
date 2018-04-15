package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPair;

import org.junit.Test;

import guitests.guihandles.PairCardHandle;
import seedu.address.model.pair.Pair;
import seedu.address.testutil.PairBuilder;

public class PairCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Pair pairWithNoTags = new PairBuilder().withTags(new String[0]).build();
        PairCard pairCard = new PairCard(pairWithNoTags, 1);
        uiPartRule.setUiPart(pairCard);
        assertCardDisplay(pairCard, pairWithNoTags, 1);

        // with tags
        Pair pairWithTags = new PairBuilder().build();
        pairCard = new PairCard(pairWithTags, 2);
        uiPartRule.setUiPart(pairCard);
        assertCardDisplay(pairCard, pairWithTags, 2);
    }

    @Test
    public void equals() {
        Pair pair = new PairBuilder().build();
        PairCard pairCard = new PairCard(pair, 0);

        // same pair, same index -> returns true
        PairCard copy = new PairCard(pair, 0);
        assertTrue(pairCard.equals(copy));

        // same object -> returns true
        assertTrue(pairCard.equals(pairCard));

        // null -> returns false
        assertFalse(pairCard.equals(null));

        // different types -> returns false
        assertFalse(pairCard.equals(0));

        // different pair, same index -> returns false
        Pair differentPair = new PairBuilder().withStudent(ELLE).build();
        assertFalse(pairCard.equals(new PairCard(differentPair, 0)));

        // same pair, different index -> returns false
        assertFalse(pairCard.equals(new PairCard(pair, 1)));
    }

    /**
     * Asserts that {@code pairCard} displays the details of {@code expectedPair} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PairCard pairCard, Pair expectedPair, int expectedId) {
        guiRobot.pauseForHuman();

        PairCardHandle pairCardHandle = new PairCardHandle(pairCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", pairCardHandle.getId());

        // verify pair details are displayed correctly
        assertCardDisplaysPair(expectedPair, pairCardHandle);
    }
}
