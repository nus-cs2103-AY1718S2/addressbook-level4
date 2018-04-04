package seedu.organizer.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.AddSubtaskCommand;
import seedu.organizer.logic.commands.ClearCommand;
import seedu.organizer.logic.commands.CurrentMonthCommand;
import seedu.organizer.logic.commands.DeleteCommand;
import seedu.organizer.logic.commands.DeleteSubtaskCommand;
import seedu.organizer.logic.commands.EditCommand;
import seedu.organizer.logic.commands.ExitCommand;
import seedu.organizer.logic.commands.FindDeadlineCommand;
import seedu.organizer.logic.commands.FindDescriptionCommand;
import seedu.organizer.logic.commands.FindMultipleFieldsCommand;
import seedu.organizer.logic.commands.FindNameCommand;
import seedu.organizer.logic.commands.HelpCommand;
import seedu.organizer.logic.commands.HistoryCommand;
import seedu.organizer.logic.commands.ListCommand;
import seedu.organizer.logic.commands.NextMonthCommand;
import seedu.organizer.logic.commands.PreviousMonthCommand;
import seedu.organizer.logic.commands.RedoCommand;
import seedu.organizer.logic.commands.SelectCommand;
import seedu.organizer.logic.commands.ToggleCommand;
import seedu.organizer.logic.commands.ToggleSubtaskCommand;
import seedu.organizer.logic.commands.UndoCommand;
import seedu.organizer.logic.commands.util.EditTaskDescriptor;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.DeadlineContainsKeywordsPredicate;
import seedu.organizer.model.task.DescriptionContainsKeywordsPredicate;
import seedu.organizer.model.task.MultipleFieldsContainsKeywordsPredicate;
import seedu.organizer.model.task.NameContainsKeywordsPredicate;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.testutil.EditTaskDescriptorBuilder;
import seedu.organizer.testutil.TaskBuilder;
import seedu.organizer.testutil.TaskUtil;

