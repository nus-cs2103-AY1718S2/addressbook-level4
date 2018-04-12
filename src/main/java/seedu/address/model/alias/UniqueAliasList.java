package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.AliasCommand;
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

    private static HashMap<String, String> aliasCommandMap = new HashMap<String, String>();

    /**
     * Constructs empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent Alias as the given argument.
     */
    public static boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return aliasCommandMap.containsKey(toCheck);
    }

    /**
     * Returns the command of the alias.
     */
    public static String getCommandFromAlias(String alias) {
        requireNonNull(alias);
        return aliasCommandMap.get(alias);
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
        aliasCommandMap.put(toAdd.getAlias(), toAdd.getCommand());
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
        aliasCommandMap.remove(toRemove);
    }

    /**
     * Imports an Alias to the list if the Alias is not a duplicate of an existing Alias in the list.
     */
    public void importAlias(Alias toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd.getAlias())) {
            aliasCommandMap.put(toAdd.getAlias(), toAdd.getCommand());
        }
    }

    /**
     * Converts HashMap of alias and command pairing into an observable list of Alias objects
     */
    public void convertToList(ObservableList<Alias> internalList) {
        for (String key : aliasCommandMap.keySet()) {
            Alias newAlias = new Alias(aliasCommandMap.get(key), key);
            internalList.add(newAlias);
        }
    }

    /**
     * Getter for alias Observable list
     */
    public ObservableList<Alias> getAliasObservableList() {
        ObservableList<Alias> internalList = FXCollections.observableArrayList();
        convertToList(internalList);
        return internalList;
    }

    /**
     * Getter for aliasCommandMap
     */
    public HashMap<String, String> getAliasCommandMappings() {
        return aliasCommandMap;
    }

    /**
     * Set internList to contain {@code aliases}.
     */
    public void setAliases(Set<Alias> aliases) {
        ObservableList<Alias> internalList = FXCollections.observableArrayList();
        requireAllNonNull(aliases);
        internalList.setAll(aliases);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the aliases in this aliasCommandMap with those.
     */
    public void replaceHashmap(HashMap<String, String> aliases) {
        aliasCommandMap = aliases;
    }

    /**
     * Clears aliasCommandMap, for clear command.
     */
    public void resetHashmap() {
        aliasCommandMap.clear();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Alias> asObservableList() {
        ObservableList<Alias> internalList = FXCollections.observableArrayList();
        convertToList(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns an arraylist of arraylist of string aliases grouped by command.
     */
    public ArrayList<ArrayList<String>> extractAliasMapping() {
        ArrayList<ArrayList<String>> aliases = new ArrayList<>();
        convertAliasHashmapToArrayList(aliases);

        int largest = findMaxCommandAliasSize(aliases);
        populateEmptyAliasCells(aliases, largest);

        ArrayList<ArrayList<String>> formattedAliases = formattingArrayListForUI(aliases, largest);
        return formattedAliases;
    }

    /**
     * Returns an arraylist of arraylist of aliases organised by rows of commands.
     */
    private ArrayList<ArrayList<String>> formattingArrayListForUI(ArrayList<ArrayList<String>> aliases, int largest) {
        ArrayList<ArrayList<String>> formattedAliases = new ArrayList<>();
        for (int j = 0; j < largest; j++) {
            formattedAliases.add(new ArrayList<>());
            for (int i = 0; i < AliasCommand.getCommands().size(); i++) {
                formattedAliases.get(j).add(aliases.get(i).get(j));
            }
        }
        return formattedAliases;
    }

    /**
     * Group alias mappings by command.
     */
    private void convertAliasHashmapToArrayList(ArrayList<ArrayList<String>> aliases) {
        for (String command : AliasCommand.getCommands()) {
            aliases.add(new ArrayList<>());
        }

        for (String key: aliasCommandMap.keySet()) {
            String command = aliasCommandMap.get(key);
            aliases.get(AliasCommand.getCommands().indexOf(command)).add(key);
        }
    }

    /**
     * Find the largest alias group.
     */
    private int findMaxCommandAliasSize(ArrayList<ArrayList<String>> aliases) {
        int largest = Integer.MIN_VALUE;
        for (ArrayList<String> list : aliases) {
            largest = Math.max(largest, list.size());
        }
        return largest;
    }

    /**
     * Generate empty cells.
     */
    private void populateEmptyAliasCells(ArrayList<ArrayList<String>> aliases, int largest) {
        for (ArrayList<String> list : aliases) {
            while (list.size() < largest) {
                list.add("");
            }
        }
    }
}
