package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AllPredicate;
import seedu.address.model.person.Person;

//@@author tanhengyeow
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
