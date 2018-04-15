package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPLIT_BY_PERCENTAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPLIT_BY_UNITS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPLIT_METHOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;

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
    public static final String VALID_NAME_JACK = "Jack Choo";
    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_JACK = "33333333";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_JACK = "jack@example.com";

    public static final String VALID_BALANCE_AMY = "0.00";
    public static final String VALID_BALANCE_BOB = "0.00";
    public static final String VALID_BALANCE_JACK = "0.00";

    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_TRANSACTION_TYPE_ONE  = "payment";
    public static final String VALID_TRANSACTION_TYPE_TWO  = "Payment";
    public static final String VALID_TRANSACTION_TYPE_THREE  = "paydebt";
    public static final String VALID_TRANSACTION_TYPE_FOUR  = "PayDebt";

    public static final String VALID_TRANSACTION_AMOUNT_ONE  = "1234.50";
    public static final String VALID_TRANSACTION_AMOUNT_TWO  = "10.10";

    public static final String VALID_TRANSACTION_DESCRIPTION_ONE  = "for dinner meal";
    public static final String VALID_TRANSACTION_DESCRIPTION_TWO  = "for transport";

    public static final String VALID_TRANSACTION_PAYEE_ONE  = "Benson Meier";
    public static final String VALID_TRANSACTION_PAYEE_TWO  = "Carl Kurz";

    public static final String VALID_TRANSACTION_SPLIT_METHOD_ONE  = "eVenly";
    public static final String VALID_TRANSACTION_SPLIT_METHOD_ONE_SPACE  = " evenly";
    public static final String VALID_TRANSACTION_SPLIT_METHOD_TWO  = "percentage";
    public static final String VALID_TRANSACTION_SPLIT_METHOD_PERCENTAGE  = "40, 40, 20";
    public static final String VALID_TRANSACTION_SPLIT_METHOD_THREE  = "units";
    public static final String VALID_TRANSACTION_SPLIT_METHOD_UNITS  = "4, 2, 1";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_JACK = " " + PREFIX_NAME + VALID_NAME_JACK;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String TRANSACTION_TYPE_DESC_ONE = " " + PREFIX_TRANSACTION_TYPE + VALID_TRANSACTION_TYPE_ONE;
    public static final String TRANSACTION_TYPE_DESC_TWO = " " + PREFIX_TRANSACTION_TYPE + VALID_TRANSACTION_TYPE_TWO;
    public static final String TRANSACTION_TYPE_DESC_THREE = " " + PREFIX_TRANSACTION_TYPE
            + VALID_TRANSACTION_TYPE_THREE;
    public static final String TRANSACTION_PAYER_DESC = " " + PREFIX_PAYER + VALID_NAME_ALICE;
    public static final String TRANSACTION_PAYER_DESC_TWO = " " + PREFIX_PAYER + VALID_NAME_JACK;
    public static final String TRANSACTION_PAYER_DESC_THREE = " " + PREFIX_PAYER + VALID_TRANSACTION_PAYEE_ONE;
    public static final String TRANSACTION_DESC_AMOUNT_ONE = " " + PREFIX_AMOUNT + VALID_TRANSACTION_AMOUNT_ONE;
    public static final String TRANSACTION_DESC_AMOUNT_TWO = " " + PREFIX_AMOUNT + VALID_TRANSACTION_AMOUNT_TWO;
    public static final String TRANSACTION_DESC_DESCRIPTION_ONE = " "
            + PREFIX_DESCRIPTION + VALID_TRANSACTION_DESCRIPTION_ONE;
    public static final String TRANSACTION_DESC_DESCRIPTION_TWO = " "
            + PREFIX_DESCRIPTION + VALID_TRANSACTION_DESCRIPTION_TWO;
    public static final String TRANSACTION_PAYEE_DESC_ONE = " " + PREFIX_PAYEE  + VALID_TRANSACTION_PAYEE_ONE;
    public static final String TRANSACTION_PAYEE_DESC_TWO = " " + PREFIX_PAYEE  + VALID_TRANSACTION_PAYEE_TWO;
    public static final String TRANSACTION_DESC_SPLIT_METHOD_ONE = " "
            + PREFIX_SPLIT_METHOD + VALID_TRANSACTION_SPLIT_METHOD_ONE;
    public static final String TRANSACTION_DESC_SPLIT_METHOD_TWO = " "
            + PREFIX_SPLIT_METHOD + VALID_TRANSACTION_SPLIT_METHOD_TWO;
    public static final String TRANSACTION_DESC_SPLIT_METHOD_PERCENTAGE = " "
            + PREFIX_SPLIT_BY_PERCENTAGE + VALID_TRANSACTION_SPLIT_METHOD_PERCENTAGE;
    public static final String TRANSACTION_DESC_SPLIT_METHOD_THREE = " "
            + PREFIX_SPLIT_METHOD + VALID_TRANSACTION_SPLIT_METHOD_THREE;
    public static final String TRANSACTION_DESC_SPLIT_METHOD_UNITS = " "
            + PREFIX_SPLIT_BY_UNITS + VALID_TRANSACTION_SPLIT_METHOD_UNITS;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_TRANSACTION_TYPE_DESC = " "
            + PREFIX_TRANSACTION_TYPE + "payment*"; // '*' not allowed in transaction
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "1.234"; // '*' not allowed in transaction
    public static final String INVALID_DESCRIPTION_DESC = " "
            + PREFIX_DESCRIPTION + ""; // '*' not allowed in transaction
    public static final String INVALID_SPLIT_METHOD_DESC_ONE = " "
            + PREFIX_SPLIT_METHOD + "Evenly1"; // '*' not allowed in transaction
    public static final String INVALID_SPLIT_METHOD_DESC_TWO = " "
            + PREFIX_SPLIT_METHOD + "percentage1"; // '*' not allowed in transaction
    public static final String INVALID_SPLIT_METHOD_DESC_PERCENTAGE_ONE = " "
            + PREFIX_SPLIT_BY_PERCENTAGE + "50 50 50"; // '*' not allowed in transaction
    public static final String INVALID_SPLIT_METHOD_DESC_PERCENTAGE_TWO = " "
            + PREFIX_SPLIT_BY_PERCENTAGE + "50, 100"; // '*' not allowed in transaction
    public static final String INVALID_SPLIT_METHOD_DESC_PERCENTAGE_THREE = " "
            + PREFIX_SPLIT_BY_PERCENTAGE + "50"; // '*' not allowed in transaction
    public static final String INVALID_SPLIT_METHOD_DESC_THREE = " "
            + PREFIX_SPLIT_METHOD + "units1"; // '*' not allowed in transaction
    public static final String INVALID_SPLIT_METHOD_DESC_UNITS_ONE = " "
            + PREFIX_SPLIT_BY_UNITS + "4.2, 4.5, 4.6"; // '*' not allowed in transaction
    public static final String INVALID_SPLIT_METHOD_DESC_UNITS_TWO = " "
            + PREFIX_SPLIT_BY_UNITS + "4"; // '*' not allowed in transaction

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonCommand.EditPersonDescriptor DESC_AMY;
    public static final EditPersonCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build(); }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) throws PersonNotFoundException {
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
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage)
            throws PersonNotFoundException {
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
    public static void deleteFirstPerson(Model model) throws CommandException {
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
