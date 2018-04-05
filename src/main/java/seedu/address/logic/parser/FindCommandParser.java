package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
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
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;

//@@Author wynonaK
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Pattern FIND_COMMAND_FORMAT_OWNER = Pattern.compile("-(o)+(?<personInfo>.*)");
    private static final Pattern FIND_COMMAND_FORMAT_PET_PATIENT = Pattern.compile("-(p)+(?<petPatientInfo>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution. Currently acceptable formats are:
     * find -o n/ nr/ t/ (inclusive of individual and all combinations)
     * find -p n/ s/ b/ c/ bt/ t/ (inclusive of individual and all combinations)
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
        ArgumentMultimap argMultimapOwner =
                ArgumentTokenizer.tokenize(personInfo, PREFIX_NAME, PREFIX_NRIC, PREFIX_TAG);
        if ((!arePrefixesPresent(argMultimapOwner, PREFIX_NAME)
                && !arePrefixesPresent(argMultimapOwner, PREFIX_NRIC)
                && !arePrefixesPresent(argMultimapOwner, PREFIX_TAG))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Unknown prefix parameters!"));
        }

        Predicate<Person> finalPredicate = null;

        if ((arePrefixesPresent(argMultimapOwner, PREFIX_NAME))) {
            String[] nameKeywords = getNameKeyword(argMultimapOwner);
            Predicate<Person> namePredicate =  person -> Arrays.stream(nameKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
            finalPredicate = namePredicate;
        }

        if ((arePrefixesPresent(argMultimapOwner, PREFIX_NRIC))) {
            String[] nricKeywords = getNricKeyword(argMultimapOwner);
            Predicate<Person>  nricPredicate = person -> Arrays.stream(nricKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNric().toString(), keyword));
            if (finalPredicate == null) {
                finalPredicate = nricPredicate;
            } else {
                finalPredicate = finalPredicate.and(nricPredicate);
            }
        }

        if ((arePrefixesPresent(argMultimapOwner, PREFIX_TAG))) {
            String[] tagKeywords = getTagKeyword(argMultimapOwner);
            Predicate<Person> tagPredicate = person -> Arrays.stream(tagKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTagString(), keyword));
            if (finalPredicate == null) {
                finalPredicate = tagPredicate;
            } else {
                finalPredicate = finalPredicate.and(tagPredicate);
            }
        }

        return new FindCommand(finalPredicate);
    }

    /**
     * Gets the nric keywords from {@code argMultimapOwner}.
     * @throws ParseException if there is an illegal value found.
     */
    private String[] getTagKeyword(ArgumentMultimap argMultimapOwner) throws ParseException {
        try {
            String tagWithoutPrefix = argMultimapOwner.getAllValues(PREFIX_TAG).get(0);
            String[] tagKeywords = tagWithoutPrefix.trim().split("\\s+");
            for (String tagKeyword : tagKeywords) {
                Tag tag = ParserUtil.parseTag(tagKeyword);
            }
            return tagKeywords;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
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
     * Gets the name keywords from {@code argMultimapOwner}.
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
     * Gets the name keywords from {@code argMultimapPetPatient}.
     * @throws ParseException if there is an illegal value found.
     */
    private String[] getPetPatientNameKeyword(ArgumentMultimap argMultimapPetPatient) throws ParseException {
        try {
            String nameWithoutPrefix = argMultimapPetPatient.getAllValues(PREFIX_NAME).get(0);
            String[] nameKeywords = nameWithoutPrefix.trim().split("\\s+");
            for (String nameKeyword : nameKeywords) {
                PetPatientName name = ParserUtil.parsePetPatientName(nameKeyword);
            }
            return nameKeywords;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Gets the keywords from {@code argMultimapPetPatient} with {@code prefix}.
     * @throws ParseException if there is an illegal value found.
     */
    private String[] getStringKeyword(ArgumentMultimap argMultimapPetPatient, Prefix prefix) throws ParseException {
        String stringWithoutPrefix = argMultimapPetPatient.getAllValues(prefix).get(0);
        if (stringWithoutPrefix.equals("")){
            throw new ParseException("Invalid keyword parameters!");
        }
        return stringWithoutPrefix.trim().split("\\s+");
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
    private FindCommand parsePetPatient(String petPatientInfo) throws ParseException, IllegalArgumentException{
        ArgumentMultimap argMultimapPetPatient =
                ArgumentTokenizer.tokenize(petPatientInfo, PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED,
                        PREFIX_COLOUR, PREFIX_BLOODTYPE, PREFIX_TAG);
        if ((!arePrefixesPresent(argMultimapPetPatient, PREFIX_NAME)
                && !arePrefixesPresent(argMultimapPetPatient, PREFIX_SPECIES)
                && !arePrefixesPresent(argMultimapPetPatient, PREFIX_BREED)
                && !arePrefixesPresent(argMultimapPetPatient, PREFIX_COLOUR)
                && !arePrefixesPresent(argMultimapPetPatient, PREFIX_BLOODTYPE)
                && !arePrefixesPresent(argMultimapPetPatient, PREFIX_TAG))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Unknown prefix parameters!"));
        }

        Predicate<PetPatient> finalPredicate = null;

        if ((arePrefixesPresent(argMultimapPetPatient, PREFIX_NAME))) {
            String[] nameKeywords = getPetPatientNameKeyword(argMultimapPetPatient);
            Predicate<PetPatient> namePredicate =  petPatient -> Arrays.stream(nameKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getName().fullName, keyword));
            finalPredicate = namePredicate;
        }

        if ((arePrefixesPresent(argMultimapPetPatient, PREFIX_SPECIES))) {
            String[] stringKeywords = getStringKeyword(argMultimapPetPatient, PREFIX_SPECIES);
            Predicate<PetPatient> stringPredicate =  petPatient -> Arrays.stream(stringKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getSpecies(), keyword));
            if (finalPredicate == null) {
                finalPredicate = stringPredicate;
            } else {
                finalPredicate = finalPredicate.and(stringPredicate);
            }
        }

        if ((arePrefixesPresent(argMultimapPetPatient, PREFIX_BREED))) {
            String[] stringKeywords = getStringKeyword(argMultimapPetPatient, PREFIX_BREED);
            Predicate<PetPatient> stringPredicate =  petPatient -> Arrays.stream(stringKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getBreed(), keyword));
            if (finalPredicate == null) {
                finalPredicate = stringPredicate;
            } else {
                finalPredicate = finalPredicate.and(stringPredicate);
            }
        }

        if ((arePrefixesPresent(argMultimapPetPatient, PREFIX_COLOUR))) {
            String[] stringKeywords = getStringKeyword(argMultimapPetPatient, PREFIX_COLOUR);
            Predicate<PetPatient> stringPredicate =  petPatient -> Arrays.stream(stringKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getColour(), keyword));
            if (finalPredicate == null) {
                finalPredicate = stringPredicate;
            } else {
                finalPredicate = finalPredicate.and(stringPredicate);
            }
        }

        if ((arePrefixesPresent(argMultimapPetPatient, PREFIX_BLOODTYPE))) {
            String[] stringKeywords = getStringKeyword(argMultimapPetPatient, PREFIX_BLOODTYPE);
            Predicate<PetPatient> stringPredicate =  petPatient -> Arrays.stream(stringKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getBloodType(), keyword));
            if (finalPredicate == null) {
                finalPredicate = stringPredicate;
            } else {
                finalPredicate = finalPredicate.and(stringPredicate);
            }
        }

        if ((arePrefixesPresent(argMultimapPetPatient, PREFIX_TAG))) {
            String[] tagKeywords = getTagKeyword(argMultimapPetPatient);
            Predicate<PetPatient> tagPredicate = petPatient -> Arrays.stream(tagKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getTagString(), keyword));
            if (finalPredicate == null) {
                finalPredicate = tagPredicate;
            } else {
                finalPredicate = finalPredicate.and(tagPredicate);
            }
        }

        return new FindCommand(finalPredicate, 2); //because of JVM problem
    }
}
