package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.activity.Task;
import seedu.address.model.tag.Tag;

//@@author karenfrilya97
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask extends XmlAdaptedActivity {

    private static final String ACTIVITY_TYPE = "Task";

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String dueDateTime, String remark, List<XmlAdaptedTag> tagged) {
        super(name, dueDateTime, remark, tagged);
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        super(source);
    }

    /**
     * Converts this jaxb-friendly adapted Task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
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
                    getActivityType(), "due date/time"));
        }
        if (!DateTime.isValidDateAndTime(this.dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime dateTime = new DateTime(this.dateTime);

        if (!Remark.isValidRemark(this.remark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }
        final Remark remark = new Remark(this.remark);

        final Set<Tag> tags = new HashSet<>(personTags);

        return new Task(name, dateTime, remark, tags);
    }

    @Override
    public String getActivityType() {
        return ACTIVITY_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && other instanceof XmlAdaptedTask;
    }

}
