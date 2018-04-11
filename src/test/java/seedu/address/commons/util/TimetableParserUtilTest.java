package seedu.address.commons.util;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.timetable.TimetableParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TimetableBuilder;

//@@author AzuraAiR
public class TimetableParserUtilTest {

    private static final String EMPTY_URL = " ";
    private static final String VALID_WEEK = "Odd Week";
    private static final String VALID_DAY = "Wednesday";
    private static final int VALID_TIMESLOT = 11;

    private static final String VALID_URL = "http://modsn.us/kqUAK";
    private static final String INVALID_OTHER_URL = "http://google.com/";
    private static final String INVALID_NUSMODS_URL = "http://modsn.us/zzzzz";

    @Test
    public void parseUrl_nullUrl_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> TimetableParserUtil.parseUrl(null));
    }

    @Test
    public void parseUrl_emptyUrl_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> TimetableParserUtil.parseUrl(EMPTY_URL));
    }

    @Test
    public void parseUrl_invalidUrl_throwsIllegalArgumentAndParseException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> TimetableParserUtil.parseUrl(INVALID_OTHER_URL));
        Assert.assertThrows(ParseException.class, () -> TimetableParserUtil.parseUrl(INVALID_NUSMODS_URL));
    }

    @Test
    public void parseShortUrl_validUrl_success() {
        Timetable timetable = new TimetableBuilder().getDummy(0);
        try {
            assertEquals(timetable.getLessonFromSlot(VALID_WEEK, VALID_DAY, VALID_TIMESLOT).toString(),
                TimetableParserUtil.parseUrl(VALID_URL).getLessonFromSlot(VALID_WEEK, VALID_DAY, VALID_TIMESLOT)
                        .toString());
        } catch (IllegalValueException pe) {
            fail("Unexpected exception thrown " + pe);
        }
    }

}
