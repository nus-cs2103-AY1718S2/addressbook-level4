package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Event in the event book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements CalendarEvent {

    private ObjectProperty<String> title;
    private ObjectProperty<String> description;
    private ObjectProperty<String> location;
    private ObjectProperty<String> datetime;

    /**
     * Every field must be present and not null.
     */
    public Event(String title, String description, String location, String datetime) {
        requireAllNonNull(title, description, location, datetime);
        this.title = new SimpleObjectProperty<>(title);
        this.description = new SimpleObjectProperty<>(description);
        this.location = new SimpleObjectProperty<>(location);
        this.datetime = new SimpleObjectProperty<>(datetime);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(CalendarEvent source) {
        this(source.getTitle(), source.getDescription(), source.getLocation(), source.getDatetime());
    }

    @Override
    public ObjectProperty<String> titleProperty() {
        return title;
    }

    @Override
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(requireNonNull(title));
    }

    @Override
    public ObjectProperty<String> descriptionProperty() {
        return description;
    }

    @Override
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(requireNonNull(description));
    }

    @Override
    public ObjectProperty<String> locationProperty() {
        return location;
    }

    @Override
    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(requireNonNull(location));
    }

    @Override
    public ObjectProperty<String> datetimeProperty() {
        return datetime;
    }

    @Override
    public String getDatetime() {
        return datetime.get();
    }

    public void setDatetime(String datetime) {
        this.datetime.set(requireNonNull(datetime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarEvent // instanceof handles nulls
                && this.isSameStateAs((CalendarEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, description, location, datetime);
    }

    @Override
    public String toString() {
        return "";
    }
}
