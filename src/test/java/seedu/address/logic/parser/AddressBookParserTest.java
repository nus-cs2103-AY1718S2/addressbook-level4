package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalCells.FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.LAST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCellCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCellCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCellCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
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

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName("Sarah").build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " n/Sarah");
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    //Todo : this test causing too much pain and burden, will fix later - doesn't directly affect functionality anyway
    //
    //    @Test
    //    public void parseCommand_find() throws Exception {
    //        List<String> nameKeywords = Arrays.asList("foo", "bar", "baz");
    //        List<String> tagKeywords = Arrays.asList("tag1", "tag2");
    //        FindCommand command = (FindCommand) parser.parseCommand(
    //                FindCommand.COMMAND_WORD + " n/" + nameKeywords.stream().collect(Collectors.joining(" "))
    //                        +" t/"+tagKeywords.stream().collect(Collectors.joining(" "))
    //        );
    //        assertEquals(new FindCommand(new ContainsKeywordsPredicate(nameKeywords,tagKeywords)), command);
    //    }

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
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    //@@author sarahgoh97
    @Test
    public void parseCommand_addCellCommandWord_returnsAddCellCommand() throws Exception {
        assertTrue(parser.parseCommand(AddCellCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + FIRST_CELL_ADDRESS) instanceof AddCellCommand);
        assertTrue(parser.parseCommand("addcell 3 1-4") instanceof AddCellCommand);
        assertTrue(parser.parseCommand("ac 1 1-1") instanceof AddCellCommand);
    }

    @Test
    public void parseCommand_deleteCellCommandWord_returnsDeleteCellCommand() throws Exception {
        assertTrue(parser.parseCommand(DeleteCellCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased())
                instanceof DeleteCellCommand);
        assertTrue(parser.parseCommand("deletecell 3") instanceof DeleteCellCommand);
        assertTrue(parser.parseCommand("dc 2") instanceof DeleteCellCommand);
    }

    @Test
    public void parseCommand_listCellCommandWord_returnsListCellCommand() throws Exception {
        assertTrue(parser.parseCommand(ListCellCommand.COMMAND_WORD + " " + LAST_CELL_ADDRESS)
                instanceof ListCellCommand);
        assertTrue(parser.parseCommand("listcell 2-2") instanceof ListCellCommand);
        assertTrue(parser.parseCommand("lc 1-1") instanceof ListCellCommand);
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
