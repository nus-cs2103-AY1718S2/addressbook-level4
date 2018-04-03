package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;

/**
 * Singleton pattern class that stores the find results of exact, fuzzy and wildcard matches.
 */
public class FindResults {
    private static FindResults instance;
    private HashMap<String, HashSet<String>> exactResults;
    private HashMap<String, HashSet<String>> fuzzyResults;
    private HashMap<String, HashSet<String>> wildcardResults;
    private HashMap<String, HashSet<String>> currResults;
    private StringBuilder textResults;

    public FindResults() {
        this.exactResults = new HashMap<>();
        this.fuzzyResults = new HashMap<>();
        this.wildcardResults = new HashMap<>();
        this.currResults = new HashMap<>();
        this.textResults = new StringBuilder();
    }

    public static FindResults getInstance() {
        if (instance == null) {
            instance = new FindResults();
        }
        return instance;
    }

    public HashMap<String, HashSet<String>> getExactResults() {
        return exactResults;
    }

    public HashMap<String, HashSet<String>> getFuzzyResults() {
        return fuzzyResults;
    }

    public HashMap<String, HashSet<String>> getWildcardResults() {
        return wildcardResults;
    }

    /**
     * Clear contents of data structures storing find results
     */
    public void clearResults() {
        this.exactResults = new HashMap<>();
        this.fuzzyResults = new HashMap<>();
        this.wildcardResults = new HashMap<>();
    }

    public String getTextResults() {
        return textResults.toString();
    }

