package seedu.address.model.timetableentry;

/**
 * Represents a timetable entry added to Employees Tracker
 */
public class TimetableEntry {
    private String calendarId;
    private String id;
    private String endDate;
    private String ownerId;

    public TimetableEntry(String calendarId, String id, String endDate, String ownerId) {
        this.calendarId = calendarId;
        this.id = id;
        this.endDate = endDate;
        this.ownerId = ownerId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public String getId() {
        return id;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
