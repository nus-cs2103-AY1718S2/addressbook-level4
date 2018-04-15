package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_ADD;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_ALIAS;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_CLEAR;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_DECRYPT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_EDIT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_ENCRYPT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_EXIT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_EXPORT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_FIND;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_HELP;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_IMPORT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_LIST;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_MAP1;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_REDO;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_SELECT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_UNALIAS;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_UNDO;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_UNION;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_UPLOAD;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_VACANT;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALIAS_SYNTAX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMMAND_SYNTAX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_ALIAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_CLEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_DECRYPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_EDIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_ENCRYPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_EXIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_EXPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_FIND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_HELP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_IMPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_MAP1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_REDO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_SELECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_UNALIAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_UNDO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_UNION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_UPLOAD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_VACANT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
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
import seedu.address.logic.commands.UploadCommand;
import seedu.address.logic.commands.VacantCommand;

import seedu.address.model.alias.Alias;
import seedu.address.testutil.AliasBuilder;

//@@author jingyinno
public class AliasCommandParserTest {
    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_addAlias_success() {
        Alias expectedAddAlias = new AliasBuilder().withCommand(AddCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_ADD).build();

        assertParseSuccess(parser, ALIAS_DESC_ADD, new AliasCommand(expectedAddAlias));
    }

    @Test
    public void parse_aliasAlias_success() {
        Alias expectedAliasAlias = new AliasBuilder().withCommand(AliasCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_ALIAS).build();

        assertParseSuccess(parser, ALIAS_DESC_ALIAS, new AliasCommand(expectedAliasAlias));
    }

    @Test
    public void parse_clearAlias_success() {
        Alias expectedClearAlias = new AliasBuilder().withCommand(ClearCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_CLEAR).build();

        assertParseSuccess(parser, ALIAS_DESC_CLEAR, new AliasCommand(expectedClearAlias));
    }

    @Test
    public void parse_deleteAlias_success() {
        Alias expectedDeleteAlias = new AliasBuilder().withCommand(DeleteCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_DELETE).build();

        assertParseSuccess(parser, ALIAS_DESC_DELETE, new AliasCommand(expectedDeleteAlias));
    }

    @Test
    public void parse_editAlias_success() {
        Alias expectedEditAlias = new AliasBuilder().withCommand(EditCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_EDIT).build();

        assertParseSuccess(parser, ALIAS_DESC_EDIT, new AliasCommand(expectedEditAlias));
    }

    @Test
    public void parse_exitAlias_success() {
        Alias expectedExitAlias = new AliasBuilder().withCommand(ExitCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_EXIT).build();

        assertParseSuccess(parser, ALIAS_DESC_EXIT, new AliasCommand(expectedExitAlias));
    }

    @Test
    public void parse_findAlias_success() {
        Alias expectedFindAlias = new AliasBuilder().withCommand(FindCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_FIND).build();

        assertParseSuccess(parser, ALIAS_DESC_FIND, new AliasCommand(expectedFindAlias));
    }

    @Test
    public void parse_helpAlias_success() {
        Alias expectedHelpAlias = new AliasBuilder().withCommand(HelpCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_HELP).build();

        assertParseSuccess(parser, ALIAS_DESC_HELP, new AliasCommand(expectedHelpAlias));
    }

    @Test
    public void parse_historyAlias_success() {
        Alias expectedHistoryAlias = new AliasBuilder().withCommand(HistoryCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_HISTORY).build();

        assertParseSuccess(parser, ALIAS_DESC_HISTORY, new AliasCommand(expectedHistoryAlias));
    }

    @Test
    public void parse_importAlias_success() {
        Alias expectedImportAlias = new AliasBuilder().withCommand(ImportCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_IMPORT).build();

        assertParseSuccess(parser, ALIAS_DESC_IMPORT, new AliasCommand(expectedImportAlias));
    }

    @Test
    public void parse_listAlias_success() {
        Alias expectedListAlias = new AliasBuilder().withCommand(ListCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_LIST).build();

        assertParseSuccess(parser, ALIAS_DESC_LIST, new AliasCommand(expectedListAlias));
    }

