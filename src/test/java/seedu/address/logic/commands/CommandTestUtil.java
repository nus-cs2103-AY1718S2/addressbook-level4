package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JERSEY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;
/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_UNUSED = "unused";
    public static final String VALID_REMARK_AMY = "MVP of all time";
    public static final String VALID_REMARK_BOB = "Need 5 more training!";
    public static final String VALID_REMARK_EMPTY = "";
    public static final String VALID_TEAM_ARSENAL = "Arsenal";
    public static final String VALID_TEAM_BARCELONA = "Barcelona";
    public static final String VALID_TEAM_CHELSEA = "Chelsea";
    public static final String VALID_RATING_0 = "0";
    public static final String VALID_RATING_1 = "1";
    public static final String VALID_POSITION_STRIKER = "1";
    public static final String VALID_POSITION_MIDFIELD = "2";
    public static final String VALID_JERSEY_NUMBER_2 = "2";
    public static final String VALID_JERSEY_NUMBER_17 = "17";
    public static final String VALID_AVATAR_WINDOWS = System.getProperty("user.dir") + "\\images\\placeholder_test.png";
    public static final String VALID_AVATAR_MAC_LINUX = System.getProperty("user.dir") + "/images/placeholder_test.png";
    public static final String VALID_INDEX = "1";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String RATING_DESC_0 = " " + PREFIX_RATING + VALID_RATING_0;
    public static final String RATING_DESC_1 = " " + PREFIX_RATING + VALID_RATING_1;
    public static final String POSITION_DESC_STRIKER = " " + PREFIX_POSITION + VALID_POSITION_STRIKER;
    public static final String POSITION_DESC_MIDFILED = " " + PREFIX_POSITION + VALID_POSITION_MIDFIELD;
    public static final String JERSEY_NUMBER_DESC_2 = " " + PREFIX_JERSEY_NUMBER + VALID_JERSEY_NUMBER_2;
    public static final String JERSEY_NUMBER_DESC_17 = " " + PREFIX_JERSEY_NUMBER + VALID_JERSEY_NUMBER_17;
    public static final String AVATAR_WINDOWS = " " + PREFIX_AVATAR + VALID_AVATAR_WINDOWS;
    public static final String AVATAR_MAC_LINUX = " " + PREFIX_AVATAR + VALID_AVATAR_MAC_LINUX;
    public static final String TEAM_DESC_ARSENAL = " " + PREFIX_TEAM_NAME + VALID_TEAM_ARSENAL;
    public static final String TEAM_DESC_CHELSEA = " " + PREFIX_TEAM_NAME + VALID_TEAM_CHELSEA;
    public static final String INDEX_DESC_1 = " " + PREFIX_INDEX + VALID_INDEX;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_RATING_DESC = " " + PREFIX_RATING + "06"; // only integer 0 to 5 allowed
    public static final String INVALID_POSITION_DESC = " " + PREFIX_POSITION + "10"; // only integer 1 to 4 allowed
    // only integer 0 to 99 not allowed
    public static final String INVALID_JERSEY_NUMBER_DESC = " " + PREFIX_JERSEY_NUMBER + "100";
    //only jpg and png file type allowed
    public static final String INVALID_AVATAR_TYPE = " " + PREFIX_AVATAR + "images.gif";
    //only jpg and png file type allowed
    public static final String INVALID_AVATAR_NO_FILE = " " + PREFIX_AVATAR + "file.png";
    public static final String INVALID_TEAM_NAME_DESC = " " + PREFIX_TEAM_NAME
            + "&-Team-&"; // '&' not allowed in team names
    public static final String INVALID_TEAM_NAME = "&-Team-&"; // without prefix and '&' not allowed in team names
    public static final String INVALID_INDEX = " " + PREFIX_INDEX + "-1"; // negative not allowed as index

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).withRating(VALID_RATING_0)
                .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withRating(VALID_RATING_1)
                .withPosition(VALID_POSITION_MIDFIELD).withJerseyNumber(VALID_JERSEY_NUMBER_17).build();
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
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

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
