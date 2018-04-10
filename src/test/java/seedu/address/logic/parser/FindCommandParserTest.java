package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.BloodType;
import seedu.address.model.petpatient.Breed;
import seedu.address.model.petpatient.Colour;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.Species;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
    
    //@@author wynonaK
    @Test
    public void parse_onlyOwnerOption_throwsParseException() {
        assertParseFailure(parser, " -o ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankNameOption_throwsParseException() {
        assertParseFailure(parser, " -o n/ ",
                String.format(Name.MESSAGE_NAME_CONSTRAINTS));
    }

    @Test
    public void parse_blankNricOption_throwsParseException() {
        assertParseFailure(parser, " -o nr/ ",
                String.format(Nric.MESSAGE_NRIC_CONSTRAINTS));
    }

    @Test
    public void parse_blankTagOption_throwsParseException() {
        assertParseFailure(parser, " -o t/ ",
                String.format(Tag.MESSAGE_TAG_CONSTRAINTS));
    }

    @Test
    public void parse_onlyPetOption_throwsParseException() {
        assertParseFailure(parser, " -p ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_blankPetNameOption_throwsParseException() {
        assertParseFailure(parser, " -p n/ ",
                String.format(PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS));
    }

    @Test
    public void parse_blankSpeciesOption_throwsParseException() {
        assertParseFailure(parser, " -p s/ ",
                String.format(Species.MESSAGE_PET_SPECIES_CONSTRAINTS));
    }

    @Test
    public void parse_blankBreedOption_throwsParseException() {
        assertParseFailure(parser, " -p b/ ",
                String.format(Breed.MESSAGE_PET_BREED_CONSTRAINTS));
    }

    @Test
    public void parse_blankColourOption_throwsParseException() {
        assertParseFailure(parser, " -p c/ ",
                String.format(Colour.MESSAGE_PET_COLOUR_CONSTRAINTS));
    }

    @Test
    public void parse_blankBloodTypeOption_throwsParseException() {
        assertParseFailure(parser, " -p bt/ ",
                String.format(BloodType.MESSAGE_PET_BLOODTYPE_CONSTRAINTS));
    }

    @Test
    public void parse_blankPetTagOption_throwsParseException() {
        assertParseFailure(parser, " -p t/ ",
                String.format(Tag.MESSAGE_TAG_CONSTRAINTS));
    }
}
