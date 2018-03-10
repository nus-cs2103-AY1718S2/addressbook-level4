package seedu.address.model.person;

import javafx.beans.property.ObjectProperty;

public interface ReadOnlyPerson {
    
    ObjectProperty< Name > nameProperty();
    ObjectProperty< Address > addressProperty();
    ObjectProperty< Phone > phoneProperty();
    ObjectProperty< Email > emailProperty();
    
}
