package seedu.address.model.event;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;

import javafx.beans.property.ObjectProperty;

//@@author x3tsunayh

/**
 * Interface for Events related to Calendar View feature.
 */
public interface ReadOnlyEvent {
    ObjectProperty<String> titleProperty();

    String getTitle();

    ObjectProperty<String> descriptionProperty();

    String getDescription();

    ObjectProperty<String> locationProperty();

    String getLocation();

    ObjectProperty<Datetime> datetimeProperty();

    Datetime getDatetime();

    /**
     * Checks if @param other is of the same state as this.
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null
                && other.getTitle().equals(this.getTitle())
                && other.getDescription().equals(this.getDescription())
                && other.getLocation().equals(this.getLocation())
                && other.getDatetime().equals(this.getDatetime()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Description: ")
                .append(getDescription())
                .append(" Location: ")
                .append(getLocation())
                .append(" Datetime: ")
                .append(getDatetime().value);
        return builder.toString();
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    default String getEventDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_TITLE + getTitle() + " ");
        sb.append(PREFIX_EVENT_DESCRIPTION + getDescription() + " ");
        sb.append(PREFIX_EVENT_LOCATION + getLocation() + " ");
        sb.append(PREFIX_EVENT_DATETIME + getDatetime().value + " ");

        return sb.toString();
    }

}
