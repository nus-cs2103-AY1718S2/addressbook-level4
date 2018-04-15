package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    //@@author zacci
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_USERNAME = new Prefix("user/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pw/");
    public static final Prefix PREFIX_SECURITY_LEVEL = new Prefix("sl/");
    //@@author philos22
    public static final Prefix PREFIX_EVENT = new Prefix("event/");
    public static final Prefix PREFIX_LOCATION = new Prefix("loc/");
    public static final Prefix PREFIX_START = new Prefix("start/");
    public static final Prefix PREFIX_END = new Prefix("end/");
    //@@author
}
