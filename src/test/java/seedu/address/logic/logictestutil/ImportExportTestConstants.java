package seedu.address.logic.logictestutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

//@@author karenfrilya97
/**
 * Constants to test import and export commands.
 */
public class ImportExportTestConstants {

    // ============================= IMPORT ============================================
    public static final String IMPORT_TEST_DATA_FOLDER = "src\\test\\data\\ImportCommandTest\\";
    public static final String ASSIGNMENT3_DEMO1_FILE_PATH = IMPORT_TEST_DATA_FOLDER + "validImportFile.xml";
    public static final String MISSING_FILE_PATH = IMPORT_TEST_DATA_FOLDER + "missing.xml";
    public static final String ILLEGAL_VALUES_FILE_PATH = IMPORT_TEST_DATA_FOLDER + "illegalValues.xml";
    public static final String DUPLICATE_ACTIVITY_FILE_PATH = IMPORT_TEST_DATA_FOLDER + "duplicateActivity.xml";
    public static final String FILE_PATH_DESC_IMPORT = " " + PREFIX_FILE_PATH + ASSIGNMENT3_DEMO1_FILE_PATH;
    public static final String FILE_PATH_DESC_DUPLICATE = " " + PREFIX_FILE_PATH + DUPLICATE_ACTIVITY_FILE_PATH;
    public static final String INVALID_FILE_PATH_DESC = " " + PREFIX_FILE_PATH + "no.xmlAtTheEnd";

    // ============================= EXPORT ============================================
    public static final String EXPORT_TEST_DATA_FOLDER = "src\\test\\data\\ExportCommandTest\\";
    public static final String EXPORT_FILE_PATH = EXPORT_TEST_DATA_FOLDER + "validExportFile.xml";
    public static final String EXISTING_FILE_PATH = EXPORT_TEST_DATA_FOLDER + "existingFile.xml";
    public static final String FILE_PATH_DESC_EXPORT = " " + PREFIX_FILE_PATH + EXPORT_FILE_PATH;
}
