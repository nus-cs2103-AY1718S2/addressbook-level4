//@@author IzHoBX
package seedu.address.model.notification;

import seedu.address.model.person.Person;

/**
 * Represents a timetable entry added to Employees Tracker
 */
public class Notification {
    private String title;
    private String calendarId;
    private String eventId;
    private String endDate;
    private String ownerId;

    public Notification(String title, String calendarId, String eventId, String endDate, String ownerId) {
        assert(title != null && eventId != null && endDate != null && ownerId != null && !ownerId.equals(Person
                .UNINITIALISED_ID + ""));
        this.title = title;
        this.calendarId = calendarId;
        this.eventId = eventId;
        this.endDate = endDate;
        this.ownerId = ownerId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String toDisplayString() {
        return "Event " + getTitle() + " ended at " + getEndDateDisplay();
    }

    public String getEndDateDisplay() {
        return getEndDate().substring(13, 23) + " " + getEndDate().substring(24, 32);
    }

    @Override
    public String toString() {
        return title;
    }
}
