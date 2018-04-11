package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.UniqueKey;

//@@author demitycho
/**
 * JAXB-friendly version of the Lesson.
 */
public class XmlAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    @XmlElement(required = true)
    private String key;
    @XmlElement(required = true)
    private String day;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;

    /**
     * Constructs an XmlAdaptedLesson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLesson() {}

    /**
     * Constructs an {@code XmlAdaptedLesson} with the given student details.
     */
    public XmlAdaptedLesson(String key, String day, String startTime, String endTime) {
        this.key = key;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given Student into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLesson
     */
    public XmlAdaptedLesson(Lesson source) {
        key = source.getUniqueKey().uniqueKey;
        day = source.getDay().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
    }

    /**
     * Converts this jaxb-friendly adapted student object into the model's Student object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student
     */
    public Lesson toModelType() throws IllegalValueException {
        if (this.key == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, UniqueKey.class.getSimpleName()));
        }
        if (!UniqueKey.isValidUniqueKey(this.key)) {
            throw new IllegalValueException(UniqueKey.MESSAGE_UNIQUE_KEY_CONSTRAINTS);
        }
        final UniqueKey uniqueKey = new UniqueKey(this.key);

        if (this.day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        if (!Day.isValidDay(this.day)) {
            throw new IllegalValueException(Day.MESSAGE_DAY_CONSTRAINTS);
        }
        final Day day = new Day(this.day);

        if (this.startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(this.startTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time startTime = new Time(this.startTime);

        if (this.endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(this.endTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time endTime = new Time(this.endTime);

        return new Lesson(uniqueKey, day, startTime, endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLesson)) {
            return false;
        }

        XmlAdaptedLesson otherLesson = (XmlAdaptedLesson) other;
        return Objects.equals(key, otherLesson.key)
                && Objects.equals(day, otherLesson.day)
                && Objects.equals(startTime, otherLesson.startTime)
                && Objects.equals(endTime, otherLesson.endTime);
    }
}
