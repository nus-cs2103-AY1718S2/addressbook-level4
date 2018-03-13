package seedu.address.model.alias;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.UniqueAliasList;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.logic.commands.Command;

import static java.util.Objects.requireNonNull;
import java.util.HashMap;

public class UniqueAliasList {

    private final static HashMap<String, String> internalList = new HashMap<String, String>();

    /**
     * Constructs empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent Alias as the given argument.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return internalList.containsKey(toCheck);
    }

    /**
     * Returns the command of the alias.
     */
    public String getCommandFromAlias(String alias) {
        requireNonNull(alias);
        return internalList.get(alias);
    }

    /**
     * Adds a Alias to the list.
     *
     * @throws DuplicateAliasException if the Alias to add is a duplicate of an existing Alias in the list.
     */
    public void add(Alias toAdd) throws DuplicateAliasException {
        requireNonNull(toAdd);
        if (contains(toAdd.aliasName)) {
            throw new DuplicateAliasException();
        }
        internalList.put(toAdd.aliasName, toAdd.command);

    }
}
