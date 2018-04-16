package seedu.address.model;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalEvents.CNY;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.ReadOnlyEventBook;

//@@author x3tsunayh

public class EventBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventBook eventBook = new EventBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventBook.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEventBook_replacesData() {
        EventBook newData = getTypicalEventBook();
        eventBook.resetData(newData);
        assertEquals(newData, eventBook);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        // Repeated events should throw AssertionError
        List<Event> newEvents = Arrays.asList(new Event(CNY), new Event(CNY));
        EventBookStub newData = new EventBookStub(newEvents);

        thrown.expect(AssertionError.class);
        eventBook.resetData(newData);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        // Event List should not be altered unknowingly by unsupported operations
        thrown.expect(UnsupportedOperationException.class);
        eventBook.getEventList().remove(0);
    }

    /**
     * A stub ReadOnlyEventBook whose events lists can violate interface constraints.
     */
    private static class EventBookStub implements ReadOnlyEventBook {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        EventBookStub(Collection<? extends ReadOnlyEvent> events) {
            this.events.setAll(events);
        }

        @Override
        public ObjectProperty<String> titleProperty() {
            return null;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public ObjectProperty<String> descriptionProperty() {
            return null;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public ObjectProperty<String> locationProperty() {
            return null;
        }

        @Override
        public String getLocation() {
            return null;
        }

        @Override
        public ObjectProperty<String> datetimeProperty() {
            return null;
        }

        @Override
        public String getDatetime() {
            return null;
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }
    }
}