    @Test
    public void parse_redoAlias_success() {
        Alias expectedRedoAlias = new AliasBuilder().withCommand(RedoCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_REDO).build();

        assertParseSuccess(parser, ALIAS_DESC_REDO, new AliasCommand(expectedRedoAlias));
    }

    @Test
    public void parse_selectAlias_success() {
        Alias expectedSelectAlias = new AliasBuilder().withCommand(SelectCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_SELECT).build();

        assertParseSuccess(parser, ALIAS_DESC_SELECT, new AliasCommand(expectedSelectAlias));
    }

    @Test
    public void parse_undoAlias_success() {
        Alias expectedUndoAlias = new AliasBuilder().withCommand(UndoCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_UNDO).build();

        assertParseSuccess(parser, ALIAS_DESC_UNDO, new AliasCommand(expectedUndoAlias));
    }

    @Test
    public void parse_encryptAlias_success() {
        Alias expectedEncryptAlias = new AliasBuilder().withCommand(PasswordCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_ENCRYPT).build();

        assertParseSuccess(parser, ALIAS_DESC_ENCRYPT, new AliasCommand(expectedEncryptAlias));
    }

    @Test
    public void parse_decryptAlias_success() {
        Alias expectedDecryptAlias = new AliasBuilder().withCommand(RemovePasswordCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_DECRYPT).build();

        assertParseSuccess(parser, ALIAS_DESC_DECRYPT, new AliasCommand(expectedDecryptAlias));
    }

    @Test
    public void parse_mapAlias_success() {
        Alias expectedMapAlias = new AliasBuilder().withCommand(MapCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_MAP1).build();

        assertParseSuccess(parser, ALIAS_DESC_MAP1, new AliasCommand(expectedMapAlias));
    }

    @Test
    public void parse_unionAlias_success() {
        Alias expectedUnionAlias = new AliasBuilder().withCommand(TimetableUnionCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_UNION).build();

        assertParseSuccess(parser, ALIAS_DESC_UNION, new AliasCommand(expectedUnionAlias));
    }

    @Test
    public void parse_unaliasAlias_success() {
        Alias expectedUnaliasAlias = new AliasBuilder().withCommand(UnaliasCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_UNALIAS).build();

        assertParseSuccess(parser, ALIAS_DESC_UNALIAS, new AliasCommand(expectedUnaliasAlias));
    }

    @Test
    public void parse_uploadAlias_success() {
        Alias expectedUploadAlias = new AliasBuilder().withCommand(UploadCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_UPLOAD).build();

        assertParseSuccess(parser, ALIAS_DESC_UPLOAD, new AliasCommand(expectedUploadAlias));
    }

    @Test
    public void parse_exportAlias_success() {
        Alias expectedExportAlias = new AliasBuilder().withCommand(ExportCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_EXPORT).build();

        assertParseSuccess(parser, ALIAS_DESC_EXPORT, new AliasCommand(expectedExportAlias));
    }

    @Test
    public void parse_vacantAlias_success() {
        Alias expectedVacantAlias = new AliasBuilder().withCommand(VacantCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_VACANT).build();

        assertParseSuccess(parser, ALIAS_DESC_VACANT, new AliasCommand(expectedVacantAlias));
    }

    @Test
    public void parse_invalidAliasSyntax_failure() {
        //alias with symbols failure
        assertParseFailure(parser, INVALID_ALIAS_SYNTAX_DESC, Alias.MESSAGE_ALIAS_CONSTRAINTS);
    }

    @Test
    public void parse_invalidCommandSyntax_failure() {
        //command with symbols failure
        assertParseFailure(parser, INVALID_COMMAND_SYNTAX_DESC, Alias.MESSAGE_ALIAS_CONSTRAINTS);
    }

    @Test
    public void parse_compulsoryArgumentMissing_failure() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE);

        //missing command/alias word argument
        String missingArgumentCommand = AddCommand.COMMAND_WORD;
        assertParseFailure(parser, missingArgumentCommand, message);

        //missing both arguments
        String noArgumentCommand = "";
        assertParseFailure(parser, noArgumentCommand, message);
    }
}
