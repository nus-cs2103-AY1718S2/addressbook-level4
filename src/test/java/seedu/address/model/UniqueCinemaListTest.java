package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.cinema.UniqueCinemaList;

public class UniqueCinemaListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCinemaList uniqueCinemaList = new UniqueCinemaList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueCinemaList.asObservableList().remove(0);
    }
}
