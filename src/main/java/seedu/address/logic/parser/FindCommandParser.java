package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameNricContainsKeywordsPredicate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Pattern FIND_COMMAND_FORMAT_OWNER= Pattern.compile("-(o)+(?<personInfo>.*)");
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

        // Eind-owner related
        if (matcherForPerson.matches()) {
            String personInfo = matcherForPerson.group("personInfo");
            return parsePerson(personInfo);
        }
        // Find-petpatient related
        else if (matcherForPetPatient.matches()) {
            String petPatientInfo = matcherForPetPatient.group("petPatientInfo");
            return parsePetPatient(petPatientInfo);
        }
        else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    private FindCommand parsePerson(String personInfo) throws ParseException {
        ArgumentMultimap argMultimapOwner = ArgumentTokenizer.tokenize(personInfo, PREFIX_NAME, PREFIX_NRIC);
        if ((!arePrefixesPresent(argMultimapOwner, PREFIX_NAME)
                && !arePrefixesPresent(argMultimapOwner, PREFIX_NRIC))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Unknown prefix parameters!"));
        }

        if ((arePrefixesPresent(argMultimapOwner, PREFIX_NAME, PREFIX_NRIC))) {
            return new FindCommand(new NameNricContainsKeywordsPredicate(
                    Arrays.asList(getNameKeyword(argMultimapOwner)), Arrays.asList(getNricKeyword(argMultimapOwner))));
        } else if (arePrefixesPresent(argMultimapOwner, PREFIX_NAME)
                && !arePrefixesPresent(argMultimapOwner, PREFIX_NRIC)) {
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(getNameKeyword(argMultimapOwner))));
        } else if (!arePrefixesPresent(argMultimapOwner, PREFIX_NAME)
                && arePrefixesPresent(argMultimapOwner, PREFIX_NRIC)) {
            return new FindCommand(new NricContainsKeywordsPredicate(Arrays.asList(getNricKeyword(argMultimapOwner))));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Unknown prefix parameters!"));
        }
    }

    private String[] getNricKeyword(ArgumentMultimap argMultimapOwner) throws ParseException {
        try {
            String nricWithoutPrefix = argMultimapOwner.getAllValues(PREFIX_NRIC).get(0);
            String[] nricKeywords = nricWithoutPrefix.trim().split("\\s+");
            for (String nricKeyword : nricKeywords){
                Nric nric = ParserUtil.parseNric(nricKeyword);
            }
            return nricKeywords;
        } catch (IllegalValueException ive){
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private String[] getNameKeyword(ArgumentMultimap argMultimapOwner) throws ParseException {
        try {
            String nameWithoutPrefix = argMultimapOwner.getAllValues(PREFIX_NAME).get(0);
            String[] nameKeywords = nameWithoutPrefix.trim().split("\\s+");
            for (String nameKeyword : nameKeywords){
                Name name = ParserUtil.parseName(nameKeyword);
            }
            return nameKeywords;
        } catch (IllegalValueException ive){
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

    private FindCommand parsePetPatient(String petPatientInfo) {

        String[] nameKeywords = petPatientInfo.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
