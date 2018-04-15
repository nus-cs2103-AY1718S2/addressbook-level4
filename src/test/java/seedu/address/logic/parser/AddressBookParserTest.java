package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.MESSAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PURPOSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PURPOSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.createDate;
import static seedu.address.testutil.TypicalDates.DATE_FIRST_JAN;
import static seedu.address.testutil.TypicalDates.VALID_DATE_DESC;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalTags.TAG_FRIEND;
import static seedu.address.testutil.TypicalTags.VALID_TAG_DESC_FRIEND;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTemplateCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteBeforeCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTemplateCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GoBackwardCommand;
import seedu.address.logic.commands.GoForwardCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ZoomInCommand;
import seedu.address.logic.commands.ZoomOutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.email.Template;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.AppointmentUtil;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TemplateBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().withDateAdded(createDate()).build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Person person = new PersonBuilder().withDateAdded(createDate()).build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddAlias(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author jlks96
    @Test
    public void parseCommand_deleteBefore() throws Exception {
        DeleteBeforeCommand command = (DeleteBeforeCommand) parser.parseCommand(
                DeleteBeforeCommand.COMMAND_WORD + VALID_DATE_DESC + VALID_TAG_DESC_FRIEND);
        assertEquals(new DeleteBeforeCommand(DATE_FIRST_JAN, new HashSet<>(Arrays.asList(TAG_FRIEND))), command);
    }

    @Test
    public void parseCommand_deleteBeforeAlias() throws Exception {
        DeleteBeforeCommand command = (DeleteBeforeCommand) parser.parseCommand(
                DeleteBeforeCommand.COMMAND_ALIAS + VALID_DATE_DESC + VALID_TAG_DESC_FRIEND);
        assertEquals(new DeleteBeforeCommand(DATE_FIRST_JAN, new HashSet<>(Arrays.asList(TAG_FRIEND))), command);
    }

    @Test
    public void parseCommand_addAppointment() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        AddAppointmentCommand command =
                (AddAppointmentCommand) parser.parseCommand(AppointmentUtil.getAddAppointmentCommand(appointment));
        assertEquals(new AddAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_addAppointmentAlias() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        AddAppointmentCommand command =
                (AddAppointmentCommand) parser.parseCommand(AppointmentUtil.getAddAppointmentAlias(appointment));
        assertEquals(new AddAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_deleteAppointment() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand command =
                (DeleteAppointmentCommand) parser.parseCommand(
                        AppointmentUtil.getDeleteAppointmentCommand(appointment));
        assertEquals(new DeleteAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_deleteAppointmentAlias() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand command =
                (DeleteAppointmentCommand) parser.parseCommand(AppointmentUtil.getDeleteAppointmentAlias(appointment));
        assertEquals(new DeleteAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_zoomIn() throws Exception {
        assertTrue(parser.parseCommand(ZoomInCommand.COMMAND_WORD) instanceof ZoomInCommand);
        assertTrue(parser.parseCommand(ZoomInCommand.COMMAND_WORD + " 3") instanceof ZoomInCommand);
    }

    @Test
    public void parseCommand_zoomInAlias() throws Exception {
        assertTrue(parser.parseCommand(ZoomInCommand.COMMAND_ALIAS) instanceof ZoomInCommand);
        assertTrue(parser.parseCommand(ZoomInCommand.COMMAND_ALIAS + " 3") instanceof ZoomInCommand);
    }

    @Test
    public void parseCommand_zoomOut() throws Exception {
        assertTrue(parser.parseCommand(ZoomOutCommand.COMMAND_WORD) instanceof ZoomOutCommand);
        assertTrue(parser.parseCommand(ZoomOutCommand.COMMAND_WORD + " 3") instanceof ZoomOutCommand);
    }

    @Test
    public void parseCommand_zoomOutAlias() throws Exception {
        assertTrue(parser.parseCommand(ZoomOutCommand.COMMAND_ALIAS) instanceof ZoomOutCommand);
        assertTrue(parser.parseCommand(ZoomOutCommand.COMMAND_ALIAS + " 3") instanceof ZoomOutCommand);
    }

    @Test
    public void parseCommand_goForward() throws Exception {
        assertTrue(parser.parseCommand(GoForwardCommand.COMMAND_WORD) instanceof GoForwardCommand);
        assertTrue(parser.parseCommand(GoForwardCommand.COMMAND_WORD + " 3") instanceof GoForwardCommand);
    }

    @Test
    public void parseCommand_goForwardAlias() throws Exception {
        assertTrue(parser.parseCommand(GoForwardCommand.COMMAND_ALIAS) instanceof GoForwardCommand);
        assertTrue(parser.parseCommand(GoForwardCommand.COMMAND_ALIAS + " 3") instanceof GoForwardCommand);
    }

    @Test
    public void parseCommand_goBackward() throws Exception {
        assertTrue(parser.parseCommand(GoBackwardCommand.COMMAND_WORD) instanceof GoBackwardCommand);
        assertTrue(parser.parseCommand(GoBackwardCommand.COMMAND_WORD + " 3") instanceof GoBackwardCommand);
    }

    @Test
    public void parseCommand_goBackwardAlias() throws Exception {
        assertTrue(parser.parseCommand(GoBackwardCommand.COMMAND_ALIAS) instanceof GoBackwardCommand);
        assertTrue(parser.parseCommand(GoBackwardCommand.COMMAND_ALIAS + " 3") instanceof GoBackwardCommand);
    }
    //@@author

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitAlias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }


    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpAlias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
    }

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

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }
    //@@author ng95junwei
    @Test
    public void parseCommand_addTemplate() throws Exception {
        List<String> userInput = Arrays.asList(AddTemplateCommand.COMMAND_WORD,
                PURPOSE_DESC, MESSAGE_DESC, SUBJECT_DESC);
        Template templateToAdd = new TemplateBuilder().withPurpose(VALID_PURPOSE).withSubject(VALID_SUBJECT)
                .withMessage(VALID_MESSAGE).build();
        AddTemplateCommand command = new AddTemplateCommand(templateToAdd);
        AddTemplateCommand parsedCommand = (AddTemplateCommand) parser.parseCommand(userInput.stream()
                .collect(Collectors.joining(" ")));
        assertEquals(command, parsedCommand);
    }

    @Test
    public void parseCommand_addTemplateAlias() throws Exception {
        List<String> userInput = Arrays.asList(AddTemplateCommand.COMMAND_ALIAS,
                PURPOSE_DESC, MESSAGE_DESC, SUBJECT_DESC);
        Template templateToAdd = new TemplateBuilder().withPurpose(VALID_PURPOSE).withSubject(VALID_SUBJECT)
                .withMessage(VALID_MESSAGE).build();
        AddTemplateCommand command = new AddTemplateCommand(templateToAdd);
        AddTemplateCommand parsedCommand = (AddTemplateCommand) parser.parseCommand(userInput.stream()
                .collect(Collectors.joining(" ")));
        assertEquals(command, parsedCommand);
    }

    @Test
    public void parseCommand_deleteTemplate() throws Exception {
        DeleteTemplateCommand command = new DeleteTemplateCommand(VALID_PURPOSE);
        DeleteTemplateCommand parsedCommand =
                (DeleteTemplateCommand) parser.parseCommand(
                        DeleteTemplateCommand.COMMAND_WORD + PURPOSE_DESC);
        assertEquals(command, parsedCommand);
    }

    @Test
    public void parseCommand_deleteTemplateAlias() throws Exception {
        DeleteTemplateCommand command = new DeleteTemplateCommand(VALID_PURPOSE);
        DeleteTemplateCommand parsedCommand =
                (DeleteTemplateCommand) parser.parseCommand(
                        DeleteTemplateCommand.COMMAND_ALIAS + PURPOSE_DESC);
        assertEquals(command, parsedCommand);
    }

    @Test
    public void parseCommand_email() throws Exception {
        String[] nameKeywordArray = new String[]{ VALID_NAME_BOB };
        String userInput = EmailCommand.COMMAND_WORD + NAME_DESC_BOB + PURPOSE_DESC;
        EmailCommand command = (EmailCommand) parser.parseCommand(userInput);
        assertEquals(new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywordArray)),
                VALID_PURPOSE), command);
    }

    @Test
    public void parseCommand_emailAlias() throws Exception {
        String[] nameKeywordArray = new String[]{ VALID_NAME_BOB };
        String userInput = EmailCommand.COMMAND_ALIAS + NAME_DESC_BOB + PURPOSE_DESC;
        EmailCommand command = (EmailCommand) parser.parseCommand(userInput);
        assertEquals(new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywordArray)),
                VALID_PURPOSE), command);
    }

    //@@author
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
