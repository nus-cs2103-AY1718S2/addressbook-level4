package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.cell.CellMap;

public class CellMapTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getCellList_modifyList_throwsUnsupportedOperationException() {
        CellMap cellMap = new CellMap();
        thrown.expect(UnsupportedOperationException.class);
        cellMap.getCellList().remove(0);
    }

}
