package seedu.address.logic.commands;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AliasListEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all persons and aliases in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons and aliases";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ArrayList<ArrayList<String>> aliasesList = model.getUIFormattedAliasList();
        ObservableList<ArrayList<String>> aliasesObservableList = FXCollections.observableArrayList(aliasesList);
        EventsCenter.getInstance().post(new AliasListEvent(aliasesObservableList));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
