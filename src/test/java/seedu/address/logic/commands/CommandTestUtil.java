package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREFERENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.order.Order;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditEntryDescriptorBuilder;
import seedu.address.testutil.EditOrderDescriptorBuilder;
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
    public static final String VALID_GROUP_FRIEND = "friend";
    public static final String VALID_GROUP_COLLEAGUES = "colleagues";
    public static final String VALID_PREFERENCE_COMPUTERS = "computers";
    public static final String VALID_PREFERENCE_SHOES = "shoes";

    public static final String VALID_ORDER_INFORMATION_CHOC = "Chocolates";
    public static final String VALID_ORDER_STATUS_CHOC = "ongoing";
    public static final String VALID_PRICE_CHOC = "10.00";
    public static final String VALID_QUANTITY_CHOC = "15";
    public static final String VALID_DELIVERY_DATE_CHOC = "12-08-2018";
    public static final String VALID_ORDER_INFORMATION_BOOKS = "Books";
    public static final String VALID_ORDER_STATUS_BOOKS = "ongoing";
    public static final String VALID_PRICE_BOOKS = "15.00";
    public static final String VALID_QUANTITY_BOOKS = "3";
    public static final String VALID_DELIVERY_DATE_BOOKS = "04-12-2018";
    public static final String VALID_ORDER_INFORMATION_COMPUTER = "Computer";
    public static final String VALID_ORDER_STATUS_COMPUTER = "ongoing";
    public static final String VALID_PRICE_COMPUTER = "2000.00";
    public static final String VALID_QUANTITY_COMPUTER = "1";
    public static final String VALID_DELIVERY_DATE_COMPUTER = "18-07-2018";

    public static final String VALID_ENTRY_TITLE_MEET_BOSS = "Meeting with boss";
    public static final String VALID_ENTRY_TITLE_GET_STOCKS = "Get stocks from supplier";
    public static final String VALID_START_DATE_MEET_BOSS = "06-06-2018";
    public static final String VALID_START_DATE_GET_STOCKS = "01-07-2018";
    public static final String VALID_END_DATE_MEET_BOSS = "06-06-2018";
    public static final String VALID_END_DATE_GET_STOCKS = "01-07-2018";
    public static final String VALID_START_TIME_MEET_BOSS = "10:00";
    public static final String VALID_START_TIME_GET_STOCKS = "08:00";
    public static final String VALID_END_TIME_MEET_BOSS = "12:00";
    public static final String VALID_END_TIME_GET_STOCKS = "13:00";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String GROUP_DESC_FRIEND = " " + PREFIX_GROUP + VALID_GROUP_FRIEND;
    public static final String GROUP_DESC_COLLEAGUES = " " + PREFIX_GROUP + VALID_GROUP_COLLEAGUES;

    public static final String PREFERENCE_DESC_COMPUTERS = " " + PREFIX_PREFERENCE + VALID_PREFERENCE_COMPUTERS;
    public static final String PREFERENCE_DESC_SHOES = " " + PREFIX_PREFERENCE + VALID_PREFERENCE_SHOES;

    public static final String ORDER_INFORMATION_DESC_CHOC = " " + PREFIX_ORDER_INFORMATION
            + VALID_ORDER_INFORMATION_CHOC;
    public static final String PRICE_DESC_CHOC = " " + PREFIX_PRICE + VALID_PRICE_CHOC;
    public static final String QUANTITY_DESC_CHOC = " " + PREFIX_QUANTITY + VALID_QUANTITY_CHOC;
    public static final String DELIVERY_DATE_DESC_CHOC = " " + PREFIX_DELIVERY_DATE + VALID_DELIVERY_DATE_CHOC;
    public static final String ORDER_INFORMATION_DESC_BOOKS = " " + PREFIX_ORDER_INFORMATION
            + VALID_ORDER_INFORMATION_BOOKS;
    public static final String PRICE_DESC_BOOKS = " " + PREFIX_PRICE + VALID_PRICE_BOOKS;
    public static final String QUANTITY_DESC_BOOKS = " " + PREFIX_QUANTITY + VALID_QUANTITY_BOOKS;
    public static final String DELIVERY_DATE_DESC_BOOKS = " " + PREFIX_DELIVERY_DATE + VALID_DELIVERY_DATE_BOOKS;
    public static final String ORDER_INFORMATION_DESC_COMPUTER = " " + PREFIX_ORDER_INFORMATION
            + VALID_ORDER_INFORMATION_COMPUTER;
    public static final String PRICE_DESC_COMPUTER = " " + PREFIX_PRICE + VALID_PRICE_COMPUTER;
    public static final String QUANTITY_DESC_COMPUTER = " " + PREFIX_QUANTITY + VALID_QUANTITY_COMPUTER;
    public static final String DELIVERY_DATE_DESC_COMPUTER = " " + PREFIX_DELIVERY_DATE + VALID_DELIVERY_DATE_COMPUTER;

    public static final String ENTRY_TITLE_DESC_MEET_BOSS = " " + PREFIX_ENTRY_TITLE + VALID_ENTRY_TITLE_MEET_BOSS;
    public static final String ENTRY_TITLE_DESC_GET_STOCKS = " " + PREFIX_ENTRY_TITLE + VALID_ENTRY_TITLE_GET_STOCKS;
    public static final String START_DATE_DESC_MEET_BOSS = " " + PREFIX_START_DATE + VALID_START_DATE_MEET_BOSS;
    public static final String START_DATE_DESC_GET_STOCKS = " " + PREFIX_START_DATE + VALID_START_DATE_GET_STOCKS;
    public static final String END_DATE_DESC_MEET_BOSS = " " + PREFIX_END_DATE + VALID_END_DATE_MEET_BOSS;
    public static final String END_DATE_DESC_GET_STOCKS = " " + PREFIX_END_DATE + VALID_END_DATE_GET_STOCKS;
    public static final String START_TIME_DESC_MEET_BOSS = " " + PREFIX_START_TIME + VALID_START_TIME_MEET_BOSS;
    public static final String START_TIME_DESC_GET_STOCKS = " " + PREFIX_START_TIME + VALID_START_TIME_GET_STOCKS;
    public static final String END_TIME_DESC_MEET_BOSS = " " + PREFIX_END_TIME + VALID_END_TIME_MEET_BOSS;
    public static final String END_TIME_DESC_GET_STOCKS = " " + PREFIX_END_TIME + VALID_END_TIME_GET_STOCKS;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_GROUP_DESC = " " + PREFIX_GROUP + "hubby*"; // '*' not allowed in groups
    public static final String INVALID_PREFERENCE_DESC =
            " " + PREFIX_PREFERENCE + "computers*"; // '*' not allowed in preferences

    public static final String INVALID_ORDER_INFORMATION_DESC = " "
            + PREFIX_ORDER_INFORMATION + "&Books"; // '&' not allowed in order information
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "jdw11"; // 'jdw' not allowed in prices
    public static final String INVALID_QUANTITY_DESC = " "
            + PREFIX_QUANTITY + "-11"; // '-' sign not allowed in quantities
    public static final String INVALID_DELIVERY_DATE_DESC = " " + PREFIX_DELIVERY_DATE + "20-45-10000"; // illegal date

    public static final String INVALID_ENTRY_TITLE_DESC = " "
            + PREFIX_ENTRY_TITLE + "M@@ting with the boss, "; // '@' and ',' are not allowed in event title.
    public static final String INVALID_START_DATE_DESC = " " + PREFIX_START_DATE + "31-02-2018"; // Illegal date
    public static final String INVALID_END_DATE_DESC = " " + PREFIX_END_DATE + "23-20-20000"; // Illegal date
    public static final String INVALID_START_TIME_DESC = " " + PREFIX_START_TIME + "12-30"; //Illegal time format
    public static final String INVALID_END_TIME_DESC = " " + PREFIX_END_TIME + "25:70"; // Illegal time
    public static final String INVALID_START_DATE_LATER_THAN_END_DATE_DESC =
            " " + PREFIX_START_DATE + "06-07-2018"; // Start Date later than 06-06-2018
    public static final String INVALID_START_TIME_LATER_THAN_END_TIME_DESC =
            " " + PREFIX_START_TIME + "23:00"; // Start Time later than End time same Start Date and End Date.
    public static final String INVALID_START_TIME_LESS_THAN_FIFTEEN_MINUTES_FROM_END_TIME_DESC =
            " " + PREFIX_START_TIME + "11:50";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String INVALID_THEME = "day791";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditOrderCommand.EditOrderDescriptor DESC_COMPUTER;
    public static final EditOrderCommand.EditOrderDescriptor DESC_COMICBOOK;

    public static final EditEntryCommand.EditEntryDescriptor DESC_MEET_BOSS;
    public static final EditEntryCommand.EditEntryDescriptor DESC_GET_STOCKS;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withGroups(VALID_GROUP_FRIEND).withPreferences(VALID_PREFERENCE_SHOES).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withGroups(VALID_GROUP_COLLEAGUES, VALID_GROUP_FRIEND)
                .withPreferences(VALID_PREFERENCE_COMPUTERS).build();
    }

    static {
        DESC_COMPUTER = new EditOrderDescriptorBuilder().withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withOrderStatus(VALID_ORDER_STATUS_COMPUTER)
                .withPrice(VALID_PRICE_COMPUTER).withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER).build();
        DESC_COMICBOOK = new EditOrderDescriptorBuilder().withOrderInformation("Comic Book")
                .withOrderStatus("ongoing")
                .withPrice("17.99").withQuantity("1")
                .withDeliveryDate("01-01-2018")
                .build();
    }

    static {
        DESC_MEET_BOSS = new EditEntryDescriptorBuilder().withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_START_DATE_MEET_BOSS).withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(VALID_START_TIME_MEET_BOSS).withEndTime(VALID_END_TIME_MEET_BOSS).build();
        DESC_GET_STOCKS = new EditEntryDescriptorBuilder().withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS)
                .withStartDate(VALID_START_DATE_GET_STOCKS).withEndDate(VALID_END_DATE_GET_STOCKS)
                .withStartTime(VALID_START_TIME_GET_STOCKS).withEndTime(VALID_END_TIME_GET_STOCKS).build();
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
        List<Person> expectedFilteredPersonList = new ArrayList<>(actualModel.getFilteredPersonList());
        List<Order> expectedFilteredOrderList = new ArrayList<>(actualModel.getFilteredOrderList());
        CalendarManager expectedCalendarManager = new CalendarManager(actualModel.getCalendarManager());
        List<CalendarEntry> expectedFilteredCalendarEntryList =
                new ArrayList<>(actualModel.getFilteredCalendarEntryList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredPersonList, actualModel.getFilteredPersonList());
            assertEquals(expectedFilteredOrderList, actualModel.getFilteredOrderList());
            assertEquals(expectedCalendarManager, actualModel.getCalendarManager());
            assertEquals(expectedFilteredCalendarEntryList, actualModel.getFilteredCalendarEntryList());

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
