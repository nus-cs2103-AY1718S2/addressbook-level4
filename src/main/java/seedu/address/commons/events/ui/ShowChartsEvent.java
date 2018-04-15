package seedu.address.commons.events.ui;

//import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
//import seedu.address.model.person.Person;

//@@author dannyngmx94
/**
 * An event requesting to view charts in person panel.
 */
public class ShowChartsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
