package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.coin.UniqueCoinList;

public class UniqueCoinListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCoinList uniqueCoinList = new UniqueCoinList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueCoinList.asObservableList().remove(0);
    }
}
