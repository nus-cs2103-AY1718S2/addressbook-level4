package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CreateCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.KeyCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TogglePrivacyCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    private static final boolean DEFAULT_LOCK_STATE = false;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person), DEFAULT_LOCK_STATE);
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS + " "
                + PersonUtil.getPersonDetails(person), DEFAULT_LOCK_STATE);
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, DEFAULT_LOCK_STATE) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", DEFAULT_LOCK_STATE) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS, DEFAULT_LOCK_STATE) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3", DEFAULT_LOCK_STATE) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_LOCK_STATE);
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_LOCK_STATE);
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person), DEFAULT_LOCK_STATE);
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person), DEFAULT_LOCK_STATE);
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, DEFAULT_LOCK_STATE) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", DEFAULT_LOCK_STATE) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("foobar");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + remark, DEFAULT_LOCK_STATE);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_remarkAlias() throws Exception {
        final Remark remark = new Remark("foobar");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + remark, DEFAULT_LOCK_STATE);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")), DEFAULT_LOCK_STATE);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")), DEFAULT_LOCK_STATE);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, DEFAULT_LOCK_STATE) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3",
                DEFAULT_LOCK_STATE) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD, DEFAULT_LOCK_STATE) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3",
                DEFAULT_LOCK_STATE) instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", DEFAULT_LOCK_STATE);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS, DEFAULT_LOCK_STATE) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3",
                DEFAULT_LOCK_STATE) instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_create() throws Exception {
        Team team = new Team(new TeamName(VALID_TEAM_ARSENAL));
        CreateCommand command = (CreateCommand) parser.parseCommand(CreateCommand.COMMAND_WORD + " "
            + VALID_TEAM_ARSENAL, DEFAULT_LOCK_STATE);
        assertEquals(new CreateCommand(team), command);
    }

    @Test
    public void parseCommand_createAlias() throws Exception {
        Team team = new Team(new TeamName(VALID_TEAM_ARSENAL));
        CreateCommand command = (CreateCommand) parser.parseCommand(CreateCommand.COMMAND_ALIAS + " "
                + VALID_TEAM_ARSENAL, DEFAULT_LOCK_STATE);
        assertEquals(new CreateCommand(team), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, DEFAULT_LOCK_STATE) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3",
                DEFAULT_LOCK_STATE) instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS, DEFAULT_LOCK_STATE) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3",
                DEFAULT_LOCK_STATE) instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_LOCK_STATE);
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_LOCK_STATE);
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD, DEFAULT_LOCK_STATE) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1", DEFAULT_LOCK_STATE) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS, DEFAULT_LOCK_STATE) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1",
                DEFAULT_LOCK_STATE) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD, DEFAULT_LOCK_STATE) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3", DEFAULT_LOCK_STATE) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS, DEFAULT_LOCK_STATE) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3",
                DEFAULT_LOCK_STATE) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("", DEFAULT_LOCK_STATE);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", DEFAULT_LOCK_STATE);
    }

    /** @lohtianwei */
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " "
                + SortCommandParser.ACCEPTED_FIELDS.get(0) + " "
                + SortCommandParser.ACCEPTED_ORDERS.get(0), DEFAULT_LOCK_STATE) instanceof SortCommand);
    }

    @Test
    public void parseCommand_sortAlias() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_ALIAS + " "
                + SortCommandParser.ACCEPTED_FIELDS.get(0) + " "
                + SortCommandParser.ACCEPTED_ORDERS.get(0), DEFAULT_LOCK_STATE) instanceof SortCommand);
    }

    @Test
    public void parseCommand_key() throws Exception {
        assertTrue(parser.parseCommand(KeyCommand.COMMAND_WORD
                + " ilikesports", DEFAULT_LOCK_STATE) instanceof KeyCommand);
    }

    @Test
    public void parseCommand_keyAlias() throws Exception {
        assertTrue(parser.parseCommand(KeyCommand.COMMAND_WORD
                + " ilikesports", DEFAULT_LOCK_STATE) instanceof KeyCommand);
    }

    @Test
    public void parseCommand_togglePrivacy() throws Exception {
        assertTrue(parser.parseCommand(TogglePrivacyCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_PHONE, DEFAULT_LOCK_STATE) instanceof TogglePrivacyCommand);
    }

    @Test
    public void parseCommand_togglePrivacyAlias() throws Exception {
        assertTrue(parser.parseCommand(TogglePrivacyCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_PHONE, DEFAULT_LOCK_STATE) instanceof TogglePrivacyCommand);
    }

    /** @@Codee */
    @Test
    public void parseCommand_theme() throws Exception {
        String[] listThemes = { "Light", "Dark" };

        for (int i = 0; i < 2; i++) {
            ChangeThemeCommand command = (ChangeThemeCommand) parser.parseCommand(
                    ChangeThemeCommand.COMMAND_WORD + " " + listThemes[i], DEFAULT_LOCK_STATE);
            assertEquals(new ChangeThemeCommand(listThemes[i]), command);
        }
    }
    //@@author
}
