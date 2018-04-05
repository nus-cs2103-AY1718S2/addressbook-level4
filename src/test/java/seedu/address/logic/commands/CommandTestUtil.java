package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.petpatient.exceptions.PetDependencyNotEmptyException;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Person: Amy
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_NRIC_AMY = "G1078999P";
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;

    // Person: Bob
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_NRIC_BOB = "S7895666N";
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;

    // Person: Charlie
    public static final String VALID_NAME_CHARLIE = "Charlie Brown";
    public static final String VALID_PHONE_CHARLIE = "94127890";
    public static final String VALID_EMAIL_CHARLIE = "charliebrown@gmail.com";
    public static final String VALID_ADDRESS_CHARLIE = "Chocolate Factory Avenue";
    public static final String VALID_NRIC_CHARLIE = "S1078899P";
    public static final String NAME_DESC_CHARLIE = " " + PREFIX_NAME + VALID_NAME_CHARLIE;
    public static final String PHONE_DESC_CHARLIE = " " + PREFIX_PHONE + VALID_PHONE_CHARLIE;
    public static final String EMAIL_DESC_CHARLIE = " " + PREFIX_EMAIL + VALID_EMAIL_CHARLIE;
    public static final String ADDRESS_DESC_CHARLIE = " " + PREFIX_ADDRESS + VALID_ADDRESS_CHARLIE;
    public static final String NRIC_DESC_CHARLIE = " " + PREFIX_NRIC + VALID_NRIC_CHARLIE;

    // More Nrics
    public static final String VALID_NRIC_DION = "T1115666G";
    public static final String NRIC_DESC_DION = " " + PREFIX_NRIC + VALID_NRIC_DION;
    public static final String VALID_NRIC_ELIAS = "G3338999P";
    public static final String NRIC_DESC_ELIAS = " " + PREFIX_NRIC + VALID_NRIC_ELIAS;
    public static final String VALID_NRIC_FION = "S2225666N";
    public static final String NRIC_DESC_FION = " " + PREFIX_NRIC + VALID_NRIC_FION;
    public static final String VALID_NRIC_JOHNNY = "S9622444T";
    public static final String NRIC_DESC_JOHNNY = " " + PREFIX_NRIC + VALID_NRIC_JOHNNY;
    public static final String VALID_NRIC_TIMMY = "S9277432G";
    public static final String NRIC_DESC_TIMMY = " " + PREFIX_NRIC + VALID_NRIC_TIMMY;

    // Tags
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String VALID_TAG_UNUSED = "unused";
    public static final String VALID_TAG_FIV = "Fiv";
    public static final String TAG_DESC_FIV = " " + PREFIX_TAG + VALID_TAG_FIV;
    public static final String VALID_TAG_DEPRESSION = "Depression";
    public static final String TAG_DESC_DEPRESSION = " " + PREFIX_TAG + VALID_TAG_DEPRESSION;
    public static final String VALID_TAG_CHECKUP = "checkup";
    public static final String TAG_DESC_CHECKUP = " " + PREFIX_TAG + VALID_TAG_CHECKUP;
    public static final String VALID_TAG_VACCINATION = "vaccination";
    public static final String TAG_DESC_VACCINATION = " " + PREFIX_TAG + VALID_TAG_VACCINATION;

    // Remarks
    public static final String VALID_REMARK_ONE = "Persistent diarrhoea";
    public static final String REMARK_DESC_ONE = " " + PREFIX_REMARK + VALID_REMARK_ONE;
    public static final String VALID_REMARK_TWO = "old age";
    public static final String REMARK_DESC_TWO = " " + PREFIX_REMARK + VALID_REMARK_TWO;

    // Date Time for appointments
    public static final String VALID_DATE_ONE = "2018-06-17 11:30";
    public static final String DATE_DESC_ONE = " " + PREFIX_DATE + VALID_DATE_ONE;
    public static final String VALID_DATE_TWO = "2018-07-05 16:30";
    public static final String DATE_DESC_TWO = " " + PREFIX_DATE + VALID_DATE_TWO;


    // PetPatient: Joker
    public static final String VALID_NAME_JOKER = "Joker";
    public static final String VALID_SPECIES_JOKER = "cat";
    public static final String VALID_BREED_JOKER = "domestic shorthair";
    public static final String VALID_COLOUR_JOKER = "brown and white";
    public static final String VALID_BLOODTYPE_JOKER = "O";
    public static final String NAME_DESC_JOKER = " " + PREFIX_NAME + VALID_NAME_JOKER;
    public static final String SPECIES_DESC_JOKER = " " + PREFIX_SPECIES + VALID_SPECIES_JOKER;
    public static final String BREED_DESC_JOKER = " " + PREFIX_BREED + VALID_BREED_JOKER;
    public static final String COLOUR_DESC_JOKER = " " + PREFIX_COLOUR + VALID_COLOUR_JOKER;
    public static final String BLOODTYPE_DESC_JOKER = " " + PREFIX_BLOODTYPE + VALID_BLOODTYPE_JOKER;

    // PetPatient: Nero
    public static final String VALID_NAME_NERO = "Nero";
    public static final String VALID_SPECIES_NERO = "cat";
    public static final String VALID_BREED_NERO = "British Shorthair";
    public static final String VALID_COLOUR_NERO = "Blue";
    public static final String VALID_BLOODTYPE_NERO = "AB";
    public static final String NAME_DESC_NERO = " " + PREFIX_NAME + VALID_NAME_NERO;
    public static final String SPECIES_DESC_NERO = " " + PREFIX_SPECIES + VALID_SPECIES_NERO;
    public static final String BREED_DESC_NERO = " " + PREFIX_BREED + VALID_BREED_NERO;
    public static final String COLOUR_DESC_NERO = " " + PREFIX_COLOUR + VALID_COLOUR_NERO;
    public static final String BLOODTYPE_DESC_NERO = " " + PREFIX_BLOODTYPE + VALID_BLOODTYPE_NERO;

    // options
    public static final String OPTION_OWNER = " -o";
    public static final String OPTION_PET = " -p";
    public static final String OPTION_APPOINTMENT = " -a";

    // themes
    public static final String VALID_THEME_PATH_LIGHT = "/view/LightTheme.css";
    public static final String VALID_THEME_PATH_DARK = "/view/DarkTheme.css";

    // invalids
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_NRIC = "&2345678M"; // '&' not allowed in NRIC
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "&2345678M"; // '&' not allowed in NRIC
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_OWNER_DESC = " " + PREFIX_NRIC + "S000000000T"; // invalid Nric
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK; //empty string not allowed for remark
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "twelvemarchtwozerooneeight"; //follows format
    public static final String INVALID_THEME_PINK = "pink";
    public static final String INVALID_THEME_LIGHTT = "lightt";
    public static final String INVALID_OPTION = " -z";


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withNric(VALID_NRIC_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withNric(VALID_NRIC_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        Predicate<Person> namePredicate = currPerson -> Arrays.asList(splitName[0]).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(currPerson.getName().fullName, keyword));
        model.updateFilteredPersonList(namePredicate);

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        } catch (PetDependencyNotEmptyException e) {
            throw new AssertionError("Dependency still exists!");
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
