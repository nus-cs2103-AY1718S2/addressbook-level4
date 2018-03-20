package seedu.address.model.timetableEntry;

import seedu.address.model.person.Person;

/**
 * Represents a timetable entry added to Employees Tracker
 */
public class TimetableEntry {
    private String calendarId;
    private Person owner;
    private String id;
    private String endDate;

    public TimetableEntry(String calendarId, Person owner, String eventId, String endDate) {
        this.calendarId = calendarId;
        this.owner = owner;
        this.id = eventId;
        this.endDate = endDate;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public Person getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((TimetableEntry) obj).id);
    }
}
