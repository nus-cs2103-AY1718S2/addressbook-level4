package seedu.progresschecker.testutil;

//@@author EdwardKSG
/**
 * A utility class containing a list of arguments to be used in tests for tasks commands.
 */
public class TypicalTaskArgs {
    // User input

    // week number arguments
    public static final String FIRST_WEEK = "1";
    public static final int FIRST_WEEK_INT = 1;
    public static final String RANDOM_WEEK = "5";
    public static final String LAST_WEEK = "13";
    public static final int LAST_WEEK_INT = 13;

    public static final String OUT_OF_BOUND_WEEK = "14";

    // task index number arguments
    public static final String INDEX_FIRST_TASK = "1";
    public static final int INDEX_FIRST_TASK_INT = 1;
    public static final int INDEX_LAST_TASK_INT = 4; //specifically for the model being tested
    public static final int OUT_OF_BOUND_TASK_INDEX_INT = 500;

    // valid char arguments
    public static final String COMPULSORY = "compulsory";
    public static final String COM = "com";
    public static final int COM_INT = -13;
    public static final String SUBMISSION = "submission";
    public static final String SUB = "sub";
    public static final int SUB_INT = -20;
    public static final String ASTERISK = "*";
    public static final int ASTERISK_INT = 0;
    public static final String DEFAULT_LIST_TITLE = "CS2103 LOs";
    public static final String VALID_TITLE_EDGE = "1234567891234567891234567891234567891234567891234";

    // general invalid input arguments
    public static final String INVALID_ZERO = "0";
    public static final String INVALID_NEGATIVE = "-3";
    public static final String INVALID_DOUBLE = "3.4";
    public static final String INVALID_CHARSET = "comppp";
    public static final String INVALID_TITLE = "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
            + "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"; // exceeds length limit
    public static final String INVALID_MULTIPLE_ARGS = "compulsory 4 2";

    //-----------------------------------------------------------------------------------------------------

    // Parser output (command input)

}
