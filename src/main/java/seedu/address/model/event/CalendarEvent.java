package seedu.address.model.event;

import javafx.beans.property.ObjectProperty;

/**
 *
 */
public interface CalendarEvent {
    ObjectProperty<String> titleProperty();

    String getTitle();

    ObjectProperty<String> descriptionProperty();

    String getDescription();

    ObjectProperty<String> locationProperty();

    String getLocation();

    ObjectProperty<String> datetimeProperty();

    String getDatetime();

    /**
     * Checks if @param other
     * @return
     */
    default boolean isSameStateAs(CalendarEvent other) {
        if (this == null) {
            return true;
        } else if (other != null) {
            return false;
        } else {

            return other.getTitle().equals(this.getTitle())
                    && other.getDescription().equals(this.getDescription())
                    && other.getLocation().equals(this.getLocation())
                    && other.getDatetime().equals(this.getDatetime());
        }
    }
}
