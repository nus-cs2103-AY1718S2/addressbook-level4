package seedu.address.model.timetableentry;

/**
 * Represents a timetable entry added to Employees Tracker
 */
public class TimetableEntry {
    private String calendarId;
    private String ownerName;
    private String entryName;
    private String ownerEmail;
    private String id;
    private String endDate;

    public TimetableEntry(String calendarId,
                          String ownerName,
                          String entryName,
                          String ownerEmail,
                          String id,
                          String endDate) {
        this.calendarId = calendarId;
        this.ownerName = ownerName;
        this.entryName = entryName;
        this.ownerEmail = ownerEmail;
        this.id = id;
        this.endDate = endDate;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getEntryName() {
        return entryName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
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
