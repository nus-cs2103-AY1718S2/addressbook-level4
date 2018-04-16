package seedu.organizer.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.organizer.model.recurrence.Recurrence;

//@@author natania-d
/**
 * JAXB-friendly adapted version of the Recurrence.
 */
public class XmlAdaptedRecurrence {

    @XmlElement(required = true)
    private boolean isRecurring;
    @XmlElement(required = true)
    private int recurrenceGroup;

    /**
     * Constructs an XmlAdaptedRecurrence.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecurrence() {}

    /**
     * Constructs a {@code XmlAdaptedRecurrence} with the given {@code isRecurring} and {@code recurrenceGroup}.
     */
    public XmlAdaptedRecurrence(boolean isRecurring, int recurrenceGroup) {
        this.isRecurring = isRecurring;
        this.recurrenceGroup = recurrenceGroup;
    }

    /**
     * Converts a given Recurrence into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedRecurrence(Recurrence source) {
        isRecurring = source.isRecurring;
        recurrenceGroup = source.recurrenceGroup;
    }

    public boolean getIsRecurring() {
        return isRecurring;
    }

    public int getRecurrenceGroup() {
        return recurrenceGroup;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Recurrence object.
     */
    public Recurrence toModelType() {
        return new Recurrence(isRecurring, recurrenceGroup);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecurrence)) {
            return false;
        }

        return isRecurring == (((XmlAdaptedRecurrence) other).isRecurring)
                && recurrenceGroup == (((XmlAdaptedRecurrence) other).recurrenceGroup);
    }
}
