package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private static final String EMPTY_CELL = "";
    private HashMap<String, String> aliasCommandMap = new HashMap<String, String>();

    /**
     * Constructs an empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent alias as the given argument.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return aliasCommandMap.containsKey(toCheck);
    }

    /**
     * Returns the command of the alias.
     */
    public String getCommandFromAlias(String alias) {
        requireNonNull(alias);
        return aliasCommandMap.get(alias);
    }

    /**
     * Adds an Alias to the list.
     *
     * @throws DuplicateAliasException if the Alias to add is a duplicate of an existing Alias in the list.
     */
    public void add(Alias toAdd) throws DuplicateAliasException {
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
    public void remove(String toRemove) throws AliasNotFoundException {
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
     * Converts the HashMap of alias and command pairings into an observable list of Alias objects
     * Add in all the aliases in the given {@code aliases}
     * if the Alias is not a duplicate of an existing Alias in the list.
     */
    public void setAliases(HashMap<String, String> aliases) {
        requireNonNull(aliases);
        this.aliasCommandMap.clear();
        this.aliasCommandMap.putAll(aliases);
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
     * Gets an Observable alias list
     */
    public ObservableList<Alias> getAliasObservableList() {
        ObservableList<Alias> internalList = FXCollections.observableArrayList();
        convertToList(internalList);
        return internalList;
    }

    /**
     * Gets aliasCommandMap
     */
    public HashMap<String, String> getAliasCommandMappings() {
        return aliasCommandMap;
    }

    /**
     * Clears aliasCommandMap, for clear command.
     */
    public void resetHashmap() {
        this.aliasCommandMap = new HashMap<>();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Alias> asObservableList() {
        ObservableList<Alias> internalList = FXCollections.observableArrayList();
        convertToList(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAliasList // instanceof handles nulls
                && this.aliasCommandMap.equals(((UniqueAliasList) other).aliasCommandMap));
    }

    /**
     * Returns an ArrayList of ArrayList of string aliases grouped by command.
     */
    public ArrayList<ArrayList<String>> extractAliasMapping() {
        requireNonNull(aliasCommandMap);
        ArrayList<ArrayList<String>> aliases = new ArrayList<>();
        convertAliasHashmapToArrayList(aliases);

        int largest = findMaxCommandAliasSize(aliases);
        populateEmptyAliasCells(aliases, largest);

        ArrayList<ArrayList<String>> formattedAliases = formatArrayListForUi(aliases, largest);
        return formattedAliases;
    }

    /**
     * Returns an ArrayList of ArrayList of aliases organised by rows of commands.
     */
    private ArrayList<ArrayList<String>> formatArrayListForUi(ArrayList<ArrayList<String>> aliases, int largest) {
        ArrayList<ArrayList<String>> formattedAliases = new ArrayList<>();
        for (int j = 0; j < largest; j++) {
            generateAliasColumn(aliases, formattedAliases, j);
        }
        return formattedAliases;
    }

    /**
     * Generates an ArrayList of a row of aliases for all the commands.
     */
    private void generateAliasColumn(ArrayList<ArrayList<String>> aliases,
                                     ArrayList<ArrayList<String>> formattedAliases, int j) {
        formattedAliases.add(new ArrayList<>());
        for (int i = 0; i < AliasCommand.getCommands().size(); i++) {
            formattedAliases.get(j).add(aliases.get(i).get(j));
        }
    }

    /**
     * Groups alias mappings by command.
     */
    private void convertAliasHashmapToArrayList(ArrayList<ArrayList<String>> aliases) {
        for (int i = 0; i < AliasCommand.getCommands().size(); i++) {
            aliases.add(new ArrayList<>());
        }

        String[] sortedKeys = sortAliasKeysByAlphabeticalOrder();
        for (String key: sortedKeys) {
            String command = aliasCommandMap.get(key);
            aliases.get(AliasCommand.getCommands().indexOf(command)).add(key);
        }
    }

    /**
     * Sorts aliases by alphabetical order.
     */
    private String[] sortAliasKeysByAlphabeticalOrder() {
        String[] sortedKeys = aliasCommandMap.keySet().toArray(new String[0]);
        Arrays.sort(sortedKeys);
        return sortedKeys;
    }

    /**
     * Finds the largest alias group among all the commands.
     */
    private int findMaxCommandAliasSize(ArrayList<ArrayList<String>> aliases) {
        int largest = Integer.MIN_VALUE;
        for (ArrayList<String> list : aliases) {
            largest = Math.max(largest, list.size());
        }
        return largest;
    }

    /**
     * Generate empty cells in alias ArrayList.
     */
    private void populateEmptyAliasCells(ArrayList<ArrayList<String>> aliases, int largest) {
        for (ArrayList<String> list : aliases) {
            while (list.size() < largest) {
                list.add(EMPTY_CELL);
            }
        }
    }
}
