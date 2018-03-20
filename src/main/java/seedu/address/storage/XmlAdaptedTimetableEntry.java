package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.timetableEntry.TimetableEntry;

/**
 * JAXB-friendly adapted version of the TimetableEntry.
 */
public class XmlAdaptedTimetableEntry {

    @XmlElement(required = true)
    private String calendarId;
    @XmlElement(required = true)
    private String ownerName;
    @XmlElement(required = true)
    private String entryName;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String ownerEmail;
    @XmlElement(required = true)
    private String id;

    /**
     * Constructs an XmlAdaptedTimetableEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTimetableEntry() {}

    /**
     * Constructs a {@code XmlAdaptedTimetableEntry} with the given timetable entry details.
     */
    public XmlAdaptedTimetableEntry(String calendarId, String ownerName, String entryName,
                                    String endDate, String ownerEmail, String id) {
        this.calendarId = calendarId;
        this.ownerName = ownerName;
        this.entryName = entryName;
        this.endDate = endDate;
        this.ownerEmail = ownerEmail;
        this.id = id;
    }

    /**
     * Converts a given TimetableEntry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTimetableEntry(TimetableEntry source) {
        this.calendarId = source.getCalendarId();
        this.ownerName = source.getOwnerName();
        this.entryName = source.getEntryName();
        this.endDate = source.getEndDate();
        this.ownerEmail = source.getOwnerEmail();
        this.id = source.getId();
    }

    /**
     * Converts this jaxb-friendly adapted timetable entry object into the model's TimetableEntry object.
     *
     */
    public TimetableEntry toModelType() {
        return new TimetableEntry(calendarId, ownerName, entryName, ownerEmail, id, endDate);
    }
}
