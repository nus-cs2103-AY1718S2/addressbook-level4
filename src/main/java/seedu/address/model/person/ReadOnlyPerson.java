package seedu.address.model.person;

import javafx.beans.property.ObjectProperty;

/**
 * Immutable Person properties for the contact details page.
 */
public interface ReadOnlyPerson {
    ObjectProperty< Name > nameProperty();
    ObjectProperty< Address > addressProperty();
    ObjectProperty< Phone > phoneProperty();
    ObjectProperty< Email > emailProperty();
}
