package seedu.address.commons.events.ui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;

//@@author jingyinno
/**
 * Represents a list of aliases that have been set
 */
public class AliasListEvent extends BaseEvent {

    private final ObservableList<ArrayList<String>> aliases;

    public AliasListEvent(ObservableList<ArrayList<String>> aliases) {
        this.aliases = aliases;
    }

    public ObservableList<ArrayList<String>> getAliases() {
        return aliases;
    }

    @Override
    public String toString() {
        return "AliasListEvent";
    }
}
