//@@author phmignot
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTransaction;

import org.junit.Test;

import guitests.guihandles.TransactionCardHandle;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TransactionBuilder;

public class TransactionCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Transaction transaction = new TransactionBuilder().build();
        TransactionCard transactionCard = new TransactionCard(transaction, 1);
        uiPartRule.setUiPart(transactionCard);
        assertCardDisplayTransaction(transactionCard, transaction, 1);
    }

    @Test
    public void equals() {
        Transaction transaction = new TransactionBuilder().build();
        TransactionCard transactionCard = new TransactionCard(transaction, 0);

        // same transaction, same index -> returns true
        TransactionCard copy = new TransactionCard(transaction, 0);
        assertTrue(transactionCard.equals(copy));

        // same object -> returns true
        assertTrue(transactionCard.equals(transactionCard));

        // null -> returns false
        assertFalse(transactionCard.equals(null));

        // different types -> returns false
        assertFalse(transactionCard.equals(0));

        // different transaction, same index -> returns false
        Transaction differentTransaction = new TransactionBuilder()
                .withPayer(SampleDataUtil.getSamplePersons()[1])
                .build();
        assertFalse(transactionCard.equals(new TransactionCard(differentTransaction, 0)));
    }

    /**
     * Asserts that {@code transactionCard} displays the details of {@code expectedTransaction} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplayTransaction(TransactionCard transactionCard, Transaction expectedTransaction,
                                              int expectedId) {
        guiRobot.pauseForHuman();

        TransactionCardHandle transactionCardHandle = new TransactionCardHandle(transactionCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", transactionCardHandle.getId());

        // verify transaction details are displayed correctly
        assertCardDisplaysTransaction(expectedTransaction, transactionCardHandle);
    }
}
