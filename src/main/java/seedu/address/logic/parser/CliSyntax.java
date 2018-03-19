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
    public static final Prefix PREFIX_EXPECTED_GRADUATION_YEAR = new Prefix("y/");
    public static final Prefix PREFIX_MAJOR = new Prefix("m/");
    public static final Prefix PREFIX_RESUME = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_TECHNICAL_SKILLS_SCORE = new Prefix("t/");
    public static final Prefix PREFIX_COMMUNICATION_SKILLS_SCORE = new Prefix("c/");
    public static final Prefix PREFIX_PROBLEM_SOLVING_SKILLS_SCORE = new Prefix("p/");
    public static final Prefix PREFIX_EXPERIENCE_SCORE = new Prefix("e/");
}
