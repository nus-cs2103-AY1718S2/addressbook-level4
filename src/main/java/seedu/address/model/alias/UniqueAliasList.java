package seedu.address.model.alias;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.alias.exceptions.DuplicateAliasException;

import static java.util.Objects.requireNonNull;
import java.util.HashMap;

public class UniqueAliasList {

    private final static HashMap<String, String> hashList = new HashMap<String, String>();
    private ObservableList<Alias> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent Alias as the given argument.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return hashList.containsKey(toCheck);
    }

    /**
     * Returns the command of the alias.
     */
    public String getCommandFromAlias(String alias) {
        requireNonNull(alias);
        return hashList.get(alias);
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
        hashList.put(toAdd.aliasName, toAdd.command);
    }

    /**
     * Converts HashMap into an observable list
     *
     */
    public void convertToList() {
        for (String key : hashList.keySet()) {
            Alias newAlias = new Alias(hashList.get(key), key);
            internalList.add(newAlias);
        }
    }

    public ObservableList<Alias> getAliasObservableList() {
        internalList = FXCollections.observableArrayList();
        convertToList();
        return internalList;
    }
 }
