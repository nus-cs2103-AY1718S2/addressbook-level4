package seedu.address.storage;
//@@author SuxianAlicia
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CalendarManager;
import seedu.address.model.ReadOnlyCalendarManager;

/**
 * An Immutable CalendarManager that is serializable to XML format
 */
@XmlRootElement(name = "calendarmanager")
public class XmlSerializableCalendarManager {
    @XmlElement
    private List<XmlAdaptedCalendarEntry> calendarEntries;

    /**
     * Creates an empty XmlSerializableCalendarManager.
     */
    public XmlSerializableCalendarManager() {
        calendarEntries = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCalendarManager(ReadOnlyCalendarManager src) {
        this();
        calendarEntries.addAll(src.getCalendarEntryList().stream().map(XmlAdaptedCalendarEntry::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this calendarManager into the model's {@code CalendarManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCalendarEntry}.
     */
    public CalendarManager toModelType() throws IllegalValueException {
        CalendarManager calendarManager = new CalendarManager();
        for (XmlAdaptedCalendarEntry entry: calendarEntries) {
            calendarManager.addCalendarEntry(entry.toModelType());
        }

        return calendarManager;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCalendarManager)) {
            return false;
        }

        XmlSerializableCalendarManager otherCm = (XmlSerializableCalendarManager) other;
        return calendarEntries.equals(otherCm.calendarEntries);
    }
}
