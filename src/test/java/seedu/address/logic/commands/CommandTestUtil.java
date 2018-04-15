package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;
import static seedu.address.testutil.TimetableBuilder.DUMMY_LINK_ONE;
import static seedu.address.testutil.TimetableBuilder.DUMMY_LINK_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.SecurityUtil;
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
    public static final String VALID_BIRTHDAY_AMY = "01011995";
    public static final String VALID_BIRTHDAY_BOB = "02021993";
    public static final String VALID_TIMETABLE_AMY = DUMMY_LINK_ONE;
    public static final String VALID_TIMETABLE_BOB = DUMMY_LINK_TWO;
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_UNUSED = "unused"; // do not use this tag when creating a person

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
    public static final String TIMETABLE_DESC_AMY = " " + PREFIX_TIMETABLE + VALID_TIMETABLE_AMY;
    public static final String TIMETABLE_DESC_BOB = " " + PREFIX_TIMETABLE + VALID_TIMETABLE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "12345b"; // 'b' not allowed in birthday
    public static final String INVALID_TIMETABLE_DESC = " " + PREFIX_TIMETABLE
            + "http://google.com/"; // not NUSMods links
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //@@author jingyinno
    public static final String VALID_ALIAS_ADD = "add1";
    public static final String VALID_ALIAS_ALIAS = "alias1";
    public static final String VALID_ALIAS_BIRTHDAYS = "birthdays1";
    public static final String VALID_ALIAS_CLEAR_COMMAND = ClearCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_CLEAR = "clear1";
    public static final String VALID_ALIAS_DELETE_COMMAND = DeleteCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_DELETE = "delete1";
    public static final String VALID_ALIAS_EDIT = "edit1";
    public static final String VALID_ALIAS_EXIT = "exit1";
    public static final String VALID_ALIAS_EXPORT = "export1";
    public static final String VALID_ALIAS_FIND = "find1";
    public static final String VALID_ALIAS_HELP_COMMAND = HelpCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_HELP = "help1";
    public static final String VALID_ALIAS_HISTORY = "history1";
    public static final String VALID_ALIAS_IMPORT = "import1";
    public static final String VALID_ALIAS_LIST_COMMAND = ListCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_LIST = "list1";
    public static final String VALID_ALIAS_MAP1 = "map1";
    public static final String VALID_ALIAS_MAP2 = "map2";
    public static final String VALID_ALIAS_ENCRYPT = "encrypt1";
    public static final String VALID_ALIAS_REDO = "redo1";
    public static final String VALID_ALIAS_DECRYPT = "decrypt1";
    public static final String VALID_ALIAS_SELECT = "select1";
    public static final String VALID_ALIAS_UNALIAS = "unalias1";
    public static final String VALID_ALIAS_UNDO = "undo1";
    public static final String VALID_ALIAS_VACANT = "vacant1";
    public static final String VALID_ALIAS_UNION = "union1";
    public static final String VALID_ALIAS_UPLOAD = "upload1";
    public static final String VALID_ALIAS_NUMBER = "911";

    public static final String ALIAS_DESC_ADD = AddCommand.COMMAND_WORD + " " + VALID_ALIAS_ADD;
    public static final String ALIAS_DESC_ALIAS = AliasCommand.COMMAND_WORD + " " + VALID_ALIAS_ALIAS;
    public static final String ALIAS_DESC_BIRTHDAYS = BirthdaysCommand.COMMAND_WORD + " " + VALID_ALIAS_BIRTHDAYS;
    public static final String ALIAS_DESC_CLEAR = ClearCommand.COMMAND_WORD + " " + VALID_ALIAS_CLEAR;
    public static final String ALIAS_DESC_DELETE = DeleteCommand.COMMAND_WORD + " " + VALID_ALIAS_DELETE;
    public static final String ALIAS_DESC_DECRYPT = RemovePasswordCommand.COMMAND_WORD + " " + VALID_ALIAS_DECRYPT;
    public static final String ALIAS_DESC_EDIT = EditCommand.COMMAND_WORD + " " + VALID_ALIAS_EDIT;
    public static final String ALIAS_DESC_EXIT = ExitCommand.COMMAND_WORD + " " + VALID_ALIAS_EXIT;
    public static final String ALIAS_DESC_ENCRYPT = PasswordCommand.COMMAND_WORD + " " + VALID_ALIAS_ENCRYPT;
    public static final String ALIAS_DESC_EXPORT = ExportCommand.COMMAND_WORD + " " + VALID_ALIAS_EXPORT;
    public static final String ALIAS_DESC_FIND = FindCommand.COMMAND_WORD + " " + VALID_ALIAS_FIND;
    public static final String ALIAS_DESC_HELP = HelpCommand.COMMAND_WORD + " " + VALID_ALIAS_HELP;
    public static final String ALIAS_DESC_HISTORY = HistoryCommand.COMMAND_WORD + " " + VALID_ALIAS_HISTORY;
    public static final String ALIAS_DESC_IMPORT = ImportCommand.COMMAND_WORD + " " + VALID_ALIAS_IMPORT;
    public static final String ALIAS_DESC_LIST = ListCommand.COMMAND_WORD + " " + VALID_ALIAS_LIST;
    public static final String ALIAS_DESC_MAP1 = MapCommand.COMMAND_WORD + " " + VALID_ALIAS_MAP1;
    public static final String ALIAS_DESC_MAP2 = MapCommand.COMMAND_WORD + " " + VALID_ALIAS_MAP2;
    public static final String ALIAS_DESC_REDO = RedoCommand.COMMAND_WORD + " " + VALID_ALIAS_REDO;
    public static final String ALIAS_DESC_SELECT = SelectCommand.COMMAND_WORD + " " + VALID_ALIAS_SELECT;
    public static final String ALIAS_DESC_UNALIAS = UnaliasCommand.COMMAND_WORD + " " + VALID_ALIAS_UNALIAS;
    public static final String ALIAS_DESC_UNDO = UndoCommand.COMMAND_WORD + " " + VALID_ALIAS_UNDO;
    public static final String ALIAS_DESC_VACANT = VacantCommand.COMMAND_WORD + " " + VALID_ALIAS_VACANT;
    public static final String ALIAS_DESC_UNION = TimetableUnionCommand.COMMAND_WORD + " " + VALID_ALIAS_UNION;
    public static final String ALIAS_DESC_UPLOAD = UploadCommand.COMMAND_WORD + " " + VALID_ALIAS_UPLOAD;
    public static final String ALIAS_DESC_NUMBER = UploadCommand.COMMAND_WORD + " " + VALID_ALIAS_NUMBER;

    public static final String INVALID_COMMAND_SYNTAX = "command!";
    public static final String INVALID_ALIAS = "alias!";
    public static final String INVALID_COMMAND_DESC = "invalid";

    public static final String INVALID_COMMAND_SYNTAX_DESC = INVALID_COMMAND_SYNTAX + " " + VALID_ALIAS_ALIAS;
    public static final String INVALID_COMMAND_WORD_DESC = INVALID_COMMAND_DESC + " " + VALID_ALIAS_ALIAS;
    public static final String INVALID_ALIAS_SYNTAX_DESC = SelectCommand.COMMAND_WORD + " " + INVALID_ALIAS;
    public static final String INVALID_ALIAS_WORD_DESC = SelectCommand.COMMAND_WORD + " " + VacantCommand.COMMAND_WORD;

    public static final String VALID_BUILDING_1 = "COM1";
    public static final String VALID_BUILDING_2 = "S1";
    public static final String VALID_BUILDING_3 = "ERC";

    public static final String INVALID_BUILDING_1 = "ERC*";
    public static final String INVALID_BUILDING_2 = "COM1 COM2";
    public static final String INVALID_BUILDING_3 = "Building";
    public static final String MIXED_CASE_VACANT_COMMAND_WORD = "VaCaNt";

    public static final String VALID_UNALIAS = VALID_ALIAS_ADD;

    public static final String INVALID_UNALIAS = "nonexistent";

    public static final String INVALID_UNALIAS_DESC = "alias!";

    public static final String VALID_LOCATION_BUILDING_UPPERCASE_1 = "EA";
    public static final String VALID_LOCATION_BUILDING_UPPERCASE_2 = "COM1";
    public static final String VALID_LOCATION_BUILDING_LOWERCASE = "ea";
    public static final String VALID_LOCATION_POSTAL_1 = "677743";
    public static final String VALID_LOCATION_POSTAL_2 = "819643";
    public static final String VALID_LOCATION_ADDRESS_1 = "Changi Airport Singapore";
    public static final String VALID_LOCATION_ADDRESS_2 = "Serangoon block 413";

    //EA to COM1
    public static final String VALID_TWO_LOCATIONS_BUILDING = VALID_LOCATION_BUILDING_UPPERCASE_1 + "/"
            + VALID_LOCATION_BUILDING_UPPERCASE_2;
    //677743 to 819643
    public static final String VALID_TWO_LOCATIONS_POSTAL = VALID_LOCATION_POSTAL_1 + "/"
            + VALID_LOCATION_POSTAL_2;
    //Changi Airport Terminal to Serangoon block 413
    public static final String VALID_TWO_LOCATIONS_ADDRESS = VALID_LOCATION_ADDRESS_1 + "/"
            + VALID_LOCATION_ADDRESS_2;
    //building, postal
    public static final String VALID_TWO_LOCATIONS_1 = VALID_LOCATION_BUILDING_UPPERCASE_1 + "/"
            + VALID_LOCATION_POSTAL_1;
    //building, address
    public static final String VALID_TWO_LOCATIONS_2 = VALID_LOCATION_BUILDING_UPPERCASE_1 + "/"
            + VALID_LOCATION_ADDRESS_1;
    //postal, address
    public static final String VALID_TWO_LOCATIONS_3 = VALID_LOCATION_POSTAL_1 + "/"
            + VALID_LOCATION_ADDRESS_1;

    public static final String VALID_THREE_LOCATIONS = VALID_LOCATION_ADDRESS_1 + "/"
            + VALID_LOCATION_BUILDING_LOWERCASE + "/" + VALID_LOCATION_POSTAL_2;
    //@@author

    //@@author yeggasd
    public static final String VALID_PASSWORD = "test";
    public static final byte[] VALID_PASSWORD_HASH;
    public static final String MIXED_CASE_PASSWORD_COMMAND_WORD = "EnCrYpT";
    public static final String MIXED_CASE_REMOVEPASSWORD_COMMAND_WORD = "DeCrYpT";
    //@@author

    //@@author Caijun7
    public static final String VALID_IMPORT_FILEPATH = "src/test/data/ImportCommandTest/aliceAddressBook.xml";
    public static final String ENCRYPTED_IMPORT_FILEPATH =
            "src/test/data/ImportCommandTest/encryptedAliceBensonAddressBook.xml";
    public static final String TEST_PASSWORD = "test";
    public static final String WRONG_PASSWORD = "wrong";
    public static final String MIXED_CASE_IMPORT_COMMAND_WORD = "ImPoRt";
    public static final String INVALID_IMPORT_FILEPATH = "src/";
    public static final String INVALID_FILE_FORMAT = "src/test/data/ImportCommandTest/invalidFileFormatAddressBook.xml";

    public static final String VALID_EXPORT_FILEPATH = "src/test/data/sandbox/temp.xml";
    public static final String MIXED_CASE_EXPORT_COMMAND_WORD = "ExPoRt";
    public static final String INVALID_EXPORT_FILEPATH = "src/";

    public static final String VALID_UPLOAD_FILEPATH = "src/test/data/sandbox/temp.xml";
    public static final String MIXED_CASE_UPLOAD_COMMAND_WORD = "UpLoAd";
    public static final String INVALID_UPLOAD_FILEPATH = "src/";
    //@@author

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        VALID_PASSWORD_HASH = SecurityUtil.hashPassword(VALID_PASSWORD);
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withTimetable(VALID_TIMETABLE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withTimetable(VALID_TIMETABLE_BOB)
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
