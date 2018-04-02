package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.alias.exceptions.AliasNotFoundException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;

//@@author jingyinno
/**
 * A list of aliases that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Alias#equals(Object)
 */
public class UniqueAliasList {

    private static HashMap<String, String> hashList = new HashMap<String, String>();
    private ObservableList<Alias> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent Alias as the given argument.
     */
    public static boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return hashList.containsKey(toCheck);
    }

    /**
     * Returns the command of the alias.
     */
    public static String getCommandFromAlias(String alias) {
        requireNonNull(alias);
        return hashList.get(alias);
    }

    /**
     * Adds an Alias to the list.
     *
     * @throws DuplicateAliasException if the Alias to add is a duplicate of an existing Alias in the list.
     */
    public static void add(Alias toAdd) throws DuplicateAliasException {
        requireNonNull(toAdd);
        if (contains(toAdd.getAlias())) {
            throw new DuplicateAliasException();
        }
        hashList.put(toAdd.getAlias(), toAdd.getCommand());
    }

    /**
     * Removes an Alias from the list.
     *
     * @throws AliasNotFoundException if the Alias to remove is a does not exist in the list.
     */
    public static void remove(String toRemove) throws AliasNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new AliasNotFoundException();
        }
        hashList.remove(toRemove);
    }

    /**
     * Imports an Alias to the list if the Alias is not a duplicate of an existing Alias in the list.
     */
    public void importAlias(Alias toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd.getAlias())) {
            hashList.put(toAdd.getAlias(), toAdd.getCommand());
        }
    }

    /**
     * Converts HashMap into an observable list
     */
    public void convertToList() {
        for (String key : hashList.keySet()) {
            Alias newAlias = new Alias(hashList.get(key), key);
            internalList.add(newAlias);
        }
    }

    /**
     * Getter for Observable list
     */
    public ObservableList<Alias> getAliasObservableList() {
        internalList = FXCollections.observableArrayList();
        convertToList();
        return internalList;
    }


    /**
     * Replaces the Aliases in this list with those in the argument alias list.
     */
    public void setAliases(Set<Alias> aliases) {
        requireAllNonNull(aliases);
        internalList.setAll(aliases);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Clears hashList, for clear command.
     */
    public void resetHashmap() {
        hashList.clear();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Alias> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }
}
