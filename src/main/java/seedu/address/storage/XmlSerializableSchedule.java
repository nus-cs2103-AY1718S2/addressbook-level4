package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.Schedule;

//@@author demitycho
/**
 * An Immutable Schedule that is serializable to XML format
 */
@XmlRootElement(name = "schedule")
public class XmlSerializableSchedule {

    @XmlElement
    private List<XmlAdaptedLesson> lessons;

    /**
     * Creates an empty XmlSerializableSchedule.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSchedule() {
        lessons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSchedule(ReadOnlySchedule src) {
        this();
        lessons.addAll(src.getSchedule().stream().map(XmlAdaptedLesson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this schedule into the model's {@code Schedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedLesson}
     */
    public Schedule toModelType() throws IllegalValueException {
        Schedule schedule = new Schedule();
        for (XmlAdaptedLesson l : lessons) {
            schedule.addLesson(l.toModelType());
        }
        return schedule;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSchedule)) {
            return false;
        }

        XmlSerializableSchedule otherSchedule = (XmlSerializableSchedule) other;
        return lessons.equals(otherSchedule.lessons);
    }
}
