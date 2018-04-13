package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET_DATE;
import static seedu.address.model.theme.Theme.LIGHT_THEME_KEYWORD;
import static seedu.address.testutil.OrderBuilder.DEFAULT_DELIVERY_DATE;
import static seedu.address.testutil.OrderBuilder.DEFAULT_ORDER_INFORMATION;
import static seedu.address.testutil.OrderBuilder.DEFAULT_PRICE;
import static seedu.address.testutil.OrderBuilder.DEFAULT_QUANTITY;
import static seedu.address.testutil.TypicalGroups.FRIENDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE_STRING;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.commands.CalendarJumpCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEntryCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCalendarEntryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewBackCommand;
import seedu.address.logic.commands.ViewCalendarCommand;
import seedu.address.logic.commands.ViewNextCommand;
import seedu.address.logic.commands.ViewTodayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.order.Order;
import seedu.address.model.person.GroupsContainKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PreferencesContainKeywordsPredicate;
import seedu.address.testutil.CalendarEntryBuilder;
import seedu.address.testutil.CalendarEntryUtil;
import seedu.address.testutil.EditEntryDescriptorBuilder;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.OrderUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    //@@author amad-person
    @Test
    public void parseCommand_addAlias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS
                + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addOrder() throws Exception {
        Order order = new OrderBuilder().build();
        AddOrderCommand command = (AddOrderCommand) parser.parseCommand(OrderUtil
                .getAddOrderCommand(INDEX_FIRST_PERSON.getOneBased(), order));
        assertEquals(new AddOrderCommand(INDEX_FIRST_PERSON, order), command);
    }

    @Test
    public void parseCommand_addOrderAlias() throws Exception {
        Order order = new OrderBuilder().build();
        AddOrderCommand command = (AddOrderCommand) parser.parseCommand(AddOrderCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + OrderUtil.getOrderDetails(order));
        assertEquals(new AddOrderCommand(INDEX_FIRST_PERSON, order), command);
    }
    //@@author

    @Test
    public void parseCommand_addEntry() throws Exception {
        CalendarEntry calendarEntry = new CalendarEntryBuilder().build();
        AddEntryCommand command = (AddEntryCommand) parser.parseCommand(CalendarEntryUtil
                .getAddEntryCommand(calendarEntry));

        assertEquals(new AddEntryCommand(calendarEntry), command);
    }

    @Test
    public void parseCommand_addEntryAlias() throws Exception {
        CalendarEntry calendarEntry = new CalendarEntryBuilder().build();
        AddEntryCommand command = (AddEntryCommand) parser.parseCommand(AddEntryCommand.COMMAND_ALIAS
                + " " + CalendarEntryUtil.getCalendarEntryDetails(calendarEntry));

        assertEquals(new AddEntryCommand(calendarEntry), command);
    }

    @Test
    public void parseCommand_calendarJump() throws Exception {
        CalendarJumpCommand command = (CalendarJumpCommand) parser.parseCommand(
                CalendarJumpCommand.COMMAND_WORD + " " + PREFIX_TARGET_DATE + "06-06-1990");

        assertEquals(new CalendarJumpCommand(NORMAL_DATE, NORMAL_DATE_STRING), command);
    }

    @Test
    public void parseCommand_calendarJumpAlias() throws Exception {
        CalendarJumpCommand command = (CalendarJumpCommand) parser.parseCommand(
                CalendarJumpCommand.COMMAND_ALIAS + " " + PREFIX_TARGET_DATE + "06-06-1990");

        assertEquals(new CalendarJumpCommand(NORMAL_DATE, NORMAL_DATE_STRING), command);
    }

    //@@author amad-person
    @Test
    public void parseCommand_changeTheme() throws Exception {
        ChangeThemeCommand command = (ChangeThemeCommand) parser.parseCommand(
                ChangeThemeCommand.COMMAND_WORD + " " + LIGHT_THEME_KEYWORD);
        assertEquals(new ChangeThemeCommand(LIGHT_THEME_KEYWORD), command);
    }

    @Test
    public void parseCommand_changeThemeAlias() throws Exception {
        ChangeThemeCommand command = (ChangeThemeCommand) parser.parseCommand(
                ChangeThemeCommand.COMMAND_ALIAS + " " + LIGHT_THEME_KEYWORD);
        assertEquals(new ChangeThemeCommand(LIGHT_THEME_KEYWORD), command);
    }
    //@@author

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    //@@author amad-person
    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }
    //@@author

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author amad-person
    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author

    @Test
    public void parseCommand_deleteGroup() throws Exception {
        DeleteGroupCommand command = (DeleteGroupCommand) parser.parseCommand(
                DeleteGroupCommand.COMMAND_WORD + " " + "friends");
        assertEquals(new DeleteGroupCommand(FRIENDS), command);
    }

    @Test
    public void parseCommand_deleteGroupAlias() throws Exception {
        DeleteGroupCommand command = (DeleteGroupCommand) parser.parseCommand(
                DeleteGroupCommand.COMMAND_ALIAS + " " + "friends");
        assertEquals(new DeleteGroupCommand(FRIENDS), command);
    }

    @Test
    public void parseCommand_deletePreference() throws Exception {
        DeletePreferenceCommand command = (DeletePreferenceCommand) parser.parseCommand(
                DeletePreferenceCommand.COMMAND_WORD + " " + "computers");
        assertEquals(new DeletePreferenceCommand(COMPUTERS), command);
    }

    @Test
    public void parseCommand_deletePreferenceAlias() throws Exception {
        DeletePreferenceCommand command = (DeletePreferenceCommand) parser.parseCommand(
                DeletePreferenceCommand.COMMAND_ALIAS + " " + "computers");
        assertEquals(new DeletePreferenceCommand(COMPUTERS), command);
    }

    //@@author amad-person
    @Test
    public void parseCommand_deleteOrder() throws Exception {
        DeleteOrderCommand command = (DeleteOrderCommand) parser.parseCommand(
                DeleteOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteOrderCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_deleteOrderAlias() throws Exception {
        DeleteOrderCommand command = (DeleteOrderCommand) parser.parseCommand(
                DeleteOrderCommand.COMMAND_ALIAS + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteOrderCommand(INDEX_FIRST_ORDER), command);
    }
    //@@author

    @Test
    public void parseCommand_deleteEntry() throws Exception {
        DeleteEntryCommand command = (DeleteEntryCommand) parser.parseCommand(DeleteEntryCommand.COMMAND_WORD
                + " " + INDEX_FIRST_ENTRY.getOneBased());
        assertEquals(new DeleteEntryCommand(INDEX_FIRST_ENTRY), command);
    }

    @Test
    public void parseCommand_deleteEntryAlias() throws Exception {
        DeleteEntryCommand command = (DeleteEntryCommand) parser.parseCommand(DeleteEntryCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_ENTRY.getOneBased());
        assertEquals(new DeleteEntryCommand(INDEX_FIRST_ENTRY), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    //@@author amad-person
    @Test
    public void parseCommand_editAlias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editOrder() throws Exception {
        Order order = new OrderBuilder().build();
        EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(DEFAULT_ORDER_INFORMATION).withPrice(DEFAULT_PRICE)
                .withQuantity(DEFAULT_QUANTITY).withDeliveryDate(DEFAULT_DELIVERY_DATE).build();

        EditOrderCommand command = (EditOrderCommand) parser.parseCommand(EditOrderCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ORDER.getOneBased() + " " + OrderUtil.getOrderDetails(order));

        assertEquals(new EditOrderCommand(INDEX_FIRST_ORDER, descriptor), command);
    }

    @Test
    public void parseCommand_editOrderAlias() throws Exception {
        Order order = new OrderBuilder().build();
        EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(DEFAULT_ORDER_INFORMATION).withPrice(DEFAULT_PRICE)
                .withQuantity(DEFAULT_QUANTITY).withDeliveryDate(DEFAULT_DELIVERY_DATE).build();

        EditOrderCommand command = (EditOrderCommand) parser.parseCommand(EditOrderCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_ORDER.getOneBased() + " " + OrderUtil.getOrderDetails(order));

        assertEquals(new EditOrderCommand(INDEX_FIRST_ORDER, descriptor), command);
    }
    //@@author

    @Test
    public void parseCommand_editEntry() throws Exception {
        CalendarEntry entry = new CalendarEntryBuilder().build();
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(entry).build();
        EditEntryCommand command = (EditEntryCommand) parser.parseCommand(EditEntryCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ENTRY.getOneBased() + " " + CalendarEntryUtil.getCalendarEntryDetails(entry));
        assertEquals(new EditEntryCommand(INDEX_FIRST_ENTRY, descriptor), command);
    }

    @Test
    public void parseCommand_editEntryAlias() throws Exception {
        CalendarEntry entry = new CalendarEntryBuilder().build();
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(entry).build();
        EditEntryCommand command = (EditEntryCommand) parser.parseCommand(EditEntryCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_ENTRY.getOneBased() + " " + CalendarEntryUtil.getCalendarEntryDetails(entry));
        assertEquals(new EditEntryCommand(INDEX_FIRST_ENTRY, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    //@@author amad-person
    @Test
    public void parseCommand_exitAlias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }
    //@@author

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    //@@author amad-person
    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
    //@@author

    @Test
    public void parseCommand_findGroups() throws Exception {
        List<String> keywords = Arrays.asList("friends", "family", "neighbours");
        FindGroupCommand command = (FindGroupCommand) parser.parseCommand(
                FindGroupCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindGroupCommand(new GroupsContainKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findGroupAlias() throws Exception {
        List<String> keywords = Arrays.asList("friends", "family", "neighbours");
        FindGroupCommand command = (FindGroupCommand) parser.parseCommand(
                FindGroupCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindGroupCommand(new GroupsContainKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findPreference() throws Exception {
        List<String> keywords = Arrays.asList("shoes", "computers", "necklaces");
        FindPreferenceCommand command = (FindPreferenceCommand) parser.parseCommand(
                FindPreferenceCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindPreferenceCommand(new PreferencesContainKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findPreferenceAlias() throws Exception {
        List<String> keywords = Arrays.asList("shoes", "computers", "necklaces");
        FindPreferenceCommand command = (FindPreferenceCommand) parser.parseCommand(
                FindPreferenceCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindPreferenceCommand(new PreferencesContainKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    //@@author amad-person
    @Test
    public void parseCommand_helpAlias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
    }
    //@@author

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    //@@author amad-person
    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
    //@@author

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    //@@author amad-person
    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }
    //@@author

    @Test
    public void parseCommand_calendarEntryList() throws Exception {
        assertTrue(parser.parseCommand(ListCalendarEntryCommand.COMMAND_WORD) instanceof ListCalendarEntryCommand);
        assertTrue(parser.parseCommand(
                ListCalendarEntryCommand.COMMAND_WORD + " 3") instanceof ListCalendarEntryCommand);
    }

    @Test
    public void parseCommand_calendarEntryListAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCalendarEntryCommand.COMMAND_ALIAS) instanceof ListCalendarEntryCommand);
        assertTrue(parser.parseCommand(
                ListCalendarEntryCommand.COMMAND_ALIAS + " 3") instanceof ListCalendarEntryCommand);
    }

    @Test
    public void parseCommand_orderList() throws Exception {
        assertTrue(parser.parseCommand(ListOrderCommand.COMMAND_WORD) instanceof ListOrderCommand);
        assertTrue(parser.parseCommand(
                ListOrderCommand.COMMAND_WORD + " 3") instanceof ListOrderCommand);
    }

    @Test
    public void parseCommand_orderListAlias() throws Exception {
        assertTrue(parser.parseCommand(ListOrderCommand.COMMAND_ALIAS) instanceof ListOrderCommand);
        assertTrue(parser.parseCommand(
                ListOrderCommand.COMMAND_ALIAS + " 3") instanceof ListOrderCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author amad-person
    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }
    //@@author

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    //@@author amad-person
    @Test
    public void parseCommand_redoCommandWordAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }
    //@@author

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    //@@author amad-person
    @Test
    public void parseCommand_undoCommandWordAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }
    //@@author

    @Test
    public void parseCommand_viewCalendarCommand_returnsViewCalendarCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewCalendarCommand.COMMAND_WORD) instanceof ViewCalendarCommand);
        assertTrue(parser.parseCommand("calendar DAY") instanceof ViewCalendarCommand);
    }

    @Test
    public void parseCommand_viewCalendarCommandAlias_returnsViewCalendarCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewCalendarCommand.COMMAND_ALIAS) instanceof ViewCalendarCommand);
        assertTrue(parser.parseCommand("cal DAY") instanceof ViewCalendarCommand);
    }

    @Test
    public void parseCommand_viewBackCommand_returnsViewBackCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewBackCommand.COMMAND_WORD) instanceof ViewBackCommand);
        assertTrue(parser.parseCommand("calendarback 2") instanceof ViewBackCommand);
    }

    @Test
    public void parseCommand_viewBackCommandAlias_returnsViewBackCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewBackCommand.COMMAND_ALIAS) instanceof ViewBackCommand);
        assertTrue(parser.parseCommand("calback 2") instanceof ViewBackCommand);
    }

    @Test
    public void parseCommand_viewNextCommand_returnsViewNextCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewNextCommand.COMMAND_WORD) instanceof ViewNextCommand);
        assertTrue(parser.parseCommand("calendarnext 1") instanceof ViewNextCommand);
    }

    @Test
    public void parseCommand_viewNextCommandAlias_returnsViewNextCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewNextCommand.COMMAND_ALIAS) instanceof ViewNextCommand);
        assertTrue(parser.parseCommand("calnext 1") instanceof ViewNextCommand);
    }

    @Test
    public void parseCommand_viewTodayCommand_returnsViewTodayCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewTodayCommand.COMMAND_WORD) instanceof ViewTodayCommand);
        assertTrue(parser.parseCommand("calendartoday 5") instanceof ViewTodayCommand);
    }

    @Test
    public void parseCommand_viewTodayCommandAlias_returnsViewTodayCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewTodayCommand.COMMAND_ALIAS) instanceof ViewTodayCommand);
        assertTrue(parser.parseCommand("caltoday 5") instanceof ViewTodayCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
