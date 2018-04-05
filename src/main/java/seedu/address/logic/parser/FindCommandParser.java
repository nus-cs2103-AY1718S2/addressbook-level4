package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameNricContainsKeywordsPredicate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Pattern FIND_COMMAND_FORMAT_OWNER = Pattern.compile("-(o)+(?<personInfo>.*)");
    private static final Pattern FIND_COMMAND_FORMAT_PET_PATIENT = Pattern.compile("-(p)+(?<petPatientInfo>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution. Currently acceptable formats are:
     * find -o n/ nr/ (inclusive of both and individually)
     * find -p s/ b/ c/ bt/ (inclusive of all 4, a mix of multiple, and individually)
     * @throws ParseException if the user input does not conform the expected format
     */

    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        final Matcher matcherForPerson = FIND_COMMAND_FORMAT_OWNER.matcher(trimmedArgs);
        final Matcher matcherForPetPatient = FIND_COMMAND_FORMAT_PET_PATIENT.matcher(trimmedArgs);

        // Eind-owner related, else find-pet related, else error
        if (matcherForPerson.matches()) {
            String personInfo = matcherForPerson.group("personInfo");
            return parsePerson(personInfo);
        } else if (matcherForPetPatient.matches()) {
            String petPatientInfo = matcherForPetPatient.group("petPatientInfo");
            return parsePetPatient(petPatientInfo);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses {@code personInfo} to find out what the user is parsing to find.
     * @throws ParseException if the {@code personInfo} cannot be identified to a known prefix.
     */
    private FindCommand parsePerson(String personInfo) throws ParseException {
        ArgumentMultimap argMultimapOwner = ArgumentTokenizer.tokenize(personInfo, PREFIX_NAME, PREFIX_NRIC);
        if ((!arePrefixesPresent(argMultimapOwner, PREFIX_NAME)
                && !arePrefixesPresent(argMultimapOwner, PREFIX_NRIC)
                && !arePrefixesPresent(argMultimapOwner, PREFIX_TAG))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Unknown prefix parameters!"));
        }

        Predicate<Person> finalPredicate = null;


        if ((arePrefixesPresent(argMultimapOwner, PREFIX_NAME))) {
            Predicate<Person> namePredicate =  person -> Arrays.asList(getNameKeyword(argMultimapOwner)).stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        }

        if ((arePrefixesPresent(argMultimapOwner, PREFIX_NRIC))) {
            Predicate<Person> nricPredicate = person -> Arrays.asList(getNricKeyword(argMultimapOwner)).stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNric().toString(), keyword));
        }

        if ((arePrefixesPresent(argMultimapOwner, PREFIX_TAG))) {
            Predicate<Person> tagPredicate = person -> Arrays.asList(getTagKeyword(argMultimapOwner)).stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTags().toString(), keyword));
        }

        return new FindCommand(finalPredicate);
    }

    /**
     * Gets the nric keywords from {@code argMultimapOwner}.
     * @throws ParseException if there is an illegal value found.
     */
    private String[] getNricKeyword(ArgumentMultimap argMultimapOwner) throws ParseException {
        try {
            String nricWithoutPrefix = argMultimapOwner.getAllValues(PREFIX_NRIC).get(0);
            String[] nricKeywords = nricWithoutPrefix.trim().split("\\s+");
            for (String nricKeyword : nricKeywords) {
                Nric nric = ParserUtil.parseNric(nricKeyword);
            }
            return nricKeywords;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Gets the nric keywords from {@code argMultimapOwner}.
     * @throws ParseException if there is an illegal value found.
     */
    private String[] getNameKeyword(ArgumentMultimap argMultimapOwner) throws ParseException {
        try {
            String nameWithoutPrefix = argMultimapOwner.getAllValues(PREFIX_NAME).get(0);
            String[] nameKeywords = nameWithoutPrefix.trim().split("\\s+");
            for (String nameKeyword : nameKeywords) {
                Name name = ParserUtil.parseName(nameKeyword);
            }
            return nameKeywords;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code petPatientInfo} to find out what the user is parsing to find.
     */
    private FindCommand parsePetPatient(String petPatientInfo) {

        String[] nameKeywords = petPatientInfo.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
