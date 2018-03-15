package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPetPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPetPatientCommand object
 */
public class AddPetPatientCommandParser implements Parser<AddPetPatientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPetPatientCommand
     * and returns an AddPetPatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPetPatientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        PREFIX_PET_PATIENT_NAME,
                        PREFIX_PET_PATIENT_SPECIES,
                        PREFIX_PET_PATIENT_BREED,
                        PREFIX_PET_PATIENT_COLOUR,
                        PREFIX_PET_PATIENT_BLOODTYPE,
                        PREFIX_TAG);

        if (!arePrefixesPresent(
                argMultimap,
                PREFIX_PET_PATIENT_NAME,
                PREFIX_PET_PATIENT_SPECIES,
                PREFIX_PET_PATIENT_BREED,
                PREFIX_PET_PATIENT_COLOUR,
                PREFIX_PET_PATIENT_BLOODTYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPetPatientCommand.MESSAGE_USAGE));
        }

        try {
            PetPatientName name = ParserUtil.parsePetPatientName(argMultimap.getValue(PREFIX_PET_PATIENT_NAME)).get();
            String species = ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_PET_PATIENT_SPECIES)).get();
            String breed = ParserUtil.parseBreed(argMultimap.getValue(PREFIX_PET_PATIENT_BREED)).get();
            String color = ParserUtil.parseColour(argMultimap.getValue(PREFIX_PET_PATIENT_COLOUR)).get();
            String bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_PET_PATIENT_BLOODTYPE)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            PetPatient petPatient = new PetPatient(name, species, breed, color, bloodType, tagList);

            return new AddPetPatientCommand(petPatient);
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
}
