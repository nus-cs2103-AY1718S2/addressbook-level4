package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertDebtorCardDisplays;

import org.junit.Test;

import guitests.guihandles.DebtorCardHandle;
import seedu.address.model.person.Debtor;
import seedu.address.testutil.DebtorBuilder;
//@@author ongkc
public class DebtorCardTest extends GuiUnitTest {

    @Test
    public void display() {

        Debtor debtor = new DebtorBuilder().build();
        DebtorCard debtorCard = new DebtorCard(debtor, 1);
        uiPartRule.setUiPart(debtorCard);
        assertDebtorCardDisplay(debtorCard, debtor, 1);
    }

    @Test
    public void equals() {
        Debtor debtor = new DebtorBuilder().build();
        DebtorCard debtorCard = new DebtorCard(debtor, 0);

        // same person, same index -> returns true
        DebtorCard copy = new DebtorCard(debtor, 0);
        assertTrue(debtorCard.equals(copy));

        // same object -> returns true
        assertTrue(debtorCard.equals(debtorCard));

        // null -> returns false
        assertFalse(debtorCard.equals(null));

        // different types -> returns false
        assertFalse(debtorCard.equals(0));

    }


    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertDebtorCardDisplay(DebtorCard debtorCard, Debtor expectedDebtor, int expectedId) {
        guiRobot.pauseForHuman();

        DebtorCardHandle debtorCardHandle = new DebtorCardHandle(debtorCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", debtorCardHandle.getId());

        // verify person details are displayed correctly
        assertDebtorCardDisplays(expectedDebtor, debtorCardHandle);
    }
}
