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
    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("p/");
    public static final Prefix PREFIX_NEW_PASSWORD = new Prefix("newp/");

    //@@author ifalluphill
    public static final Prefix PREFIX_CAL_EVENT_NAME = new Prefix("title/");
    public static final Prefix PREFIX_CAL_START_DATE_TIME = new Prefix("start/");
    public static final Prefix PREFIX_CAL_END_DATE_TIME = new Prefix("end/");
    public static final Prefix PREFIX_CAL_LOCATION = new Prefix("loc/");
    public static final Prefix PREFIX_CAL_LINK_PERSON = new Prefix("lp/");

    public static final Prefix PREFIX_SCHEDULE_DATE = new Prefix("d/");
    //@@author

}
