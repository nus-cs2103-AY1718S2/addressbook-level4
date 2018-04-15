package seedu.address.logic.logictestutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

//@@author Kyomian
/**
 * Constants for event object.
 */
public class EventTestConstants {

    public static final String VALID_NAME_CCA = "CCA";
    public static final String VALID_NAME_CAMP = "Orientation Camp";
    public static final String VALID_START_DATETIME_CCA = "15/5/2018 17:00";
    public static final String VALID_START_DATETIME_CAMP = "1/8/2018 8:00";
    public static final String VALID_END_DATETIME_CCA = "15/5/2018 21:00";
    public static final String VALID_END_DATETIME_CAMP = "8/8/2018 17:00";
    public static final String VALID_LOCATION_CCA = "NUS Utown";
    public static final String VALID_LOCATION_CAMP = "NUS School of Computing";
    public static final String VALID_REMARK_CCA = "Bring flute";
    public static final String VALID_REMARK_CAMP = "Arrive earlier for briefing";
    public static final String VALID_TAG_CCA = "Band";
    public static final String VALID_TAG_CAMP = "Orientation";
    public static final String VALID_TAG_IMPORTANT = "Important";

    public static final String NAME_DESC_CCA = " " + PREFIX_NAME + VALID_NAME_CCA;
    public static final String NAME_DESC_CAMP = " " + PREFIX_NAME + VALID_NAME_CAMP;
    public static final String START_DATETIME_DESC_CCA = " " + PREFIX_START_DATETIME
            + VALID_START_DATETIME_CCA;
    public static final String START_DATETIME_DESC_CAMP = " " + PREFIX_START_DATETIME
            + VALID_START_DATETIME_CAMP;
    public static final String END_DATETIME_DESC_CCA = " " + PREFIX_END_DATETIME
            + VALID_END_DATETIME_CCA;
    public static final String END_DATETIME_DESC_CAMP = " " + PREFIX_END_DATETIME
            + VALID_END_DATETIME_CAMP;
    public static final String LOCATION_DESC_CCA = " " + PREFIX_LOCATION + VALID_LOCATION_CCA;
    public static final String LOCATION_DESC_CAMP = " " + PREFIX_LOCATION + VALID_LOCATION_CAMP;
    public static final String REMARK_DESC_CCA = " " + PREFIX_REMARK + VALID_REMARK_CCA;
    public static final String REMARK_DESC_CAMP = " " + PREFIX_REMARK + VALID_REMARK_CAMP;
    public static final String TAG_DESC_CCA = " " + PREFIX_TAG + VALID_TAG_CCA;
    public static final String TAG_DESC_CAMP = " " + PREFIX_TAG + VALID_TAG_CAMP;
    public static final String TAG_DESC_IMPORTANT = " " + PREFIX_TAG + VALID_TAG_IMPORTANT;

    public static final String AFTEREND_START_DATETIME_CCA = "16/5/2018 8:00";
    public static final String AFTEREND_START_DATETIME_DESC_CCA = " " + PREFIX_START_DATETIME
            + AFTEREND_START_DATETIME_CCA;

    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_NAME + "Orbital&"; // '&' not allowed
    public static final String INVALID_EVENT_START_DATETIME_DESC = " " + PREFIX_START_DATETIME + "2018-03-04 17:00";
    public static final String INVALID_EVENT_END_DATETIME_DESC = " " + PREFIX_END_DATETIME + "2018-03-10 17:00";
    public static final String INVALID_EVENT_LOCATION_DESC = " " + PREFIX_LOCATION + ""; // '' not allowed
    // whitespace in front not allowed

    public static final String INVALID_EVENT_REMARK_DESC = " " + PREFIX_REMARK + ""; // '' not allowed
    public static final String INVALID_EVENT_TAG_DESC = " " + PREFIX_TAG + "Important*"; // '*' not allowed in tags
}
