package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class UniqueTagListTest {
    private static final Tag VALID_TAG = new Tag("friends");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //@@author Caijun7
    @Test
    public void importTag_validTag_success() throws Exception {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.importTag(VALID_TAG);
        assertEquals(Arrays.asList(VALID_TAG), uniqueTagList.asObservableList());
    }
    //@@author

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }
}
