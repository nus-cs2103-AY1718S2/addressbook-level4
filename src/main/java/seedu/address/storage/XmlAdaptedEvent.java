package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Location;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.tag.Tag;

//@@author karenfrilya97
/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent extends XmlAdaptedActivity {

    private static final String ACTIVITY_TYPE = "Event";

    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String location;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String name, String startDateTime, String endDateTime,
                           String location, String remark, List<XmlAdaptedTag> tagged) {
        super(name, startDateTime, remark, tagged);
        this.endDateTime = endDateTime;
        this.location = location;
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        super((Activity) source);
        endDateTime = source.getEndDateTime().toString();
        if (source.getLocation() != null) {
            location = source.getLocation().toString();
        }
    }

    /**
     * Converts this jaxb-friendly adapted Event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ectivity
     */
    @Override
    public Event toModelType() throws IllegalValueException {
        final List<Tag> activityTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            activityTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    getActivityType(), "name"));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    getActivityType(), "start date/time"));
        }
        if (!DateTime.isValidDateTime(this.dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime startDateTime = new DateTime(this.dateTime);

        if (this.endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    getActivityType(), "end date/time"));
        }
        if (!DateTime.isValidDateTime(this.endDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime endDateTime = new DateTime(this.endDateTime);
        final Location location;
        if (this.location == null) {
            location = null;
        } else if (!Location.isValidLocation(this.location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        } else {
            location = new Location(this.location);
        }

        final Remark remark;
        if (this.remark == null) {
            remark = null;
        } else if (!Remark.isValidRemark(this.remark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        } else {
            remark = new Remark(this.remark);
        }

        final Set<Tag> tags = new HashSet<>(activityTags);
        return new Event(name, startDateTime, endDateTime, location, remark, tags, this.isCompleted);
    }

    @Override
    public String getActivityType() {
        return ACTIVITY_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherEvent = (XmlAdaptedEvent) other;
        return Objects.equals(endDateTime, otherEvent.endDateTime)
                && Objects.equals(location, otherEvent.location);
    }

}
