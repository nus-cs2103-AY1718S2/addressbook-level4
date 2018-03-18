package seedu.address.logic.parser;

import org.junit.Test;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AliasCommand.MESSAGE_INVALID_COMMAND;
import static seedu.address.logic.commands.AliasCommand.MESSAGE_INVALID_COMMAND_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseAliasWord;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.alias.Alias.MESSAGE_ALIAS_CONSTRAINTS;

import seedu.address.logic.commands.*;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;
import seedu.address.testutil.AliasBuilder;

public class AliasCommandParserTest {
    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_addAlias_success() {
        Alias expectedAddAlias = new AliasBuilder().withCommand(AddCommand.COMMAND_WORD).withAlias(VALID_ALIAS_ADD).build();

        assertParseSuccess(parser, ALIAS_DESC_ADD, new AliasCommand(expectedAddAlias));
    }

    @Test
    public void parse_aliasAlias_success() {
        Alias expectedAliasAlias = new AliasBuilder().withCommand(AliasCommand.COMMAND_WORD).withAlias(VALID_ALIAS_ALIAS).build();

        assertParseSuccess(parser, ALIAS_DESC_ALIAS, new AliasCommand(expectedAliasAlias));
    }

    @Test
    public void parse_clearAlias_success() {
        Alias expectedClearAlias = new AliasBuilder().withCommand(ClearCommand.COMMAND_WORD).withAlias(VALID_ALIAS_CLEAR).build();

        assertParseSuccess(parser, ALIAS_DESC_CLEAR, new AliasCommand(expectedClearAlias));
    }

    @Test
    public void parse_deleteAlias_success() {
        Alias expectedDeleteAlias = new AliasBuilder().withCommand(DeleteCommand.COMMAND_WORD).withAlias(VALID_ALIAS_DELETE).build();

        assertParseSuccess(parser, ALIAS_DESC_DELETE, new AliasCommand(expectedDeleteAlias));
    }

    @Test
    public void parse_editAlias_success() {
        Alias expectedEditAlias = new AliasBuilder().withCommand(EditCommand.COMMAND_WORD).withAlias(VALID_ALIAS_EDIT).build();

        assertParseSuccess(parser, ALIAS_DESC_EDIT, new AliasCommand(expectedEditAlias));
    }

    @Test
    public void parse_exitAlias_success() {
        Alias expectedExitAlias = new AliasBuilder().withCommand(ExitCommand.COMMAND_WORD).withAlias(VALID_ALIAS_EXIT).build();

        assertParseSuccess(parser, ALIAS_DESC_EXIT, new AliasCommand(expectedExitAlias));
    }

    @Test
    public void parse_findAlias_success() {
        Alias expectedFindAlias = new AliasBuilder().withCommand(FindCommand.COMMAND_WORD).withAlias(VALID_ALIAS_FIND).build();

        assertParseSuccess(parser, ALIAS_DESC_FIND, new AliasCommand(expectedFindAlias));
    }

    @Test
    public void parse_helpAlias_success() {
        Alias expectedHelpAlias = new AliasBuilder().withCommand(HelpCommand.COMMAND_WORD).withAlias(VALID_ALIAS_HELP).build();

        assertParseSuccess(parser, ALIAS_DESC_HELP, new AliasCommand(expectedHelpAlias));
    }

    @Test
    public void parse_historyAlias_success() {
        Alias expectedHistoryAlias = new AliasBuilder().withCommand(HistoryCommand.COMMAND_WORD).withAlias(VALID_ALIAS_HISTORY).build();

        assertParseSuccess(parser, ALIAS_DESC_HISTORY, new AliasCommand(expectedHistoryAlias));
    }

    @Test
    public void parse_importAlias_success() {
        Alias expectedImportAlias = new AliasBuilder().withCommand(ImportCommand.COMMAND_WORD).withAlias(VALID_ALIAS_IMPORT).build();

        assertParseSuccess(parser, ALIAS_DESC_IMPORT, new AliasCommand(expectedImportAlias));
    }

    @Test
    public void parse_listAlias_success() {
        Alias expectedListAlias = new AliasBuilder().withCommand(ListCommand.COMMAND_WORD).withAlias(VALID_ALIAS_LIST).build();

        assertParseSuccess(parser, ALIAS_DESC_LIST, new AliasCommand(expectedListAlias));
    }

    @Test
    public void parse_redoAlias_success() {
        Alias expectedRedoAlias = new AliasBuilder().withCommand(RedoCommand.COMMAND_WORD).withAlias(VALID_ALIAS_REDO).build();

        assertParseSuccess(parser, ALIAS_DESC_REDO, new AliasCommand(expectedRedoAlias));
    }

    @Test
    public void parse_selectAlias_success() {
        Alias expectedSelectAlias = new AliasBuilder().withCommand(SelectCommand.COMMAND_WORD).withAlias(VALID_ALIAS_SELECT).build();

        assertParseSuccess(parser, ALIAS_DESC_SELECT, new AliasCommand(expectedSelectAlias));
    }

    @Test
    public void parse_undoAlias_success() {
        Alias expectedUndoAlias = new AliasBuilder().withCommand(UndoCommand.COMMAND_WORD).withAlias(VALID_ALIAS_UNDO).build();

        assertParseSuccess(parser, ALIAS_DESC_UNDO, new AliasCommand(expectedUndoAlias));
    }

    @Test
    public void parse_AliasWordAlias_failure() {
        //test alias word to be a command word failure
        assertParseAliasWord(parser, parser.getCommands());
    }

//    @Test
//    public void parse_AliasFormatAlias_failure() {
//        //test invalid alias word format with valid command word failure
//        Alias alias = new AliasBuilder().withCommand(AddCommand.COMMAND_WORD).withAlias(INVALID_ALIAS_DESC).build();
//        Command command = new AliasCommand(alias);
//        assertCommandFailure(command, (Model) alias, MESSAGE_ALIAS_CONSTRAINTS);
//    }

    @Test
    public void parse_commandWordAlias_failure() {
        //test invalid command word with valid alias word failure
        String command = INVALID_COMMAND_DESC + " " + VALID_ALIAS_ADD;
        String message = String.format(MESSAGE_INVALID_COMMAND, MESSAGE_INVALID_COMMAND_DESCRIPTION);
        assertParseFailure(parser, command, message);
    }

    @Test
    public void parse_compulsoryArgumentMissing_failure() {
        Alias expectedAlias = new AliasBuilder().withCommand(INVALID_COMMAND_DESC).withAlias(VALID_ALIAS_ADD).build();

        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE);

        //missing command/alias word argument
        String missingArgumentCommand = AddCommand.COMMAND_WORD;
        assertParseFailure(parser, missingArgumentCommand, message);

        //missing both arguments
        String noArgumentCommand = "";
        assertParseFailure(parser, noArgumentCommand, message);
    }
}
