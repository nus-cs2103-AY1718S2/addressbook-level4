package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCreditorCardDisplays;

import org.junit.Test;

import guitests.guihandles.CreditorCardHandle;
import seedu.address.model.person.Creditor;
import seedu.address.testutil.CreditorBuilder;
//@@author ongkc
public class CreditorCardTest extends GuiUnitTest {

    @Test
    public void display() {

        Creditor creditor = new CreditorBuilder().build();
        CreditorCard creditorCard = new CreditorCard(creditor, 1);
        uiPartRule.setUiPart(creditorCard);
        assertCreditorCardDisplay(creditorCard, creditor, 1);
    }

    @Test
    public void equals() {
        Creditor creditor = new CreditorBuilder().build();
        CreditorCard creditorCard = new CreditorCard(creditor, 0);

        // same person, same index -> returns true
        CreditorCard copy = new CreditorCard(creditor, 0);
        assertTrue(creditorCard.equals(copy));

        // same object -> returns true
        assertTrue(creditorCard.equals(creditorCard));

        // null -> returns false
        assertFalse(creditorCard.equals(null));

        // different types -> returns false
        assertFalse(creditorCard.equals(0));

    }


    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCreditorCardDisplay(CreditorCard creditorCard, Creditor expectedCreditor, int expectedId) {
        guiRobot.pauseForHuman();

        CreditorCardHandle creditorCardHandle = new CreditorCardHandle(creditorCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", creditorCardHandle.getId());

        // verify person details are displayed correctly
        assertCreditorCardDisplays(expectedCreditor, creditorCardHandle);
    }
}
