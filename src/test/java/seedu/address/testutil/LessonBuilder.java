package seedu.address.testutil;

import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.UniqueKey;

//@@author demitycho
/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_KEY = "ffff00";
    public static final String DEFAULT_DAY = "mon";
    public static final String DEFAULT_START_TIME = "10:00";
    public static final String DEFAULT_END_TIME = "12:00";


    private UniqueKey key;
    private Day day;
    private Time startTime;
    private Time endTime;

    public LessonBuilder() {
        key = new UniqueKey(DEFAULT_KEY);
        day = new Day(DEFAULT_DAY);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_END_TIME);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        key = lessonToCopy.getUniqueKey();
        day = lessonToCopy.getDay();
        startTime = lessonToCopy.getStartTime();
        endTime = lessonToCopy.getEndTime();
    }

    /**
     * Sets the {@code UniqueKey} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withKey(String key) {
        this.key = new UniqueKey(key);
        return this;
    }

    /**
     * Sets the {@code Day} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withStartTime(String startTime) {
        this.startTime = new Time (startTime);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withEndTime(String endTime) {
        this.endTime = new Time(endTime);
        return this;
    }

    /**
     * Builds the lesson with given attributes
     */
    public Lesson build() {
        return new Lesson(key, day, startTime, endTime);
    }
}
