package seedu.address.logic.logictestutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Constants for task object.
 */
public class TaskTestConstants {

    public static final String VALID_NAME_MA2108_HOMEWORK = "MA2108 Homework 3";
    public static final String VALID_NAME_CS2010_QUIZ = "CS2010 Online Quiz 2";
    public static final String VALID_DATE_TIME_MA2108_HOMEWORK = "11/11/1111 11:11";
    public static final String VALID_DATE_TIME_CS2010_QUIZ = "22/12/2222 22:22";
    public static final String VALID_REMARK_MA2108_HOMEWORK = "3% of total grade";
    public static final String VALID_REMARK_CS2010_QUIZ = "5% of total grade";
    public static final String VALID_TAG_MA2108 = "MA2108";
    public static final String VALID_TAG_CS2010 = "CS2010";
    //@@author Kyomian
    public static final String VALID_TAG_URGENT = "Urgent";

    //@@author
    public static final String NAME_DESC_MA2108_HOMEWORK = " " + PREFIX_NAME + VALID_NAME_MA2108_HOMEWORK;
    public static final String NAME_DESC_CS2010_QUIZ = " " + PREFIX_NAME + VALID_NAME_CS2010_QUIZ;
    public static final String DATE_TIME_DESC_MA2108_HOMEWORK = " " + PREFIX_DATE_TIME
            + VALID_DATE_TIME_MA2108_HOMEWORK;
    public static final String DATE_TIME_DESC_CS2010_QUIZ = " " + PREFIX_DATE_TIME + VALID_DATE_TIME_CS2010_QUIZ;
    public static final String REMARK_DESC_MA2108_HOMEWORK = " " + PREFIX_REMARK + VALID_REMARK_MA2108_HOMEWORK;
    public static final String REMARK_DESC_CS2010_QUIZ = " " + PREFIX_REMARK + VALID_REMARK_CS2010_QUIZ;
    public static final String TAG_DESC_CS2010 = " " + PREFIX_TAG + VALID_TAG_CS2010;
    public static final String TAG_DESC_MA2108 = " " + PREFIX_TAG + VALID_TAG_MA2108;
    //@@author Kyomian
    public static final String TAG_DESC_URGENT = " " + PREFIX_TAG + VALID_TAG_URGENT;

    public static final String INVALID_TASK_NAME_DESC = " " + PREFIX_NAME + "CS2106 Assignment&"; // '&' not allowed
    public static final String INVALID_TASK_DATE_TIME_DESC = " " + PREFIX_DATE_TIME + "2018-03-04 17:00";
    public static final String INVALID_TASK_REMARK_DESC = " " + PREFIX_REMARK + ""; // '' not allowed
    public static final String INVALID_TASK_TAG_DESC = " " + PREFIX_TAG + "CS2106*"; // '*' not allowed in tags
}
