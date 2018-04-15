//@@author LeonidAgarth
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_NDP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
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

    @Test
    public void setEvent_editedEvent_success() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        uniqueEventList.setEvent(ndp, f1);
        uniqueEventList2.add(f1);
        assertEquals(uniqueEventList, uniqueEventList2);
    }

    @Test
    public void setEvent_wrongEvent_throwsEventNotFoundException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.setEvent(f1, ndp);
    }

    @Test
    public void setEvent_duplicateEvent_throwsDuplicateEventException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        uniqueEventList.add(f1);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvent(f1, ndp);
    }

    @Test
    public void removeEvent_wrongEvent_throwsEventNotFoundException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        uniqueEventList.add(ndp);
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.removeEvent(new EventBuilder().build());
    }

    @Test
    public void removeEvent_correctEvent_success() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(new EventBuilder().build());
        uniqueEventList.removeEvent(new EventBuilder().build());
        assertEquals(uniqueEventList, new UniqueEventList());
    }

    @Test
    public void setEvents_correctParameters_success() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();

        uniqueEventList2.add(new EventBuilder().build());
        uniqueEventList1.setEvents(uniqueEventList2);
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList1.setEvents(uniqueEventList2);
        assertEquals(uniqueEventList1, uniqueEventList2);

        List<Event> events = new ArrayList<Event>();
        events.add(new EventBuilder().build());
        events.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        events.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList1.setEvents(events);
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void setEvents_null_throwsNullPointerException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((List<Event>) null);
    }

    @Test
    public void iterator() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());

        Iterator<Event> iter = uniqueEventList1.iterator();
        while (iter.hasNext()) {
            uniqueEventList2.add(iter.next());
        }

        assertEquals(uniqueEventList1, uniqueEventList2);
    }
}
