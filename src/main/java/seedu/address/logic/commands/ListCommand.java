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
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons and aliases";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        HashMap<String, String> aliasesMapping = model.getAliasList();
        ArrayList<ArrayList<String>> aliasesList = extractAliases(aliasesMapping);
        ObservableList<ArrayList<String>> aliasesObservableList = FXCollections.observableArrayList(aliasesList);
        EventsCenter.getInstance().post(new AliasListEvent(aliasesObservableList));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private ArrayList<ArrayList<String>> extractAliases(HashMap<String, String> aliasesMapping) {
        assert aliasesMapping == null;
        ArrayList<ArrayList<String>> aliases = new ArrayList<>();
        ArrayList<ArrayList<String>> formattedAliases = new ArrayList<>();
        for (String command : AliasCommand.getCommands()) {
            aliases.add(new ArrayList<>());
        }

        for (String key: aliasesMapping.keySet()) {
            String command = aliasesMapping.get(key);
            aliases.get(AliasCommand.getCommands().indexOf(command)).add(key);
        }

        int largest = Integer.MIN_VALUE;
        for (ArrayList<String> list : aliases) {
            largest = Math.max(largest, list.size());
        }

        for (int i = 0; i < largest; i ++) {
            formattedAliases.add(new ArrayList<>());
        }

        // populate empty aliases
        for (ArrayList<String> list : aliases) {
            while (list.size() < largest) {
                list.add("");
            }
        }

        for (int j = 0; j < largest; j++) {
            for (int i = 0; i < AliasCommand.getCommands().size(); i++) {
                formattedAliases.get(j).add(aliases.get(i).get(j));
            }
        }

        System.out.println(formattedAliases);
        return formattedAliases;
    }
}
