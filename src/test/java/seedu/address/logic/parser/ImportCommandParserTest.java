package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.ASSIGNMENT3_DEMO1_FILE_PATH;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.FILE_PATH_DESC_DUPLICATE;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.FILE_PATH_DESC_IMPORT;
import static seedu.address.logic.logictestutil.ImportExportTestConstants.INVALID_FILE_PATH_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.model.FilePath;

//@@author karenfrilya97
public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validFilePath_success() {
        FilePath filePath = new FilePath(ASSIGNMENT3_DEMO1_FILE_PATH);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FILE_PATH_DESC_IMPORT, new ImportCommand(filePath));

        // multiple file paths - last file path accepted
        assertParseSuccess(parser, FILE_PATH_DESC_DUPLICATE + FILE_PATH_DESC_IMPORT, new ImportCommand(filePath));

        // multiple file paths, the first one being invalid - last file path accepted with no exception thrown
        assertParseSuccess(parser, INVALID_FILE_PATH_DESC + FILE_PATH_DESC_IMPORT, new ImportCommand(filePath));
    }

    @Test
    public void parse_filePathMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);

        // missing file path prefix
        assertParseFailure(parser, ASSIGNMENT3_DEMO1_FILE_PATH, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid file path
        assertParseFailure(parser, INVALID_FILE_PATH_DESC, FilePath.MESSAGE_FILE_PATH_CONSTRAINTS);

        // multiple file paths, the last one being invalid
        assertParseFailure(parser, FILE_PATH_DESC_IMPORT + INVALID_FILE_PATH_DESC,
                FilePath.MESSAGE_FILE_PATH_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FILE_PATH_DESC_IMPORT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
