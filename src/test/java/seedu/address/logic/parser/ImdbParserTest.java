package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPatientQueueCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.PrintCommand;
import seedu.address.logic.commands.RecordCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemovePatientQueueCommand;
import seedu.address.logic.commands.RemoveRecordCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewAppointmentCommand;

import seedu.address.logic.login.LoginManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PatientUtil;

public class ImdbParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ImdbParser parser = new ImdbParser();

    @Test
    public void parseCommand_add() throws Exception {
        LoginManager.authenticate("alice", "password123");
        Patient patient = new PatientBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PatientUtil.getAddCommand(patient));
        assertEquals(new AddCommand(patient), command);
    }

    @Test
    public void parseCommand_addCommandAlias() throws Exception {
        LoginManager.authenticate("alice", "password123");
        Patient patient = new PatientBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PatientUtil.getAddCommandAlias(patient));
        assertEquals(new AddCommand(patient), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearCommandAlias() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        LoginManager.authenticate("alice", "password123");
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteCommandAlias1() throws Exception {
        LoginManager.authenticate("alice", "password123");
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS1 + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteCommandAlias2() throws Exception {
        LoginManager.authenticate("alice", "password123");
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS2 + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        LoginManager.authenticate("alice", "password123");
        Patient patient = new PatientBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(patient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PatientUtil.getPersonDetails(patient));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editCommandAlias() throws Exception {
        LoginManager.authenticate("alice", "password123");
        Patient patient = new PatientBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(patient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PatientUtil.getPersonDetails(patient));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitCommandAlias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        LoginManager.authenticate("alice", "password123");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findCommandAlias() throws Exception {
        LoginManager.authenticate("alice", "password123");
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
    public void parseCommand_helpCommandAlias() throws Exception {
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
    public void parseCommand_historyCommandAlias() throws Exception {
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
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listCommandAlias() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_loginCommandWord_returnsLoginCommand() throws Exception {
        LoginManager.logout();
        assertTrue(parser.parseCommand(LoginCommand.COMMAND_WORD) instanceof LoginCommand);
    }

    @Test
    public void parseCommand_loginCommandAlias_returnsLoginCommand() throws Exception {
        LoginManager.logout();
        assertTrue(parser.parseCommand(LoginCommand.COMMAND_ALIAS) instanceof LoginCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        LoginManager.authenticate("alice", "password123");
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_selectCommandAlias() throws Exception {
        LoginManager.authenticate("alice", "password123");
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand1() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS1) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand2() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS2) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand1() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS1) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand2() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS2) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void remarkCommand_returnsRemarkCommand() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + "r/ Is very clever") instanceof RemarkCommand);
    }

    @Test
    public void parseCommand_viewAppointment() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ViewAppointmentCommand command = (ViewAppointmentCommand) parser.parseCommand(
                ViewAppointmentCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new ViewAppointmentCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void record_returnsRecordCommand() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(RecordCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_INDEX + "1") instanceof RecordCommand);
    }

    @Test
    public void recordAlias_returnsRecordCommand() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(RecordCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_INDEX + "1") instanceof RecordCommand);
    }

    @Test
    public void removeRecord_returnsRemoveRecordCommand() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(RemoveRecordCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_INDEX + "1") instanceof RemoveRecordCommand);
    }

    @Test
    public void removeRecordAlias_returnsRecordCommand() throws Exception {
        LoginManager.authenticate("alice", "password123");
        assertTrue(parser.parseCommand(RemoveRecordCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_INDEX + "1") instanceof RemoveRecordCommand);
    }

    @Test
    public void parseCommand_viewAppointmentCommandAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ViewAppointmentCommand command = (ViewAppointmentCommand) parser.parseCommand(
                ViewAppointmentCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new ViewAppointmentCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_deleteAppointment() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz", "1");
        DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
                DeleteAppointmentCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new DeleteAppointmentCommand(new NameContainsKeywordsPredicate(keywords),
                ParserUtil.parseIndex("1")), command);
    }

    @Test
    public void parseCommand_deleteAppointmentCommandAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz", "1");
        DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
                DeleteAppointmentCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new DeleteAppointmentCommand(new NameContainsKeywordsPredicate(keywords),
                ParserUtil.parseIndex("1")), command);
    }

    @Test
    public void parseCommand_addPatientQueue() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        AddPatientQueueCommand command = (AddPatientQueueCommand) parser.parseCommand(
                AddPatientQueueCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new AddPatientQueueCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_addPatientQueueAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        AddPatientQueueCommand command = (AddPatientQueueCommand) parser.parseCommand(
                AddPatientQueueCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new AddPatientQueueCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_removePatientQueue() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        RemovePatientQueueCommand command = (RemovePatientQueueCommand) parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_WORD);
        assertEquals(new RemovePatientQueueCommand(), command);
    }

    @Test
    public void parseCommand_removePatientQueueAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        RemovePatientQueueCommand command = (RemovePatientQueueCommand) parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_ALIAS);
        assertEquals(new RemovePatientQueueCommand(), command);
    }

    @Test
    public void parseCommand_printCommand() throws Exception {
        LoginManager.authenticate("bob", "password456");
        PrintCommand command = (PrintCommand) parser.parseCommand(
                PrintCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PrintCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_printCommandAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        PrintCommand command = (PrintCommand) parser.parseCommand(
                PrintCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PrintCommand(INDEX_FIRST_PERSON), command);
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
