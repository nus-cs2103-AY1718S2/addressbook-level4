package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.transaction.TransactionList;
//@@author ongkc
public class TransactionListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        TransactionList transactionList = new TransactionList();
        thrown.expect(UnsupportedOperationException.class);
        transactionList.asObservableList().remove(0);
    }
}
