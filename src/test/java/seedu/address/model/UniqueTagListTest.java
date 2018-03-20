package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }

    @Test
    public void equals_sameList_true() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_HUSBAND));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList2.add(new Tag(VALID_TAG_HUSBAND));
        assertEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertNotEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        assertNotEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        assertEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());

        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_HUSBAND));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList2.add(new Tag(VALID_TAG_HUSBAND));
        assertEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertNotEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        assertNotEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());
    }

    @Test
    public void duplicateTag() throws Exception {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.add(new Tag(VALID_TAG_FRIEND));
        thrown.expect(UniqueTagList.DuplicateTagException.class);
        uniqueTagList.add(new Tag(VALID_TAG_FRIEND));
    }
}
