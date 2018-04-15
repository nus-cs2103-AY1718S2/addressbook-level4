package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_APPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIVERSITY;

import java.util.function.Predicate;

import org.apache.commons.lang.NullArgumentException;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.AllPredicate;
import seedu.address.model.person.Person;

//@@author tanhengyeow
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
    public Predicate<Person> parseFindArgs(String trimmedArgs, ArgumentMultimap argMultimap)
            throws ParseException {
        requireNonNull(trimmedArgs);
        assert trimmedArgs != null;
        Predicate<Person> finalPredicate;

        // no prefix used, search for all fields (global search)
        if (!startWithPrefix(trimmedArgs)) {
            String[] keywords = trimmedArgs.split(",");

            try {
                AllPredicate allPredicate = new PredicateUtil().parseAllPredicates(keywords);
                finalPredicate = new PredicateUtil().formOrPredicate(allPredicate.getNamePredicate().getPredicate(),
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
                AllPredicate allPredicate = new PredicateUtil().parseSelectedPredicates(argMultimap);
                finalPredicate = new PredicateUtil().formAndPredicate(allPredicate.getNamePredicate().getPredicate(),
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
    private boolean startWithPrefix(String trimmedArgs) throws NullArgumentException {
        assert trimmedArgs != null;

        String[] args = null;

        try {
            args = trimmedArgs.split("\\s+");

        } catch (NullArgumentException ne) {
            ne.printStackTrace();
        }
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
