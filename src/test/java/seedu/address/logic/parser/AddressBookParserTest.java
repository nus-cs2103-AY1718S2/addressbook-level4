package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddClientCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CloseCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.SearchContainsKeywordsPredicate;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.ClientUtil;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Client person = new ClientBuilder().build();
        AddClientCommand command = (AddClientCommand) parser.parseCommand(ClientUtil.getAddClientCommand(person));
        assertEquals(new AddClientCommand(person), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Client person = new ClientBuilder().build();
        AddClientCommand command = (AddClientCommand) parser.parseCommand(AddClientCommand.COMMAND_ALIAS + " "
                + ClientUtil.getClientDetails(person));
        assertEquals(new AddClientCommand(person), command);
    }

    @Test
    public void parseCommand_addClient() throws Exception {
        Client client = new ClientBuilder().build();
        AddClientCommand command = (AddClientCommand) parser.parseCommand(ClientUtil.getAddClientCommand(client));
        assertEquals(new AddClientCommand(client), command);
    }

    @Test
    public void parseCommand_addClientAlias() throws Exception {
        Client client = new ClientBuilder().build();
        AddClientCommand command = (AddClientCommand) parser.parseCommand(AddClientCommand.COMMAND_ALIAS + " "
                + ClientUtil.getClientDetails(client));
        assertEquals(new AddClientCommand(client), command);
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
    public void parseCommand_match() throws Exception {
        MatchCommand command = (MatchCommand) parser.parseCommand(
                MatchCommand.COMMAND_WORD + " 3" + " c/s");
        assertEquals(new MatchCommand(INDEX_THIRD_PERSON, new Category("s")), command);
    }

    @Test
    public void parseCommand_matchAlias() throws Exception {
        MatchCommand command = (MatchCommand) parser.parseCommand(
                MatchCommand.COMMAND_ALIAS + " 3" + " c/s");
        assertEquals(new MatchCommand(INDEX_THIRD_PERSON, new Category("s")), command);
    }

    @Test
    public void parseCommand_restore() throws Exception {
        RestoreCommand command = (RestoreCommand) parser.parseCommand(
                RestoreCommand.COMMAND_WORD + " 3" + " c/s");
        assertEquals(new RestoreCommand(INDEX_THIRD_PERSON, new Category("s")), command);
    }

    @Test
    public void parseCommand_restoreAlias() throws Exception {
        RestoreCommand command = (RestoreCommand) parser.parseCommand(
                RestoreCommand.COMMAND_ALIAS + " 3" + " c/s");
        assertEquals(new RestoreCommand(INDEX_THIRD_PERSON, new Category("s")), command);
    }

    @Test
    public void parseCommand_close() throws Exception {
        CloseCommand command = (CloseCommand) parser.parseCommand(
                CloseCommand.COMMAND_WORD + " 3" + " c/s");
        assertEquals(new CloseCommand(INDEX_THIRD_PERSON, new Category("s")), command);
    }

    @Test
    public void parseCommand_closeAlias() throws Exception {
        CloseCommand command = (CloseCommand) parser.parseCommand(
                CloseCommand.COMMAND_ALIAS + " 3" + " c/s");
        assertEquals(new CloseCommand(INDEX_THIRD_PERSON, new Category("s")), command);
    }

    @Test
    public void parseCommand_switch() throws Exception {
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_WORD) instanceof SwitchCommand);
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_WORD + " 3") instanceof SwitchCommand);
    }

    @Test
    public void parseCommand_switchAlias() throws Exception {
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_ALIAS) instanceof SwitchCommand);
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_ALIAS + " 3") instanceof SwitchCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " c/s");
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON, new Category("s")), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + " c/s");
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON, new Category("s")), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Client person = new ClientBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + ClientUtil.getClientDetails(person) + " c/s");
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor, new Category("s")), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Client person = new ClientBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + ClientUtil.getClientDetails(person) + " c/s");
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor, new Category("s")), command);
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
        assertEquals(new FindCommand(new SearchContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new SearchContainsKeywordsPredicate(keywords)), command);
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
