package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalOddEven.EVEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.BirthdaysCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemovePasswordCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TimetableUnionCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.VacantCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;
import seedu.address.model.building.Building;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AliasBuilder;
import seedu.address.testutil.AliasUtil;
import seedu.address.testutil.BuildingBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private static final int ARG_INDEX = 1;
    private static final int COMMAND_INDEX = 0;
    private static final String EMPTY_ARG = "";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        String[] input = parser.extractCommandArgs(PersonUtil.getAddCommand(person));
        AddCommand command = (AddCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new AddCommand(person), command);
    }

    //@@author jingyinno
    @Test
    public void parseCommand_alias() throws Exception {
        Alias alias = new AliasBuilder().build();
        String[] input = parser.extractCommandArgs(AliasUtil.getAliasCommand(alias));
        AliasCommand command = (AliasCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new AliasCommand(alias), command);
    }

    @Test
    public void parseCommand_unalias() throws Exception {
        Alias toUnalias = new AliasBuilder().build();
        String unalias = toUnalias.getAlias();
        String[] input = parser.extractCommandArgs(AliasUtil.getUnliasCommand(unalias));
        UnaliasCommand command = (UnaliasCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new UnaliasCommand(unalias), command);
    }

    @Test
    public void parseCommand_vacant() throws Exception {
        Building building = new BuildingBuilder().build();
        String vacant = VacantCommand.COMMAND_WORD + " " + building.getBuildingName();
        String[] input = parser.extractCommandArgs(vacant);
        VacantCommand command = (VacantCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new VacantCommand(building), command);
    }

    @Test
    public void parseCommand_map() throws Exception {
        String locations = "com1";
        String map = MapCommand.COMMAND_WORD + " " + locations;
        String[] input = parser.extractCommandArgs(map);
        MapCommand command = (MapCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new MapCommand(locations), command);
    }
    //@@author

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, EMPTY_ARG) instanceof ClearCommand);
        String clear = ClearCommand.COMMAND_WORD + " 3";
        String[] input = parser.extractCommandArgs(clear);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        String delete = DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        String[] input = parser.extractCommandArgs(delete);
        DeleteCommand command = (DeleteCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author yeggasd
    @Test
    public void parseCommand_password() throws Exception {
        String password = PasswordCommand.COMMAND_WORD + " test";
        String[] input = parser.extractCommandArgs(password);
        PasswordCommand command = (PasswordCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new PasswordCommand("test"), command);
    }

    @Test
    public void parseCommand_nopassword() throws Exception {
        String removePassword = RemovePasswordCommand.COMMAND_WORD + " 3";
        assertTrue(parser.parseCommand(RemovePasswordCommand.COMMAND_WORD, EMPTY_ARG) instanceof RemovePasswordCommand);
        String[] input = parser.extractCommandArgs(removePassword);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX])
                instanceof RemovePasswordCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        String importFile = ImportCommand.COMMAND_WORD + " /data/addressbook.xml test";
        String[] input = parser.extractCommandArgs(importFile);
        ImportCommand command = (ImportCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new ImportCommand("/data/addressbook.xml", "test"), command);
    }
    //@@author

    //@@author AzuraAiR
    @Test
    public void parseCommand_birthdays() throws Exception {
        BirthdaysCommand command = (BirthdaysCommand) parser.parseCommand(
                BirthdaysCommand.COMMAND_WORD, EMPTY_ARG);
        assertEquals(new BirthdaysCommand(false), command);
    }

    @Test
    public void parseCommand_birthdaysToday() throws Exception {
        String birthday = BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER;
        String[] input = parser.extractCommandArgs(birthday);
        BirthdaysCommand command = (BirthdaysCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new BirthdaysCommand(true), command);
    }

    @Test
    public void parseCommand_timeTableUnion() throws Exception {
        String timetableUnion = TimetableUnionCommand.COMMAND_WORD + " Odd 1 2";
        String[] input = parser.extractCommandArgs(timetableUnion);
        TimetableUnionCommand command = (TimetableUnionCommand) parser.parseCommand(input[
                COMMAND_INDEX], input[ARG_INDEX]);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        assertEquals(new TimetableUnionCommand(indexes, "Odd"), command);
    }
    //@@author

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        String edit = EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person);
        String[] input = parser.extractCommandArgs(edit);
        EditCommand command = (EditCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        String exit = ExitCommand.COMMAND_WORD + " 3";
        String[] input = parser.extractCommandArgs(exit);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, EMPTY_ARG) instanceof ExitCommand);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String find = FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" "));
        String[] input = parser.extractCommandArgs(find);
        FindCommand command = (FindCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, EMPTY_ARG) instanceof HelpCommand);
        String help = HelpCommand.COMMAND_WORD + " 3";
        String[] input = parser.extractCommandArgs(help);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD, EMPTY_ARG) instanceof HistoryCommand);
        String history = HistoryCommand.COMMAND_WORD + " 3";
        String[] input = parser.extractCommandArgs(history);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]) instanceof HistoryCommand);

        try {
            history = "histories";
            input = parser.extractCommandArgs(history);
            parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, EMPTY_ARG) instanceof ListCommand);
        String list = ListCommand.COMMAND_WORD + " 3";
        String[] input = parser.extractCommandArgs(list);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]) instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        String select = SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " " + EVEN;
        String[] input = parser.extractCommandArgs(select);
        SelectCommand command = (SelectCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON, EVEN), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD, EMPTY_ARG) instanceof RedoCommand);
        String redo = RedoCommand.COMMAND_WORD + " 1";
        String[] input = parser.extractCommandArgs(redo);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD, EMPTY_ARG) instanceof UndoCommand);
        String undo = UndoCommand.COMMAND_WORD + " 3";
        String[] input = parser.extractCommandArgs(undo);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        String invalid = EMPTY_ARG;
        String[] input = parser.extractCommandArgs(invalid);
        parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", EMPTY_ARG);
    }
}
