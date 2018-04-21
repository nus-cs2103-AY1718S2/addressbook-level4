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
    public static final Prefix PREFIX_UNIVERSITY = new Prefix("u/");
    public static final Prefix PREFIX_EXPECTED_GRADUATION_YEAR = new Prefix("y/");
    public static final Prefix PREFIX_MAJOR = new Prefix("m/");
    public static final Prefix PREFIX_GRADE_POINT_AVERAGE = new Prefix("g/");
    public static final Prefix PREFIX_JOB_APPLIED = new Prefix("j/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    // Used only in add/edit command
    public static final Prefix PREFIX_RESUME = new Prefix("r/");
    public static final Prefix PREFIX_IMAGE = new Prefix("i/");
    public static final Prefix PREFIX_COMMENT = new Prefix("c/");
    // Used only in filter command
    public static final Prefix PREFIX_RATING = new Prefix("r/");
    public static final Prefix PREFIX_INTERVIEW_DATE = new Prefix("d/");

    // used in rating-sort and gpa-sort commands only
    public static final Prefix PREFIX_SORT_ORDER = new Prefix("o/");

    public static final Prefix PREFIX_TECHNICAL_SKILLS_SCORE = new Prefix("t/");
    public static final Prefix PREFIX_COMMUNICATION_SKILLS_SCORE = new Prefix("c/");
    public static final Prefix PREFIX_PROBLEM_SOLVING_SKILLS_SCORE = new Prefix("p/");
    public static final Prefix PREFIX_EXPERIENCE_SCORE = new Prefix("e/");
}
