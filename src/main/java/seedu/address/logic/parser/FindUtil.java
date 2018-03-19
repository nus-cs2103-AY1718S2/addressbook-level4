package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.ExpectedGraduationYearContainsKeywordsPredicate;
import seedu.address.model.person.MajorContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

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
    @SuppressWarnings("unchecked")
    public static Predicate<Person> parseFindArgs(String trimmedArgs, ArgumentMultimap argMultimap)
            throws ParseException {
        requireNonNull(trimmedArgs);
        List<Predicate<Person>> predicateList = new ArrayList<>();
        boolean isGlobalSearch = false;
        Predicate<Person> finalPredicate;

        // no prefix used, search for all fields (global search)
        if (!startWithPrefix(trimmedArgs)) {
            isGlobalSearch = true;
            String[] keywords = trimmedArgs.split("\\s+");

            predicateList = parseAllPredicates(keywords, predicateList);
            finalPredicate = combinePredicates(isGlobalSearch,
                    predicateList.toArray(new Predicate[predicateList.size()]));

            return finalPredicate;
        } else {
            // at least one prefix is used, search for fields that matches prefix only
            if (!argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            predicateList = parseSelectedPredicates(argMultimap, predicateList);
            finalPredicate = combinePredicates(isGlobalSearch,
                    predicateList.toArray(new Predicate[predicateList.size()]));

            return finalPredicate;
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
                || args[0].contains(PREFIX_EXPECTED_GRADUATION_YEAR.toString())
                || args[0].contains(PREFIX_MAJOR.toString()));
    }

    /**
     * Parses the String array {@code trimmedArgs} and {@code predicateList} to
     * form a combined Predicate based on user request
     * @param keywords,predicateList
     * @return a list of Predicate
     */
    private static List<Predicate<Person>> parseAllPredicates(String[] keywords,
                                                              List<Predicate<Person>> predicateList) {
        predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        predicateList.add(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        predicateList.add(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
        predicateList.add(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
        predicateList.add(new ExpectedGraduationYearContainsKeywordsPredicate(Arrays.asList(keywords)));
        predicateList.add(new MajorContainsKeywordsPredicate(Arrays.asList(keywords)));

        return predicateList;
    }

    /**
     * Parses the ArgumentMultimap {@code argMultimap} and {@code predicateList} to
     * form a combined Predicate based on user request
     * @param argMultimap,predicateList
     * @return a list of Predicate
     */
    private static List<Predicate<Person>> parseSelectedPredicates(
            ArgumentMultimap argMultimap, List<Predicate<Person>> predicateList) {

        // checks if prefix is present in argMultimap and adds the corresponding predicate to predicateList
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] phoneKeywords = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");
            predicateList.add(new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeywords)));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            predicateList.add(new EmailContainsKeywordsPredicate(Arrays.asList(emailKeywords)));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String[] addressKeywords = argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+");
            predicateList.add(new AddressContainsKeywordsPredicate(Arrays.asList(addressKeywords)));
        }
        if (argMultimap.getValue(PREFIX_EXPECTED_GRADUATION_YEAR).isPresent()) {
            String[] expectedGraduationYearKeywords =
                    argMultimap.getValue(PREFIX_EXPECTED_GRADUATION_YEAR).get().split("\\s+");
            predicateList.add(new ExpectedGraduationYearContainsKeywordsPredicate(
                    Arrays.asList(expectedGraduationYearKeywords)));
        }
        if (argMultimap.getValue(PREFIX_MAJOR).isPresent()) {
            String[] majorKeywords =
                    argMultimap.getValue(PREFIX_MAJOR).get().split("\\s+");
            predicateList.add(new MajorContainsKeywordsPredicate(
                    Arrays.asList(majorKeywords)));
        }

        return predicateList;
    }

    /**
     * Combines all predicates in {@code predicateList} that matches the
     * corresponding condition to form {@code finalPredicate}
     * @param predicates in the form of varargs
     * @return {@code finalPredicate} of type {@code Predicate<Person>}
     */
    @SuppressWarnings("unchecked")
    private static Predicate<Person> combinePredicates(
            boolean isGlobalSearch, Predicate<Person>... predicates) {
        Predicate<Person> finalPredicate;

        if (isGlobalSearch) {
            finalPredicate = Stream.of(predicates).reduce(condition -> false, Predicate::or);
        } else {
            finalPredicate = Stream.of(predicates).reduce(condition -> true, Predicate::and);
        }

        return finalPredicate;
    }
}
