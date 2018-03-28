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

    public static final Prefix PREFIX_EVENT_TITLE = new Prefix("et/");
    public static final Prefix PREFIX_EVENT_DESCRIPTION = new Prefix("ed/");
    public static final Prefix PREFIX_EVENT_LOCATION = new Prefix("el/");
    public static final Prefix PREFIX_EVENT_DATETIME = new Prefix("edt/");

    public static final Prefix PREFIX_TITLE = new Prefix("et/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("ed/");
    public static final Prefix PREFIX_LOCATION = new Prefix("el/");
    public static final Prefix PREFIX_DATETIME = new Prefix("edt/");

    public static final Prefix PREFIX_TASK_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TASK_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_TASK_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TASK_DUE_DATE = new Prefix("dd/");
    public static final Prefix PREFIX_TASK_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_TASK_CATEGORY = new Prefix("c/");

}
