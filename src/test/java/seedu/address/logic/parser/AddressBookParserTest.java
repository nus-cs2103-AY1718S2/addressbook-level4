package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.commands.AuthenCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.MyCalendarCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetPasswordCommand;
import seedu.address.logic.commands.TestAddEventCommand;
import seedu.address.logic.commands.TodoListCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FieldContainKeyphrasesPredicate;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

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
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> nameKeyphrases = Arrays.asList("foo", "bar", "baz");
        List<String> tagKeyphrases = Arrays.asList("friend", "family");
        List<String> ratingKeyphrases = Arrays.asList("5");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD
                        + " n/"
                        + nameKeyphrases.stream().collect(Collectors.joining(" n/"))
                        //@@author emer7
                        + " t/"
                        + tagKeyphrases.stream().collect(Collectors.joining(" t/"))
                        + " r/"
                        + ratingKeyphrases.stream().collect(Collectors.joining(" r/")));
        assertEquals(new FindCommand(
                new FieldContainKeyphrasesPredicate(
                        nameKeyphrases, tagKeyphrases, ratingKeyphrases)),
                        command);
        //@@author
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
    public void parseCommand_addEvent() throws Exception {
        assertTrue(parser.parseCommand(TestAddEventCommand.COMMAND_WORD
                + " 1 title/Project loca/NUS, Singapore stime/2017-03-19T08:00:00 "
                + "etime/2017-03-19T10:00:00 descrip/discuss")
                instanceof TestAddEventCommand);
    }

    @Test
    public void parseCommand_lock() throws Exception {
        assertTrue(parser.parseCommand(LockCommand.COMMAND_WORD) instanceof LockCommand);
    }

    @Test
    public void parseCommand_unlock() throws Exception {
        assertTrue(parser.parseCommand(UnlockCommand.COMMAND_WORD) instanceof UnlockCommand);
    }

    @Test
    public void parseCommand_setPasswrod() throws Exception {
        assertTrue(parser.parseCommand(SetPasswordCommand.COMMAND_WORD) instanceof SetPasswordCommand);
    }

    @Test
    public void parseCommand_todoList() throws Exception {
        assertTrue(parser.parseCommand(TodoListCommand.COMMAND_WORD) instanceof TodoListCommand);
    }

    @Test
    public void parseCommand_myCalendar() throws Exception {
        assertTrue(parser.parseCommand(MyCalendarCommand.COMMAND_WORD) instanceof MyCalendarCommand);
    }

    @Test
    public void parseCommand_addPhoto() throws Exception {
        AddPhotoCommand command = (AddPhotoCommand) parser.parseCommand(
                AddPhotoCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new AddPhotoCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_authen() throws Exception {
        assertTrue(parser.parseCommand(AuthenCommand.COMMAND_WORD) instanceof AuthenCommand);
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
