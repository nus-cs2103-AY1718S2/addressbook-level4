package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.tag.Tag;

//@@author karenfrilya97
/**
 * JAXB-friendly version of the Activity.
 */
public abstract class XmlAdaptedActivity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s's %s field is missing!";

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String dateTime;
    @XmlElement
    protected String remark;
    @XmlElement
    protected boolean isCompleted;
    @XmlElement
    protected List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedActivity.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedActivity() {}

    /**
     * Constructs an {@code XmlAdaptedActivity} with the given activity details.
     */
    public XmlAdaptedActivity(String name, String dateTime, String remark, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.dateTime = dateTime;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        isCompleted = false;
    }

    /**
     * Converts a given Activity into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedActivity
     */
    public XmlAdaptedActivity(Activity source) {
        name = source.getName().fullName;
        dateTime = source.getDateTime().toString();
        if (source.getRemark() != null) {
            remark = source.getRemark().value;
        }
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        isCompleted = source.isCompleted();
    }

    /**
     * Converts this jaxb-friendly adapted activity object into the model's Activity object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted activity
     */
    public abstract Activity toModelType() throws IllegalValueException;

    public abstract String getActivityType();

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedActivity)) {
            return false;
        }

        XmlAdaptedActivity otherActivity = (XmlAdaptedActivity) other;
        return Objects.equals(name, otherActivity.name)
                && Objects.equals(dateTime, otherActivity.dateTime)
                && Objects.equals(remark, otherActivity.remark)
                && tagged.equals(otherActivity.tagged)
                && this.isCompleted == otherActivity.isCompleted;
    }
}
