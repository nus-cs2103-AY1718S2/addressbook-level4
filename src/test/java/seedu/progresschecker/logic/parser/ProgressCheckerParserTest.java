package seedu.progresschecker.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.progresschecker.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.progresschecker.testutil.TypicalTabTypes.TYPE_EXERCISE;
import static seedu.progresschecker.testutil.TypicalTaskArgs.COMPULSORY;
import static seedu.progresschecker.testutil.TypicalTaskArgs.COM_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.FIRST_WEEK;
import static seedu.progresschecker.testutil.TypicalTaskArgs.FIRST_WEEK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_FIRST_TASK;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_FIRST_TASK_INT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.progresschecker.logic.commands.AddCommand;
import seedu.progresschecker.logic.commands.AddDefaultTasksCommand;
import seedu.progresschecker.logic.commands.ClearCommand;
import seedu.progresschecker.logic.commands.CompleteTaskCommand;
import seedu.progresschecker.logic.commands.DeleteCommand;
import seedu.progresschecker.logic.commands.EditCommand;
import seedu.progresschecker.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.progresschecker.logic.commands.ExitCommand;
import seedu.progresschecker.logic.commands.FindCommand;
import seedu.progresschecker.logic.commands.GoToTaskUrlCommand;
import seedu.progresschecker.logic.commands.HelpCommand;
import seedu.progresschecker.logic.commands.HistoryCommand;
import seedu.progresschecker.logic.commands.ListCommand;
import seedu.progresschecker.logic.commands.RedoCommand;
import seedu.progresschecker.logic.commands.ResetTaskCommand;
import seedu.progresschecker.logic.commands.SelectCommand;
import seedu.progresschecker.logic.commands.UndoCommand;
import seedu.progresschecker.logic.commands.ViewCommand;
import seedu.progresschecker.logic.commands.ViewTaskListCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;
import seedu.progresschecker.model.person.NameContainsKeywordsPredicate;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.testutil.EditPersonDescriptorBuilder;
import seedu.progresschecker.testutil.PersonBuilder;
import seedu.progresschecker.testutil.PersonUtil;

public class ProgressCheckerParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ProgressCheckerParser parser = new ProgressCheckerParser();

    //@@author EdwardKSG
    @Test
    public void parseCommand_addDefaultTasks() throws Exception {
        assertTrue(parser.parseCommand(AddDefaultTasksCommand.COMMAND_WORD) instanceof AddDefaultTasksCommand);
        assertTrue(parser.parseCommand(AddDefaultTasksCommand.COMMAND_WORD
                + " 3") instanceof AddDefaultTasksCommand);
        assertTrue(parser.parseCommand(AddDefaultTasksCommand.COMMAND_ALIAS) instanceof AddDefaultTasksCommand);
        assertTrue(parser.parseCommand(AddDefaultTasksCommand.COMMAND_ALIAS
                + " 3") instanceof AddDefaultTasksCommand);
    }

    @Test
    public void parseCommand_completeTask() throws Exception {
        CompleteTaskCommand command = (CompleteTaskCommand) parser.parseCommand(
                CompleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK);
        assertEquals(new CompleteTaskCommand(INDEX_FIRST_TASK_INT), command);
        CompleteTaskCommand command2 = (CompleteTaskCommand) parser.parseCommand(
                CompleteTaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK);
        assertEquals(new CompleteTaskCommand(INDEX_FIRST_TASK_INT), command2);
    }

    @Test
    public void parseCommand_resetTask() throws Exception {
        ResetTaskCommand command = (ResetTaskCommand) parser.parseCommand(
                ResetTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK);
        assertEquals(new ResetTaskCommand(INDEX_FIRST_TASK_INT), command);
        ResetTaskCommand command2 = (ResetTaskCommand) parser.parseCommand(
                ResetTaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK);
        assertEquals(new ResetTaskCommand(INDEX_FIRST_TASK_INT), command2);
    }

    @Test
    public void parseCommand_goToTaskUrl() throws Exception {
        GoToTaskUrlCommand command = (GoToTaskUrlCommand) parser.parseCommand(
                GoToTaskUrlCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK);
        assertEquals(new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT), command);
        GoToTaskUrlCommand command2 = (GoToTaskUrlCommand) parser.parseCommand(
                GoToTaskUrlCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK);
        assertEquals(new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT), command2);
    }

    @Test
    public void parseCommand_viewTaskList() throws Exception {
        ViewTaskListCommand command = (ViewTaskListCommand) parser.parseCommand(
                ViewTaskListCommand.COMMAND_WORD + " " + FIRST_WEEK);
        assertEquals(new ViewTaskListCommand(FIRST_WEEK_INT), command);
        ViewTaskListCommand command2 = (ViewTaskListCommand) parser.parseCommand(
                ViewTaskListCommand.COMMAND_ALIAS + " " + COMPULSORY);
        assertEquals(new ViewTaskListCommand(COM_INT), command2);
    }
    //@@author

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
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
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    //@@author iNekox3
    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + TYPE_EXERCISE + " 11 true");
        assertEquals(new ViewCommand(TYPE_EXERCISE, 11, true), command);
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
