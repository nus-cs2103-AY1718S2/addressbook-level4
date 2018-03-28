package seedu.address.model.event;

import java.util.List;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a event in the eventbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEventBook {

    ObjectProperty<String> titleProperty();

    String getTitle();

    ObjectProperty<String> descriptionProperty();

    String getDescription();

    ObjectProperty<String> locationProperty();

    String getLocation();

    ObjectProperty<String> datetimeProperty();

    String getDatetime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEventBook other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getLocation().equals(this.getLocation())
                && other.getDatetime().equals(this.getDatetime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Description: ")
                .append(getDescription())
                .append(" Location: ")
                .append(getLocation())
                .append(" Datetime: ")
                .append(getDatetime());
        return builder.toString();
    }

    List<? extends CalendarEvent> getEventList();
}
