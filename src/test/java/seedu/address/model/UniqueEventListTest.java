package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.model.event.DuplicateEventException;
import seedu.address.model.event.UniqueEventList;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }

    @Test
    public void equals_sameList_true() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().build());
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        uniqueEventList2.add(new EventBuilder().build());
        assertNotEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        assertNotEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        assertEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().build());
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        uniqueEventList2.add(new EventBuilder().build());
        assertNotEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        assertNotEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());
    }

    @Test
    public void duplicateEvent() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
    }
}
