package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.timetableentry.TimetableEntry;

/**
 * JAXB-friendly adapted version of the TimetableEntry.
 */
public class XmlAdaptedTimetableEntry {

    @XmlElement(required = true)
    private String calendarId;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String ownerId;
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
    public XmlAdaptedTimetableEntry(String calendarId, String id, String endDate, String ownerId) {
        this.calendarId = calendarId;
        this.id = id;
        this.endDate = endDate;
        this.ownerId = ownerId;
    }

    /**
     * Converts a given TimetableEntry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTimetableEntry(TimetableEntry source) {
        this.calendarId = source.getCalendarId();
        this.endDate = source.getEndDate();
        this.ownerId = source.getOwnerId();
        this.id = source.getId();
    }

    /**
     * Converts this jaxb-friendly adapted timetable entry object into the model's TimetableEntry object.
     *
     */
    public TimetableEntry toModelType() {
        return new TimetableEntry(calendarId, id, endDate, ownerId);
    }
}
