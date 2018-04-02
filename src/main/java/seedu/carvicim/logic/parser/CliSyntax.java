package seedu.carvicim.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple COMMANDS
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_VEHICLE_NUMBER = new Prefix("v/");
    public static final Prefix PREFIX_ASSIGNED_EMPLOYEE = new Prefix("w/");
    public static final Prefix PREFIX_START_DATE = new Prefix("sd/");
    public static final Prefix PREFIX_END_DATE = new Prefix("ed/");
}