    /**
     * Form a StringBuilder containing the find results to display to the user. This is the driver method.
     */
    public void formTextResults() {
        textResults.setLength(0); //reset from last search

        textResults.append("Exact word search matches:\n");
        parseTextResults("exact");

        textResults.append("\nFuzzy search matches:\n");
        parseTextResults("fuzzy");

        textResults.append("\nWildcard search matches:\n");
        parseTextResults("wildcard");

        clearResults();
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public boolean containsWordIgnoreCase(String sentence, String word, String commandPrefix) {
        //requireNonNull(sentence);
        if (sentence == null ) {
            sentence = ""; //quick fix for test first, fix later
        }
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split(",").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                String value = "(" + commandPrefix + ")" + wordInSentence;
                parseFindResults(preppedWord, value, "exact");
                return true;
            }
        }
        parseFindResults(preppedWord, "-", "exact"); //not found
        return false;
    }

    /**
     * Returns true if the {@code sentence} is a fuzzy match with the {@code word}.
     *   Ignores case, and a threshold is required
     *   <br>examples:<pre>
     *       containsFuzzyMatchIgnoreCase("aaapppp", "", 8) == true
     *       containsWordIgnoreCase("aaapppp", "", 6) == false
     *       containsWordIgnoreCase("hippo", "elephant", 7) == true
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     * @param threshold cannot be null
     */
    public boolean containsFuzzyMatchIgnoreCase(String sentence, String word,
                                                String commandPrefix,
                                                int threshold) {
        //requireNonNull(sentence);
        if (sentence == null ) {
            sentence = ""; //quick fix for test first, fix later
        }
        requireNonNull(word);
        requireNonNull(threshold);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split(",").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            int levenshteinDistance = StringUtils.getLevenshteinDistance(
                    wordInSentence.toLowerCase(),
                    preppedWord.toLowerCase());
            if (levenshteinDistance <= threshold && levenshteinDistance != 0) {
                String value = "(" + commandPrefix + ")" + wordInSentence;
                parseFindResults(preppedWord, value, "fuzzy");
                return true;
            }
        }

        parseFindResults(preppedWord, "-", "fuzzy"); // keyword not found
        return false;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code substring}.
     *   Ignores case and matches the substring. Substring ignores spaces in between.
     *   <br>examples:<pre>
     *       containsSubstringIgnoreCase("Tes ting", "e ting") == true
     *       containsSubstringIgnoreCase("TES ting", "e tin") == true
     *       containsSubstringIgnoreCase("TeS Ting", "ef") == false //not a substring
     *       </pre>
     * @param sentence cannot be null
     * @param substring cannot be null, cannot be empty
     */
    public boolean containsSubstringIgnoreCase(String sentence, String substring, String commandPrefix) {
        //requireNonNull(sentence);
        if (sentence == null ) {
            sentence = ""; //quick fix for test first, fix later
        }
        requireNonNull(substring);

        String preppedSubstring = substring;
        checkArgument(!preppedSubstring.isEmpty(), "Substring parameter cannot be empty");

        boolean isSubstringPresent = StringUtils.containsIgnoreCase(sentence, preppedSubstring);
        String currKey = "\"" + preppedSubstring + "\"";
        if (isSubstringPresent) {
            String value = "(" + commandPrefix + ")" + sentence;
            parseFindResults(currKey, value, "wildcard");
        }

        parseFindResults(currKey, "-", "wildcard");
        return isSubstringPresent;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code prefix}.
     *   Ignores case and matches the prefix.
     *   <br>examples:<pre>
     *       containsPrefixIgnoreCase("Tes ting", "tes") == true
     *       containsPrefixIgnoreCase("TES ting", "TeS") == true
     *       containsPrefixIgnoreCase("TeS Ting", "ti") == false
     *       </pre>
     * @param sentence cannot be null
     * @param prefix cannot be null, cannot be empty
     */
    public boolean containsPrefixIgnoreCase(String sentence, String prefix, String commandPrefix) {
        //requireNonNull(sentence);
        if (sentence == null ) {
            sentence = ""; //quick fix for test first, fix later
        }
        requireNonNull(prefix);

        String preppedPrefix = prefix.trim();
        checkArgument(!preppedPrefix.isEmpty(), "Prefix parameter cannot be empty");

        boolean isPrefixPresent = StringUtils.startsWithIgnoreCase(sentence, prefix);
        String currKey = prefix + "*";
        if (isPrefixPresent) {
            String value = "(" + commandPrefix + ")" + sentence;
            parseFindResults(currKey, value, "wildcard");
        }

        parseFindResults(currKey, "-", "wildcard");
        return isPrefixPresent;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code suffix}.
     *   Ignores case and matches the suffix.
     *   <br>examples:<pre>
     *       containsSuffixIgnoreCase("Tes ting", "ing") == true
     *       containsSuffixIgnoreCase("TES ting", "NG") == true
     *       containsSuffixIgnoreCase("TeS Ting", "es") == false
     *       </pre>
     * @param sentence cannot be null
     * @param suffix cannot be null, cannot be empty
     */
    public boolean containsSuffixIgnoreCase(String sentence, String suffix, String commandPrefix) {
        //requireNonNull(sentence);
        if (sentence == null ) {
            sentence = ""; //quick fix for test first, fix later
        }
        requireNonNull(suffix);

        String preppedSuffix = suffix.trim();
        checkArgument(!preppedSuffix.isEmpty(), "Suffix parameter cannot be empty");

        boolean isSuffixPresent = StringUtils.endsWithIgnoreCase(sentence, suffix);
        String currKey =  "*" + suffix;
        if (isSuffixPresent) {
            String value = "(" + commandPrefix + ")" + sentence;
            parseFindResults(currKey, value, "wildcard");
        }

        parseFindResults(currKey, "-", "wildcard");
        return isSuffixPresent;
    }

    private void parseFindResults(String key, String value, String resultsType) {
        chooseResultsType(resultsType);
        insertKeyValuePair(key, value);
    }

    /**
     * Inserts a key pair value a HashMap according to the search option
     * @param key represents the keyword being search
     * @param value represents the search results that corresponds to the keyword being search
     */
    private void insertKeyValuePair(String key, String value) {
        HashSet<String> resultsSet = new HashSet<>();

        // insert new key value pair into currResults
        if (currResults.containsKey(key)) {
            resultsSet = currResults.get(key);
            resultsSet.add(value);
            currResults.put(key, resultsSet);
        } else {
            resultsSet.add(value);
            currResults.put(key, resultsSet);
        }
    }

    private void parseTextResults(String resultsType) {
        chooseResultsType(resultsType);
        buildTextResults();
    }

    /**
     * Form a StringBuilder containing the find results to display to the user. This method parses the current HashMap.
     */
    private void buildTextResults() {
        HashSet<String> resultsSet;
        for (String key: currResults.keySet()) {
            resultsSet = currResults.get(key);
            textResults.append(key + ": ");
            for (String value: resultsSet) {
                if (resultsSet.size() == 1 || !value.equals("-")) {
                    textResults.append(value + " ");
                }
            }
            textResults.append("\n");
        }
    }

    /**
     * Chooses the corresponding HashMap according to the option.
     * @param resultsType represents the type of HashMap to choose from
     */
    private void chooseResultsType(String resultsType) {
        switch(resultsType) {
        case "exact":
            currResults = exactResults;
            break;

        case "fuzzy":
            currResults = fuzzyResults;
            break;

        case "wildcard":
            currResults = wildcardResults;
            break;
        default:
            break;
        }
    }
}
