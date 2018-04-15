package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.testutil.TypicalTargets.INDEX_FIRST_COIN;
import static seedu.address.testutil.TypicalTargets.TARGET_FIRST_COIN;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListNotifsCommand;
import seedu.address.logic.commands.NotifyCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TagCommand.EditCoinDescriptor;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.CoinBuilder;
import seedu.address.testutil.CoinUtil;
import seedu.address.testutil.EditCoinDescriptorBuilder;

public class CoinBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CoinBookParser parser = new CoinBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Coin coin = new CoinBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(CoinUtil.getAddCommand(coin));
        assertEquals(new AddCommand(coin), command);
        AddCommand aliasedCommand = (AddCommand) parser.parseCommand(CoinUtil.getAddCommand(coin));
        assertEquals(new AddCommand(coin), aliasedCommand);
    }

    @Test
    public void parseCommand_buy() throws Exception {
        Coin coin = new CoinBuilder().build();
        BuyCommand command = (BuyCommand) parser.parseCommand(BuyCommand.COMMAND_WORD + " "
                + INDEX_FIRST_COIN.getOneBased() + PREFIX_AMOUNT  + " 50.0");
        assertEquals(new BuyCommand(new CommandTarget(INDEX_FIRST_COIN), new Amount("50.0")), command);
        command = (BuyCommand) parser.parseCommand(BuyCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_COIN.getOneBased() + PREFIX_AMOUNT  + " 50.0");
        assertEquals(new BuyCommand(new CommandTarget(INDEX_FIRST_COIN), new Amount("50.0")), command);
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
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_COIN.getOneBased());
        assertEquals(new DeleteCommand(TARGET_FIRST_COIN), command);
        DeleteCommand aliasedCommand = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_COIN.getOneBased());
        assertEquals(new DeleteCommand(TARGET_FIRST_COIN), aliasedCommand);
    }

    @Test
    public void parseCommand_tag() throws Exception {
        Coin coin = new CoinBuilder().build();
        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder(coin).build();
        TagCommand command = (TagCommand) parser.parseCommand(TagCommand.COMMAND_WORD + " "
                + INDEX_FIRST_COIN.getOneBased() + " " + CoinUtil.getCoinTags(coin));
        assertEquals(new TagCommand(new CommandTarget(INDEX_FIRST_COIN), descriptor), command);
        TagCommand aliasedCommand = (TagCommand) parser.parseCommand(TagCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_COIN.getOneBased() + " " + CoinUtil.getCoinTags(coin));
        assertEquals(new TagCommand(new CommandTarget(INDEX_FIRST_COIN), descriptor), aliasedCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_notify() throws Exception {
        assertTrue(parser.parseCommand(
                NotifyCommand.COMMAND_WORD + " c/TEST AND p/>100") instanceof NotifyCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
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

        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listNotifs() throws Exception {
        assertTrue(parser.parseCommand(ListNotifsCommand.COMMAND_WORD) instanceof ListNotifsCommand);
        assertTrue(parser.parseCommand(ListNotifsCommand.COMMAND_ALIAS) instanceof ListNotifsCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_COIN.getOneBased());
        assertEquals(new ViewCommand(TARGET_FIRST_COIN), command);
        ViewCommand aliasedCommand = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_ALIAS + " " + INDEX_FIRST_COIN.getOneBased());
        assertEquals(new ViewCommand(TARGET_FIRST_COIN), aliasedCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("y 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("z 3") instanceof UndoCommand);
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