public class OrganizerParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final OrganizerParser parser = new OrganizerParser();

    @Before
    public void setUp() throws UserNotFoundException, CurrentlyLoggedInException {
        Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
        model.loginUser(ADMIN_USER);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Task task = new TaskBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(TaskUtil.getAddCommand(task));
        assertEquals(new AddCommand(task), command);

        AddCommand commandAlias = (AddCommand) parser.parseCommand(TaskUtil.getAddCommandAlias(task));
        assertEquals(new AddCommand(task), commandAlias);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        DeleteCommand commandAlias = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_TASK), command);
        assertEquals(new DeleteCommand(INDEX_FIRST_TASK), commandAlias);
    }

    @Test
    public void parseCommand_deleteSubtask() throws Exception {
        DeleteSubtaskCommand command = (DeleteSubtaskCommand) parser.parseCommand(
                DeleteSubtaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + " "
                        + INDEX_FIRST_TASK.getOneBased());
        DeleteSubtaskCommand commandAlias = (DeleteSubtaskCommand) parser.parseCommand(
                DeleteSubtaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased() + " "
                        + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteSubtaskCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK), command);
        assertEquals(new DeleteSubtaskCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK), commandAlias);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Task task = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(task).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getPersonDetails(task));
        EditCommand commandAlias = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getPersonDetails(task));
        assertEquals(new EditCommand(INDEX_FIRST_TASK, descriptor), command);
        assertEquals(new EditCommand(INDEX_FIRST_TASK, descriptor), commandAlias);
    }

    @Test
    public void parseCommand_addSubtask() throws Exception {
        Task task = new TaskBuilder().build();
        Subtask subtask = new Subtask(task.getName());
        AddSubtaskCommand command = (AddSubtaskCommand) parser.parseCommand(
                AddSubtaskCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getSubtaskDetails(task));
        AddSubtaskCommand commandAlias = (AddSubtaskCommand) parser.parseCommand(
                AddSubtaskCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getSubtaskDetails(task));
        assertEquals(new AddSubtaskCommand(INDEX_FIRST_TASK, subtask), command);
        assertEquals(new AddSubtaskCommand(INDEX_FIRST_TASK, subtask), commandAlias);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    //@@author guekling
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("Study", "es2660", "update");
        FindMultipleFieldsCommand command = (FindMultipleFieldsCommand) parser.parseCommand(
                FindMultipleFieldsCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        FindMultipleFieldsCommand commandAlias = (FindMultipleFieldsCommand) parser.parseCommand(
                FindMultipleFieldsCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(keywords)),
            commandAlias);
    }
    //@@author

    @Test
    public void parseCommand_findName() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
                FindNameCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        FindNameCommand commandAlias = (FindNameCommand) parser.parseCommand(
                FindNameCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), commandAlias);
    }

    //@@author guekling
    @Test
    public void parseCommand_findDescription() throws Exception {
        List<String> keywords = Arrays.asList("cs2103", "cs2101", "CS2010");
        FindDescriptionCommand command = (FindDescriptionCommand) parser.parseCommand(
            FindDescriptionCommand.COMMAND_WORD + " " + keywords.stream()
            .collect(Collectors.joining(" ")));
        FindDescriptionCommand commandAlias = (FindDescriptionCommand) parser.parseCommand(
            FindDescriptionCommand.COMMAND_ALIAS + " " + keywords.stream()
            .collect(Collectors.joining(" ")));
        assertEquals(new FindDescriptionCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindDescriptionCommand(new DescriptionContainsKeywordsPredicate(keywords)), commandAlias);
    }

    @Test
    public void parseCommand_findDeadline() throws Exception {
        List<String> keywords = Arrays.asList("2018-04-03", "2019-01-01", "2018-03-17");
        FindDeadlineCommand command = (FindDeadlineCommand) parser.parseCommand(
                FindDeadlineCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        FindDeadlineCommand commandAlias = (FindDeadlineCommand) parser.parseCommand(
                FindDeadlineCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(keywords)), commandAlias);
    }
    //@@author

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
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        SelectCommand commandAlias = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_TASK), command);
        assertEquals(new SelectCommand(INDEX_FIRST_TASK), commandAlias);
    }

    @Test
    public void parseCommand_toggle() throws Exception {
        ToggleCommand command = (ToggleCommand) parser.parseCommand(
                ToggleCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        ToggleCommand commandAlias = (ToggleCommand) parser.parseCommand(
                ToggleCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new ToggleCommand(INDEX_FIRST_TASK), command);
        assertEquals(new ToggleCommand(INDEX_FIRST_TASK), commandAlias);
    }

    @Test
    public void parseCommand_toggleSubtask() throws Exception {
        ToggleSubtaskCommand command = (ToggleSubtaskCommand) parser.parseCommand(
                ToggleSubtaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + " "
                        + INDEX_FIRST_TASK.getOneBased());
        ToggleSubtaskCommand commandAlias = (ToggleSubtaskCommand) parser.parseCommand(
                ToggleSubtaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased() + " "
                        + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new ToggleSubtaskCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK), command);
        assertEquals(new ToggleSubtaskCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK), commandAlias);
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

    //@@author guekling
    @Test
    public void parseCommand_previousMonthCommand() throws Exception {
        assertTrue(parser.parseCommand(PreviousMonthCommand.COMMAND_WORD) instanceof PreviousMonthCommand);
        assertTrue(parser.parseCommand(PreviousMonthCommand.COMMAND_WORD + " 3")
                instanceof PreviousMonthCommand);
        assertTrue(parser.parseCommand(PreviousMonthCommand.COMMAND_ALIAS) instanceof PreviousMonthCommand);
        assertTrue(parser.parseCommand(PreviousMonthCommand.COMMAND_ALIAS + " 3")
                instanceof PreviousMonthCommand);
    }

    @Test
    public void parseCommand_nextMonthCommand() throws Exception {
        assertTrue(parser.parseCommand(NextMonthCommand.COMMAND_WORD) instanceof NextMonthCommand);
        assertTrue(parser.parseCommand(NextMonthCommand.COMMAND_WORD + " 3")
                instanceof NextMonthCommand);
        assertTrue(parser.parseCommand(NextMonthCommand.COMMAND_ALIAS) instanceof NextMonthCommand);
        assertTrue(parser.parseCommand(NextMonthCommand.COMMAND_ALIAS + " 3")
                instanceof NextMonthCommand);
    }

    @Test
    public void parseCommand_currentMonthCommand() throws Exception {
        assertTrue(parser.parseCommand(CurrentMonthCommand.COMMAND_WORD) instanceof CurrentMonthCommand);
        assertTrue(parser.parseCommand(CurrentMonthCommand.COMMAND_WORD + " 3")
                instanceof CurrentMonthCommand);
        assertTrue(parser.parseCommand(CurrentMonthCommand.COMMAND_ALIAS) instanceof CurrentMonthCommand);
        assertTrue(parser.parseCommand(CurrentMonthCommand.COMMAND_ALIAS + " 3")
                instanceof CurrentMonthCommand);
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
