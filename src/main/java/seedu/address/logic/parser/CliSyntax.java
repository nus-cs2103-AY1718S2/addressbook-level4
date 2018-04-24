package seedu.address.logic.parser;

//@@author Kyomian
/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATE_TIME = new Prefix("d/");
    public static final Prefix PREFIX_FILE_PATH = new Prefix("f/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("s/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("e/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");

    // To be deleted soon
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_EMAIL = new Prefix("em/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
}
