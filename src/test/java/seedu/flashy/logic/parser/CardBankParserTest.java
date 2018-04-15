package seedu.flashy.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.flashy.logic.commands.AddCardCommand;
import seedu.flashy.logic.commands.ClearCommand;
import seedu.flashy.logic.commands.DeleteCommand;
import seedu.flashy.logic.commands.EditCommand;
import seedu.flashy.logic.commands.EditCommand.EditTagDescriptor;
import seedu.flashy.logic.commands.ExitCommand;
import seedu.flashy.logic.commands.FindCommand;
import seedu.flashy.logic.commands.HelpCommand;
import seedu.flashy.logic.commands.HistoryCommand;
import seedu.flashy.logic.commands.ListCommand;
import seedu.flashy.logic.commands.RedoCommand;
import seedu.flashy.logic.commands.SelectCommand;
import seedu.flashy.logic.commands.UndoCommand;
import seedu.flashy.logic.parser.exceptions.ParseException;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.tag.NameContainsKeywordsPredicate;
import seedu.flashy.model.tag.Tag;
import seedu.flashy.testutil.CardBuilder;
import seedu.flashy.testutil.CardUtil;
import seedu.flashy.testutil.EditTagDescriptorBuilder;
import seedu.flashy.testutil.TagBuilder;
import seedu.flashy.testutil.TagUtil;

public class CardBankParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CardBankParser parser = new CardBankParser();

    @Test
    public void parseCommand_addCard() throws Exception {
        Card card = new CardBuilder().build();
        AddCardCommand command = (AddCardCommand) parser.parseCommand(CardUtil.getAddCardCommand(card));
        assertEquals(new AddCardCommand(card), command);

    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TAG.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_TAG), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Tag tag = new TagBuilder().build();
        EditTagDescriptor descriptor = new EditTagDescriptorBuilder(tag).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TAG.getOneBased() + " " + TagUtil.getTagDetails(tag));
        assertEquals(new EditCommand(INDEX_FIRST_TAG, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD_ALIAS + " 3") instanceof ExitCommand);
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
    public void parseCommand_listCard() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " "
                + ListCommandParser.PREFIX_NO_TAGS_ONLY) instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_TAG.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_TAG), command);
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
