package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.ExpectedGraduationYearContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_EXPECTED_GRADUATION_YEAR); // more fields to be added if necessary

        List<Predicate<Person>> predicateList = new ArrayList<>();
        Predicate<Person> finalPredicate;
        boolean isGlobalSearch = false;

        try {
            // no prefix used, search for all fields (global search)
            if (!startWithPrefix(trimmedArgs)) {
                isGlobalSearch = true;
                String[] keywords = trimmedArgs.split("\\s+");

                predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
                predicateList.add(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
                predicateList.add(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
                predicateList.add(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
                predicateList.add(new ExpectedGraduationYearContainsKeywordsPredicate(Arrays.asList(keywords)));

                finalPredicate = combinePredicates(isGlobalSearch,
                        predicateList.toArray(new Predicate[predicateList.size()]));
                return new FindCommand(finalPredicate);
            }

            // at least one prefix is used, search for fields that matches prefix only
            if (!argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

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

            finalPredicate = combinePredicates(isGlobalSearch,
                                               predicateList.toArray(new Predicate[predicateList.size()]));
            return new FindCommand(finalPredicate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns whether the user starts with a prefix in the argument as a boolean result.
     */
    private boolean startWithPrefix(String trimmedArgs) {
        String[] args = trimmedArgs.split("\\s+");

        return (args[0].contains(PREFIX_NAME.toString())
                || args[0].contains(PREFIX_PHONE.toString())
                || args[0].contains(PREFIX_EMAIL.toString())
                || args[0].contains(PREFIX_ADDRESS.toString())
                || args[0].contains(PREFIX_EXPECTED_GRADUATION_YEAR.toString()));
    }

    /**
     * Combines all predicates in {@code predicateList} that matches the
     * corresponding condition to form {@code finalPredicate}
     * @param predicates in the form of varargs
     * @return {@code finalPredicate} of type {@code Predicate<Person>}
     */
    private Predicate<Person> combinePredicates(boolean isGlobalSearch, Predicate<Person>... predicates) {
        Predicate<Person> finalPredicate;

        if (isGlobalSearch) {
            finalPredicate = Stream.of(predicates).reduce(condition -> false, Predicate::or);
        } else {
            finalPredicate = Stream.of(predicates).reduce(condition -> true, Predicate::and);
        }

        return finalPredicate;
    }
}
