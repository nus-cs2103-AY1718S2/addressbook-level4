//@@author IzHoBX
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.notification.Notification;

/**
 * JAXB-friendly adapted version of the Notification.
 */
public class XmlAdaptedNotification {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String calendarId;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String ownerId;
    @XmlElement(required = true)
    private String id;

    /**
     * Constructs an XmlAdaptedNotification.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNotification() {}

    /**
     * Constructs a {@code XmlAdaptedNotification} with the given timetable entry details.
     */
    public XmlAdaptedNotification(String title, String calendarId, String id, String endDate, String ownerId) {
        this.title = title;
        this.calendarId = calendarId;
        this.id = id;
        this.endDate = endDate;
        this.ownerId = ownerId;
    }

    /**
     * Converts a given Notification into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedNotification(Notification source) {
        this.title = title;
        this.calendarId = source.getCalendarId();
        this.endDate = source.getEndDate();
        this.ownerId = source.getOwnerId();
        this.id = source.getId();
    }

    /**
     * Converts this jaxb-friendly adapted timetable entry object into the model's Notification object.
     *
     */
    public Notification toModelType() {
        return new Notification(title, calendarId, id, endDate, ownerId);
    }
}
