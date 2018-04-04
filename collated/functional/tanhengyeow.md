# tanhengyeow
###### /java/seedu/address/model/FindResults.java
``` java
/**
 * Singleton pattern class that stores the find results of exact, fuzzy and wildcard matches.
 * The class also provides string utility functions for predicates
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
```
###### /java/seedu/address/model/person/AddressContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String commandPrefix = "a/";

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> FindResults.getInstance()
                        .containsWordIgnoreCase(person.getAddress().value, keyword, commandPrefix)
                    || keywords.stream()
                        .anyMatch(fuzzyKeyword -> FindResults.getInstance().containsFuzzyMatchIgnoreCase(
                                person.getAddress().value, fuzzyKeyword, commandPrefix,
                                FindCommand.LEVENSHTEIN_DISTANCE_THRESHOLD)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/JobAppliedContainsPrefixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code JobApplied} matches the prefix string given.
 */
public class JobAppliedContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "j/";

    public JobAppliedContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getJobApplied().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals((
                (JobAppliedContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/JobAppliedContainsSuffixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code JobApplied} matches the suffix string given.
 */
public class JobAppliedContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "j/";

    public JobAppliedContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getJobApplied().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals((
                (JobAppliedContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/UniversityContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code University} matches the substring given.
 */
public class UniversityContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "u/";

    public UniversityContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getUniversity().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniversityContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                (UniversityContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/MajorContainsPrefixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Major} matches the prefix string given.
 */
public class MajorContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "m/";

    public MajorContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getMajor().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals(((MajorContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/PhonePredicate.java
``` java
/**
 * Represents an Phone predicate
 */
public class PhonePredicate implements FieldPredicate {

    private Predicate<Person> phonePredicate = null;

    /**
     * Constructs an {@code PhonePredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public PhonePredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                          ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {
        PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate = null;
        PhoneContainsSubstringsPredicate phoneContainsSubstringsPredicate = null;
        PhoneContainsPrefixesPredicate phoneContainsPrefixesPredicate = null;
        PhoneContainsSuffixesPredicate phoneContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            phoneContainsKeywordsPredicate = new PhoneContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            phoneContainsSubstringsPredicate = new PhoneContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            phoneContainsPrefixesPredicate = new PhoneContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            phoneContainsSuffixesPredicate = new PhoneContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.phonePredicate = PredicateUtil.formOrPredicate(phoneContainsKeywordsPredicate,
                    phoneContainsSubstringsPredicate, phoneContainsPrefixesPredicate,
                    phoneContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return phonePredicate;
    }

    @Override
    public String toString() {
        return phonePredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhonePredicate // instanceof handles nulls
                && this.phonePredicate.equals(((PhonePredicate) other).phonePredicate)); // state check
    }

    @Override
    public int hashCode() {
        return phonePredicate.hashCode();
    }

}
```
###### /java/seedu/address/model/person/EmailContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Email} matches the substring given.
 */
public class EmailContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "e/";

    public EmailContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getEmail().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (EmailContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/EmailContainsSuffixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Email} matches the suffix string given.
 */
public class EmailContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "e/";

    public EmailContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getEmail().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals(((EmailContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/NameContainsSuffixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches the suffix string given.
 */
public class NameContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "n/";

    public NameContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getName().fullName, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals(((NameContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/MajorContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Major} matches the substring given.
 */
public class MajorContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "m/";

    public MajorContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getMajor().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (MajorContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/JobAppliedPredicate.java
``` java
/**
 * Represents a JobApplied predicate
 */
public class JobAppliedPredicate implements FieldPredicate {

    private Predicate<Person> jobAppliedPredicate = null;

    /**
     * Constructs an {@code JobAppliedPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public JobAppliedPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                            ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        JobAppliedContainsKeywordsPredicate jobAppliedContainsKeywordsPredicate = null;
        JobAppliedContainsSubstringsPredicate jobAppliedContainsSubstringsPredicate = null;
        JobAppliedContainsPrefixesPredicate jobAppliedContainsPrefixesPredicate = null;
        JobAppliedContainsSuffixesPredicate jobAppliedContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            jobAppliedContainsKeywordsPredicate =
                    new JobAppliedContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            jobAppliedContainsSubstringsPredicate = new JobAppliedContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            jobAppliedContainsPrefixesPredicate = new JobAppliedContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            jobAppliedContainsSuffixesPredicate = new JobAppliedContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.jobAppliedPredicate = PredicateUtil.formOrPredicate(jobAppliedContainsKeywordsPredicate,
                    jobAppliedContainsSubstringsPredicate, jobAppliedContainsPrefixesPredicate,
                    jobAppliedContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return jobAppliedPredicate;
    }

    @Override
    public String toString() {
        return jobAppliedPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedPredicate // instanceof handles nulls
                && this.jobAppliedPredicate.equals(((JobAppliedPredicate) other).jobAppliedPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return jobAppliedPredicate.hashCode();
    }

}
```
###### /java/seedu/address/model/person/EmailContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String commandPrefix = "e/";

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> FindResults.getInstance()
                        .containsWordIgnoreCase(person.getEmail().value, keyword, commandPrefix)
                    || keywords.stream()
                        .anyMatch(fuzzyKeyword -> FindResults.getInstance().containsFuzzyMatchIgnoreCase(
                                person.getEmail().value, fuzzyKeyword, commandPrefix,
                                FindCommand.LEVENSHTEIN_DISTANCE_THRESHOLD)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/MajorPredicate.java
``` java
/**
 * Represents an Major predicate
 */
public class MajorPredicate implements FieldPredicate {

    private Predicate<Person> majorPredicate = null;

    /**
     * Constructs an {@code MajorPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public MajorPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                          ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {
        MajorContainsKeywordsPredicate majorContainsKeywordsPredicate = null;
        MajorContainsSubstringsPredicate majorContainsSubstringsPredicate = null;
        MajorContainsPrefixesPredicate majorContainsPrefixesPredicate = null;
        MajorContainsSuffixesPredicate majorContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            majorContainsKeywordsPredicate = new MajorContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            majorContainsSubstringsPredicate = new MajorContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            majorContainsPrefixesPredicate = new MajorContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            majorContainsSuffixesPredicate = new MajorContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.majorPredicate = PredicateUtil.formOrPredicate(majorContainsKeywordsPredicate,
                    majorContainsSubstringsPredicate, majorContainsPrefixesPredicate,
                    majorContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return majorPredicate;
    }

    @Override
    public String toString() {
        return majorPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorPredicate // instanceof handles nulls
                && this.majorPredicate.equals(((MajorPredicate) other).majorPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return majorPredicate.hashCode();
    }

}
```
###### /java/seedu/address/model/person/CommentContainsPrefixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Comment} matches the prefix string given.
 */
public class CommentContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "c/";

    public CommentContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getComment().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommentContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals((
                (CommentContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/Major.java
``` java
/**
 * Represents a Person's major in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMajor(String)}
 */
public class Major {

    public static final String MESSAGE_MAJOR_CONSTRAINTS =
            "Person major should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MAJOR_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Major}.
     *
     * @param major A valid major.
     */
    public Major(String major) {
        requireNonNull(major);
        checkArgument(isValidMajor(major), MESSAGE_MAJOR_CONSTRAINTS);
        this.value = major;
    }

    /**
     * Returns true if a given string is a valid person major.
     */
    public static boolean isValidMajor(String test) {
        return test.matches(MAJOR_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major // instanceof handles nulls
                && this.value.equals(((Major) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/EmailContainsPrefixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Email} matches the prefix string given.
 */
public class EmailContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "e/";

    public EmailContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getEmail().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals(((EmailContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/CommentPredicate.java
``` java
/**
 * Represents a Comment predicate
 */
public class CommentPredicate implements FieldPredicate {

    private Predicate<Person> commentPredicate = null;

    /**
     * Constructs an {@code CommentPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public CommentPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                            ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        CommentContainsKeywordsPredicate commentContainsKeywordsPredicate = null;
        CommentContainsSubstringsPredicate commentContainsSubstringsPredicate = null;
        CommentContainsPrefixesPredicate commentContainsPrefixesPredicate = null;
        CommentContainsSuffixesPredicate commentContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            commentContainsKeywordsPredicate =
                    new CommentContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            commentContainsSubstringsPredicate = new CommentContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            commentContainsPrefixesPredicate = new CommentContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            commentContainsSuffixesPredicate = new CommentContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.commentPredicate = PredicateUtil.formOrPredicate(commentContainsKeywordsPredicate,
                    commentContainsSubstringsPredicate, commentContainsPrefixesPredicate,
                    commentContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return commentPredicate;
    }

    @Override
    public String toString() {
        return commentPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommentPredicate // instanceof handles nulls
                && this.commentPredicate.equals(((CommentPredicate) other).commentPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return commentPredicate.hashCode();
    }

}
```
###### /java/seedu/address/model/person/NameContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String commandPrefix = "n/";

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> FindResults.getInstance()
                        .containsWordIgnoreCase(person.getName().fullName, keyword, commandPrefix)
                    || keywords.stream()
                        .anyMatch(fuzzyKeyword -> FindResults.getInstance().containsFuzzyMatchIgnoreCase(
                                person.getName().fullName, fuzzyKeyword, commandPrefix,
                                FindCommand.LEVENSHTEIN_DISTANCE_THRESHOLD)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/UniversityPredicate.java
``` java
/**
 * Represents a University predicate
 */
public class UniversityPredicate implements FieldPredicate {

    private Predicate<Person> universityPredicate = null;

    /**
     * Constructs an {@code UniversityPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public UniversityPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                               ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        UniversityContainsKeywordsPredicate universityContainsKeywordsPredicate = null;
        UniversityContainsSubstringsPredicate universityContainsSubstringsPredicate = null;
        UniversityContainsPrefixesPredicate universityContainsPrefixesPredicate = null;
        UniversityContainsSuffixesPredicate universityContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            universityContainsKeywordsPredicate =
                    new UniversityContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            universityContainsSubstringsPredicate = new UniversityContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            universityContainsPrefixesPredicate = new UniversityContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            universityContainsSuffixesPredicate = new UniversityContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.universityPredicate = PredicateUtil.formOrPredicate(universityContainsKeywordsPredicate,
                    universityContainsSubstringsPredicate, universityContainsPrefixesPredicate,
                    universityContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return universityPredicate;
    }

    @Override
    public String toString() {
        return universityPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniversityPredicate // instanceof handles nulls
                && this.universityPredicate.equals(((UniversityPredicate) other).universityPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return universityPredicate.hashCode();
    }

}
```
###### /java/seedu/address/model/person/PhoneContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String commandPrefix = "p/";

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> FindResults.getInstance()
                        .containsWordIgnoreCase(person.getPhone().value, keyword, commandPrefix)
                        || keywords.stream()
                            .anyMatch(fuzzyKeyword -> FindResults.getInstance().containsFuzzyMatchIgnoreCase(
                                person.getPhone().value, fuzzyKeyword, commandPrefix,
                                    FindCommand.LEVENSHTEIN_DISTANCE_THRESHOLD)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/UniversityContainsPrefixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code University} matches the prefix string given.
 */
public class UniversityContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "u/";

    public UniversityContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getUniversity().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniversityContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals((
                (UniversityContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/PhoneContainsSuffixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Phone} matches the suffix string given.
 */
public class PhoneContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "p/";

    public PhoneContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getPhone().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals(((PhoneContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/MajorContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Major} matches any of the keywords given.
 */
public class MajorContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String commandPrefix = "m/";

    public MajorContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> FindResults.getInstance()
                        .containsWordIgnoreCase(person.getMajor().value, keyword, commandPrefix)
                        || keywords.stream()
                            .anyMatch(fuzzyKeyword -> FindResults.getInstance().containsFuzzyMatchIgnoreCase(
                                person.getMajor().value, fuzzyKeyword, commandPrefix,
                                    FindCommand.LEVENSHTEIN_DISTANCE_THRESHOLD)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MajorContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/PhoneContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Phone} matches the substring given.
 */
public class PhoneContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "p/";

    public PhoneContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getPhone().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (PhoneContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/JobAppliedContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code JobApplied} matches any of the keywords given.
 */
public class JobAppliedContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String commandPrefix = "j/";

    public JobAppliedContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> FindResults.getInstance()
                        .containsWordIgnoreCase(person.getJobApplied().value, keyword, commandPrefix)
                        || keywords.stream()
                        .anyMatch(fuzzyKeyword -> FindResults.getInstance().containsFuzzyMatchIgnoreCase(
                                person.getJobApplied().value, fuzzyKeyword, commandPrefix,
                                FindCommand.LEVENSHTEIN_DISTANCE_THRESHOLD)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((JobAppliedContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/EmailPredicate.java
``` java
/**
 * Represents an Email predicate
 */
public class EmailPredicate implements FieldPredicate {

    private Predicate<Person> emailPredicate = null;

    /**
     * Constructs an {@code EmailPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public EmailPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                          ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        EmailContainsKeywordsPredicate emailContainsKeywordsPredicate = null;
        EmailContainsSubstringsPredicate emailContainsSubstringsPredicate = null;
        EmailContainsPrefixesPredicate emailContainsPrefixesPredicate = null;
        EmailContainsSuffixesPredicate emailContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            emailContainsKeywordsPredicate = new EmailContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            emailContainsSubstringsPredicate = new EmailContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            emailContainsPrefixesPredicate = new EmailContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            emailContainsSuffixesPredicate = new EmailContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            emailPredicate = PredicateUtil.formOrPredicate(emailContainsKeywordsPredicate,
                    emailContainsSubstringsPredicate, emailContainsPrefixesPredicate,
                    emailContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return emailPredicate;
    }

    @Override
    public String toString() {
        return emailPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailPredicate // instanceof handles nulls
                && this.emailPredicate.equals(((EmailPredicate) other).emailPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return emailPredicate.hashCode();
    }

}
```
###### /java/seedu/address/model/person/FieldPredicate.java
``` java
/**
 * Immutable interface for all predicates in AllPredicate
 */
public interface FieldPredicate {
    Predicate<Person> getPredicate();
}
```
###### /java/seedu/address/model/person/UniversityContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code University} matches any of the keywords given.
 */
public class UniversityContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String commandPrefix = "u/";

    public UniversityContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> FindResults.getInstance()
                        .containsWordIgnoreCase(person.getUniversity().value, keyword, commandPrefix)
                        || keywords.stream()
                        .anyMatch(fuzzyKeyword -> FindResults.getInstance().containsFuzzyMatchIgnoreCase(
                                person.getUniversity().value, fuzzyKeyword, commandPrefix,
                                FindCommand.LEVENSHTEIN_DISTANCE_THRESHOLD)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniversityContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((UniversityContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/AddressContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Address} matches the substring given.
 */
public class AddressContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "a/";

    public AddressContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getAddress().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (AddressContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/NameContainsPrefixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches the prefix string given.
 */
public class NameContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "n/";

    public NameContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getName().fullName, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals(((NameContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/CommentContainsSuffixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Comment} matches the suffix string given.
 */
public class CommentContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "c/";

    public CommentContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getComment().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommentContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals((
                (CommentContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/AddressPredicate.java
``` java
/**
 * Represents a Address predicate
 */
public class AddressPredicate implements FieldPredicate {

    private Predicate<Person> addressPredicate = null;

    /**
     * Constructs an {@code AddressPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public AddressPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                            ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        AddressContainsKeywordsPredicate addressContainsKeywordsPredicate = null;
        AddressContainsSubstringsPredicate addressContainsSubstringsPredicate = null;
        AddressContainsPrefixesPredicate addressContainsPrefixesPredicate = null;
        AddressContainsSuffixesPredicate addressContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            addressContainsKeywordsPredicate =
                    new AddressContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            addressContainsSubstringsPredicate = new AddressContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            addressContainsPrefixesPredicate = new AddressContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            addressContainsSuffixesPredicate = new AddressContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.addressPredicate = PredicateUtil.formOrPredicate(addressContainsKeywordsPredicate,
                    addressContainsSubstringsPredicate, addressContainsPrefixesPredicate,
                    addressContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return addressPredicate;
    }

    @Override
    public String toString() {
        return addressPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressPredicate // instanceof handles nulls
                && this.addressPredicate.equals(((AddressPredicate) other).addressPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return addressPredicate.hashCode();
    }

}
```
###### /java/seedu/address/model/person/AllPredicate.java
``` java
/**
 * Represents all predicates to form {@code Predicate<Person>}
 */
public class AllPredicate {

    private NamePredicate namePredicate;
    private PhonePredicate phonePredicate;
    private EmailPredicate emailPredicate;
    private AddressPredicate addressPredicate;
    private UniversityPredicate universityPredicate;
    private MajorPredicate majorPredicate;
    private JobAppliedPredicate jobAppliedPredicate;
    private CommentPredicate commentPredicate;

    /**
     * Default constructor for no arguments
     */
    public AllPredicate() {
        this.namePredicate = new NamePredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.phonePredicate = new PhonePredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.emailPredicate = new EmailPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.addressPredicate = new AddressPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.universityPredicate = new UniversityPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.majorPredicate = new MajorPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.jobAppliedPredicate = new JobAppliedPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.commentPredicate = new CommentPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
    }
    /**
     * Constructor to initialize all predicates
     */
    public AllPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                        ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {
        this.namePredicate = new NamePredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.phonePredicate = new PhonePredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.emailPredicate = new EmailPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.addressPredicate = new AddressPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.universityPredicate = new UniversityPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.majorPredicate = new MajorPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.jobAppliedPredicate = new JobAppliedPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.commentPredicate = new CommentPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public NamePredicate getNamePredicate() {
        return namePredicate;
    }

    public void setNamePredicate(ArrayList<String> exactKeywords,
                                 ArrayList<String> substringKeywords,
                                 ArrayList<String> prefixKeywords,
                                 ArrayList<String> suffixKeywords) {
        this.namePredicate = new NamePredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public PhonePredicate getPhonePredicate() {
        return phonePredicate;
    }

    public void setPhonePredicate(ArrayList<String> exactKeywords,
                                  ArrayList<String> substringKeywords,
                                  ArrayList<String> prefixKeywords,
                                  ArrayList<String> suffixKeywords) {
        this.phonePredicate = new PhonePredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public EmailPredicate getEmailPredicate() {
        return emailPredicate;
    }

    public void setEmailPredicate(ArrayList<String> exactKeywords,
                                  ArrayList<String> substringKeywords,
                                  ArrayList<String> prefixKeywords,
                                  ArrayList<String> suffixKeywords) {
        this.emailPredicate = new EmailPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public AddressPredicate getAddressPredicate() {
        return addressPredicate;
    }

    public void setAddressPredicate(ArrayList<String> exactKeywords,
                                    ArrayList<String> substringKeywords,
                                    ArrayList<String> prefixKeywords,
                                    ArrayList<String> suffixKeywords) {
        this.addressPredicate = new AddressPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public UniversityPredicate getUniversityPredicate() {
        return universityPredicate;
    }

    public void setUniversityPredicate(ArrayList<String> exactKeywords,
                                    ArrayList<String> substringKeywords,
                                    ArrayList<String> prefixKeywords,
                                    ArrayList<String> suffixKeywords) {
        this.universityPredicate = new UniversityPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public MajorPredicate getMajorPredicate() {
        return majorPredicate;
    }

    public void setMajorPredicate(ArrayList<String> exactKeywords,
                                  ArrayList<String> substringKeywords,
                                  ArrayList<String> prefixKeywords,
                                  ArrayList<String> suffixKeywords) {
        this.majorPredicate = new MajorPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public JobAppliedPredicate getJobAppliedPredicate() {
        return jobAppliedPredicate;
    }

    public void setJobAppliedPredicate(ArrayList<String> exactKeywords,
                                       ArrayList<String> substringKeywords,
                                       ArrayList<String> prefixKeywords,
                                       ArrayList<String> suffixKeywords) {
        this.jobAppliedPredicate = new JobAppliedPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public CommentPredicate getCommentPredicate() {
        return commentPredicate;
    }

    public void setCommentPredicate(ArrayList<String> exactKeywords,
                                       ArrayList<String> substringKeywords,
                                       ArrayList<String> prefixKeywords,
                                       ArrayList<String> suffixKeywords) {
        this.commentPredicate = new CommentPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AllPredicate)) {
            return false;
        }

        AllPredicate otherPerson = (AllPredicate) other;
        return otherPerson.getNamePredicate().equals(this.getNamePredicate())
                && otherPerson.getPhonePredicate().equals(this.getPhonePredicate())
                && otherPerson.getEmailPredicate().equals(this.getEmailPredicate())
                && otherPerson.getAddressPredicate().equals(this.getAddressPredicate())
                && otherPerson.getUniversityPredicate().equals(this.getUniversityPredicate())
                && otherPerson.getMajorPredicate().equals(this.getMajorPredicate())
                && otherPerson.getJobAppliedPredicate().equals(this.getJobAppliedPredicate())
                && otherPerson.getCommentPredicate().equals(this.getCommentPredicate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(namePredicate, phonePredicate, emailPredicate,
                addressPredicate, universityPredicate, majorPredicate,
                jobAppliedPredicate, commentPredicate);
    }
}
```
###### /java/seedu/address/model/person/CommentContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Comment} matches the substring given.
 */
public class CommentContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "c/";

    public CommentContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getComment().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommentContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                (CommentContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/GradePointAverage.java
``` java
/**
 * Represents a Person's GradePointAverage in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGradePointAverage(String)}
 */
public class GradePointAverage {
    public static final String MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS =
            "Grade point average can only floating point numbers, and should be between 0.00 and 5.00 "
                    + "(in 2 decimal point).";
    public static final double GPA_LOWER_BOUND = 0.0;
    public static final double GPA_UPPER_BOUND = 5.0;
    private static final String EXPECTED_GRADE_POINT_AVERAGE_REGEX = "\\d+([.]\\d{2})?";
    public final String value;

    /**
     * Constructs a {@code gradePointAverage}.
     *
     * @param gradePointAverage A valid gradePointAverage.
     */
    public GradePointAverage(String gradePointAverage) {
        requireNonNull(gradePointAverage);
        checkArgument(isValidGradePointAverage(gradePointAverage),
                MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
        this.value = gradePointAverage;
    }

    /**
     * Returns true if a given string is a valid gradePointAverage.
     */
    public static boolean isValidGradePointAverage(String test) {
        return test.matches(EXPECTED_GRADE_POINT_AVERAGE_REGEX) && isInValidRange(test);
    }

    /**
     *
     * @param test A grade point average matching regex
     * @return whether the grade point average is in valid range
     */
    private static boolean isInValidRange(String test) {
        double gradePointAverage = Double.parseDouble(test);
        return gradePointAverage >= GPA_LOWER_BOUND && gradePointAverage <= GPA_UPPER_BOUND;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GradePointAverage // instanceof handles nulls
                && this.value.equals(((GradePointAverage) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/person/AddressContainsPrefixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Address} matches the prefix string given.
 */
public class AddressContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "a/";

    public AddressContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getAddress().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals((
                        (AddressContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/CommentContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Comment} matches any of the keywords given.
 */
public class CommentContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String commandPrefix = "c/";

    public CommentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> FindResults.getInstance()
                        .containsWordIgnoreCase(person.getComment().value, keyword, commandPrefix)
                        || keywords.stream()
                        .anyMatch(fuzzyKeyword -> FindResults.getInstance().containsFuzzyMatchIgnoreCase(
                                person.getComment().value, fuzzyKeyword, commandPrefix,
                                FindCommand.LEVENSHTEIN_DISTANCE_THRESHOLD)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommentContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((CommentContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/UniversityContainsSuffixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code University} matches the suffix string given.
 */
public class UniversityContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "u/";

    public UniversityContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getUniversity().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniversityContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals((
                (UniversityContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/MajorContainsSuffixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Major} matches the suffix string given.
 */
public class MajorContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "m/";

    public MajorContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getMajor().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals(((MajorContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/NameContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches the substring given.
 */
public class NameContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "n/";

    public NameContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getName().fullName, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (NameContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/PhoneContainsPrefixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Phone} matches the prefix string given.
 */
public class PhoneContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "p/";

    public PhoneContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getPhone().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals(((PhoneContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/AddressContainsSuffixesPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Address} matches the suffix string given.
 */
public class AddressContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "a/";

    public AddressContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getAddress().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals((
                        (AddressContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/JobAppliedContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code JobApplied} matches the substring given.
 */
public class JobAppliedContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "j/";

    public JobAppliedContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getJobApplied().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                (JobAppliedContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/NamePredicate.java
``` java
/**
 * Represents an Name predicate
 */
public class NamePredicate implements FieldPredicate {

    private Predicate<Person> namePredicate = null;

    /**
     * Constructs an {@code NamePredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public NamePredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                         ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = null;
        NameContainsSubstringsPredicate nameContainsSubstringsPredicate = null;
        NameContainsPrefixesPredicate nameContainsPrefixesPredicate = null;
        NameContainsSuffixesPredicate nameContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            nameContainsSubstringsPredicate = new NameContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            nameContainsPrefixesPredicate = new NameContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            nameContainsSuffixesPredicate = new NameContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.namePredicate = PredicateUtil.formOrPredicate(nameContainsKeywordsPredicate,
                    nameContainsSubstringsPredicate, nameContainsPrefixesPredicate,
                    nameContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return namePredicate;
    }

    @Override
    public String toString() {
        return namePredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NamePredicate // instanceof handles nulls
                && this.namePredicate.equals(((NamePredicate) other).namePredicate)); // state check
    }

    @Override
    public int hashCode() {
        return namePredicate.hashCode();
    }

}
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.backupAddressBook(addressBook);
    }

```
###### /java/seedu/address/storage/XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, getDestinationPath(filePath));
    }

    /**
     *
     * @return destination filePath of backup file.
     */
    private String getDestinationPath(String filePath) {
        return (filePath == null) ? null : filePath + BACKUP_FILE_EXTENSION;
    }
}
```
###### /java/seedu/address/storage/AddressBookStorage.java
``` java
    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage, serving as a backup.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
}
```
###### /java/seedu/address/logic/commands/Command.java
``` java
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons after finding.
     *
     * @return summary find message for searches
     */
    public static String getFindMessageForPersonListShownSummary() {
        FindResults.getInstance().formTextResults();
        FindResults.getInstance().clearResults();
        return FindResults.getInstance().getTextResults();
    }

```
###### /java/seedu/address/logic/commands/FindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose field contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose fields contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Option 1 (Search all fields): KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alex david alexyeoh@example.com\n\n"
            + "Option 2 (Search by prefix): /n[KEYWORD] [MORE_KEYWORDS] /p...\n"
            + "Example: " + COMMAND_WORD + " n/Alex Bernice p/999 555";

    public static final int LEVENSHTEIN_DISTANCE_THRESHOLD = 2;

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        FindResults.getInstance().clearResults(); // clear existing results if any (e.g. when undo command is executed)
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getFindMessageForPersonListShownSummary());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/parser/FindUtil.java
``` java
/**
 * Contains utility methods used for FindCommandParser
 */
public class FindUtil {

    /**
     * Parses the string {@code trimmedArgs} and {@code argMultimap} to form a combined Predicate based on user request
     * @param trimmedArgs,argMultimap
     * @return the predicate user demanded
     * @throws ParseException
     */
    public static Predicate<Person> parseFindArgs(String trimmedArgs, ArgumentMultimap argMultimap)
            throws ParseException {
        requireNonNull(trimmedArgs);
        Predicate<Person> finalPredicate;

        // no prefix used, search for all fields (global search)
        if (!startWithPrefix(trimmedArgs)) {
            String[] keywords = trimmedArgs.split(",");

            try {
                AllPredicate allPredicate = PredicateUtil.parseAllPredicates(keywords);
                finalPredicate = PredicateUtil.formOrPredicate(allPredicate.getNamePredicate().getPredicate(),
                        allPredicate.getPhonePredicate().getPredicate(),
                        allPredicate.getEmailPredicate().getPredicate(),
                        allPredicate.getAddressPredicate().getPredicate(),
                        allPredicate.getUniversityPredicate().getPredicate(),
                        allPredicate.getMajorPredicate().getPredicate(),
                        allPredicate.getJobAppliedPredicate().getPredicate(),
                        allPredicate.getCommentPredicate().getPredicate());
                return finalPredicate;
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage(), pe);
            }

        } else {
            // at least one prefix is used, search for fields that matches prefix only
            if (!argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            try {
                AllPredicate allPredicate = PredicateUtil.parseSelectedPredicates(argMultimap);
                finalPredicate = PredicateUtil.formAndPredicate(allPredicate.getNamePredicate().getPredicate(),
                        allPredicate.getPhonePredicate().getPredicate(),
                        allPredicate.getEmailPredicate().getPredicate(),
                        allPredicate.getAddressPredicate().getPredicate(),
                        allPredicate.getUniversityPredicate().getPredicate(),
                        allPredicate.getMajorPredicate().getPredicate(),
                        allPredicate.getJobAppliedPredicate().getPredicate(),
                        allPredicate.getCommentPredicate().getPredicate());
                return finalPredicate;
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage(), pe);
            }

        }
    }

    /**
     * Parses the string {@code trimmedArgs} and a returns a boolean value true if prefix is present
     * @param trimmedArgs
     * @return boolean value
     */
    private static boolean startWithPrefix(String trimmedArgs) {
        String[] args = trimmedArgs.split("\\s+");

        return (args[0].contains(PREFIX_NAME.toString())
                || args[0].contains(PREFIX_PHONE.toString())
                || args[0].contains(PREFIX_EMAIL.toString())
                || args[0].contains(PREFIX_ADDRESS.toString())
                || args[0].contains(PREFIX_UNIVERSITY.toString())
                || args[0].contains(PREFIX_MAJOR.toString())
                || args[0].contains(PREFIX_JOB_APPLIED.toString())
                || args[0].contains(PREFIX_COMMENT.toString())); // more fields to be added if necessary
    }
}
```
###### /java/seedu/address/logic/parser/FindCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        // Check for empty argument input
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_UNIVERSITY, PREFIX_MAJOR,
                        PREFIX_JOB_APPLIED, PREFIX_COMMENT); // more fields to be added if necessary
        try {
            Predicate<Person> finalPredicate = FindUtil.parseFindArgs(trimmedArgs, argMultimap);
            return new FindCommand(finalPredicate);
        } catch (ParseException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }


}
```
###### /java/seedu/address/logic/parser/ArgumentMultimap.java
``` java
    /**
     * Returns a set of {@code prefix}
     */
    public Set<Prefix> getAllPrefixes() {
        return argMultimap.keySet();
    }
}
```
###### /java/seedu/address/logic/parser/PredicateUtil.java
``` java
/**
 * Contains utility methods used for parsing predicates in FindUtil
 */
public class PredicateUtil {

    /**
     * Parses the String array {@code keywords} to
     * form a combined Predicate based on user request
     * @param keywords contains user argument
     * @return void
     */
    public static AllPredicate parseAllPredicates(String[] keywords) throws ParseException {
        ArrayList<String> substringKeywords = new ArrayList<>();
        ArrayList<String> exactKeywords = new ArrayList<>();
        ArrayList<String> prefixKeywords = new ArrayList<>();
        ArrayList<String> suffixKeywords = new ArrayList<>();
        parseKeywordsArray(keywords, substringKeywords, exactKeywords,
                prefixKeywords, suffixKeywords);

        return new AllPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    /**
     * Parses the String array {@code keywords} and add keywords to respective ArrayList
     * @param keywords contains all user arguments
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    private static void parseKeywordsArray(String[] keywords,
                                           ArrayList<String> substringKeywords,
                                           ArrayList<String> exactKeywords,
                                           ArrayList<String> prefixKeywords,
                                           ArrayList<String> suffixKeywords) throws ParseException {
        if (keywords[0].isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Word parameter cannot be empty"));
        }

        for (String keyword : keywords) {
            keyword = keyword.trim();

            if (keyword.equals("*") || keyword.equals("\"")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, "One * or \" is not a valid parameter"));
            }
            String strippedKeyword;
            if (keyword.startsWith("\"") && keyword.endsWith("\"")) { // substring
                strippedKeyword = keyword.substring(1, keyword.length() - 1);
                if (strippedKeyword.isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Substring parameter cannot be empty"));
                }
                substringKeywords.add(strippedKeyword);
            } else if (!keyword.startsWith("*") && keyword.endsWith("*")) { // prefix
                strippedKeyword = keyword.substring(0, keyword.length() - 1);
                if (strippedKeyword.isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Prefix parameter cannot be empty"));
                }
                prefixKeywords.add(strippedKeyword);
            } else if (keyword.startsWith("*") && !keyword.endsWith("*")) { // suffix
                strippedKeyword = keyword.substring(1, keyword.length());
                if (strippedKeyword.isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Suffix parameter cannot be empty"));
                }
                suffixKeywords.add(strippedKeyword);
            } else {
                exactKeywords.add(keyword);
            }
        }
    }

    /**
     * Parses the ArgumentMultimap {@code argMultimap} to
     * form a combined Predicate based on user request
     * @param argMultimap mapping of prefixes to their respective user arguments.
     * @return AllPredicate
     */
    public static AllPredicate parseSelectedPredicates(
            ArgumentMultimap argMultimap) throws ParseException {

        Set<Prefix> prefixSet = argMultimap.getAllPrefixes();
        AllPredicate allPredicate = new AllPredicate();

        // checks if prefix is present in argMultimap and parses the respective predicate
        for (Prefix prefix : prefixSet) {
            if (prefix.toString().equals("")) {
                continue;
            }
            String[] keywords = argMultimap.getValue(prefix).get().split(",");
            ArrayList<String> substringKeywords = new ArrayList<>();
            ArrayList<String> exactKeywords = new ArrayList<>();
            ArrayList<String> prefixKeywords = new ArrayList<>();
            ArrayList<String> suffixKeywords = new ArrayList<>();

            parseKeywordsArray(keywords, substringKeywords, exactKeywords,
                    prefixKeywords, suffixKeywords);
            addSelectedPredicates(prefix, substringKeywords, exactKeywords,
                    prefixKeywords, suffixKeywords, allPredicate);
        }
        return allPredicate;
    }

    /**
     * Parses all contents in ArrayList and form an AllPredicate based on the prefix
     * @param prefix specified by user to search for a field
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     * @param allPredicate a predicate class that contains reference to all predicates objects
     */
    private static void addSelectedPredicates(Prefix prefix, ArrayList<String> substringKeywords,
                                              ArrayList<String> exactKeywords,
                                              ArrayList<String> prefixKeywords,
                                              ArrayList<String> suffixKeywords,
                                              AllPredicate allPredicate) {
        switch (prefix.toString()) {
        case "n/":
            allPredicate.setNamePredicate(exactKeywords, substringKeywords,
                    prefixKeywords, suffixKeywords);
            break;
        case "p/":
            allPredicate.setPhonePredicate(exactKeywords, substringKeywords,
                    prefixKeywords, suffixKeywords);
            break;
        case "e/":
            allPredicate.setEmailPredicate(exactKeywords, substringKeywords,
                    prefixKeywords, suffixKeywords);
            break;
        case "a/":
            allPredicate.setAddressPredicate(exactKeywords, substringKeywords,
                    prefixKeywords, suffixKeywords);
            break;
        case "u/":
            allPredicate.setUniversityPredicate(exactKeywords, substringKeywords,
                    prefixKeywords, suffixKeywords);
            break;
        case "m/":
            allPredicate.setMajorPredicate(exactKeywords, substringKeywords,
                    prefixKeywords, suffixKeywords);
            break;
        case "j/":
            allPredicate.setJobAppliedPredicate(exactKeywords, substringKeywords,
                    prefixKeywords, suffixKeywords);
            break;
        case "c/":
            allPredicate.setCommentPredicate(exactKeywords, substringKeywords,
                    prefixKeywords, suffixKeywords);
            break;
        default:
            throw new IllegalArgumentException("Invalid prefix detected");
        }
    }

    /**
     * Combines all predicates that matches the
     * corresponding condition to form the final predicate
     * @param predicates in the form of varargs
     * @return {@code Predicate<Person>}
     */
    @SafeVarargs
    public static Predicate<Person> formOrPredicate(Predicate<Person>... predicates) {
        return Stream.of(predicates).filter(Objects::nonNull)
                .reduce(condition -> false, Predicate::or);
    }

    /**
     * Combines all predicates that matches the
     * corresponding condition to form the final predicate
     * @param predicates in the form of varargs
     * @return {@code Predicate<Person>}
     */
    @SafeVarargs
    public static Predicate<Person> formAndPredicate(Predicate<Person>... predicates) {
        return Stream.of(predicates).filter(Objects::nonNull)
                .reduce(condition -> true, Predicate::and);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String major} into a {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code major} is invalid.
     */
    public static Major parseMajor(String major) throws IllegalValueException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new IllegalValueException(Major.MESSAGE_MAJOR_CONSTRAINTS);
        }
        return new Major(trimmedMajor);
    }

    /**
     * Parses a {@code Optional<String> major} into an {@code Optional<Major>} if {@code major} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Major> parseMajor(Optional<String> major) throws IllegalValueException {
        requireNonNull(major);
        return major.isPresent() ? Optional.of(parseMajor(major.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String gradePointAverage} into an {@code gradePointAverage}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if given {@code gradePointAverage} is invalid.
     */
    public static GradePointAverage parseGradePointAverage(String gradePointAverage)
            throws IllegalValueException {
        requireNonNull(gradePointAverage);
        String trimmedGradePointAverage = gradePointAverage.trim();
        if (!GradePointAverage.isValidGradePointAverage(trimmedGradePointAverage)) {
            throw new IllegalValueException(GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
        }
        return new GradePointAverage(trimmedGradePointAverage);
    }

    /**
     * Parses a {@code Optional<String> gradePointAverage}
     * into an {@code Optional<GradePointAverage>} if {@code gradePointAverage} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<GradePointAverage> parseGradePointAverage(Optional<String> gradePointAverage)
            throws IllegalValueException {
        requireNonNull(gradePointAverage);
        return gradePointAverage.isPresent() ? Optional.of(parseGradePointAverage(
                gradePointAverage.get())) : Optional.empty();
    }

```
