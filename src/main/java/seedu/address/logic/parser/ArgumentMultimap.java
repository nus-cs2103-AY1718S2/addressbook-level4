package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {

    /** Prefixes mapped to their respective arguments**/
    private final Map<TokenType, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     *
     * @param prefixTokenType TokenType key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(TokenType prefixTokenType, String argValue) {
        List<String> argValues = getAllValues(prefixTokenType);
        argValues.add(argValue);
        argMultimap.put(prefixTokenType, argValues);
    }

    /**
     * Returns the last value of {@code prefixTokenType}.
     */
    public Optional<String> getValue(TokenType prefixTokenType) {
        List<String> values = getAllValues(prefixTokenType);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code prefixTokenType}.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(TokenType prefixTokenType) {
        if (!argMultimap.containsKey(prefixTokenType)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(argMultimap.get(prefixTokenType));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(TokenType.STRING).orElse("");
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in this
     * {@code ArgumentMultimap}.
     */
    public boolean arePrefixesPresent(TokenType... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> getValue(prefix).isPresent());
    }

}
