package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TRANSACTION_TYPE = new Prefix("type/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("a/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_PAYER = new Prefix("payer/");
    public static final Prefix PREFIX_PAYEE = new Prefix("payee/");
    public static final Prefix PREFIX_SPLIT_METHOD = new Prefix("m/");
    public static final Prefix PREFIX_SPLIT_BY_UNITS = new Prefix("units/");
    public static final Prefix PREFIX_SPLIT_BY_PERCENTAGE = new Prefix("percentage/");

}
