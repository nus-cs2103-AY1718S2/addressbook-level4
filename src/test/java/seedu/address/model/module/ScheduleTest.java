package seedu.address.model.module;

import org.junit.Assert;
import org.junit.Test;

public class ScheduleTest {
    private static final String DEFAULT_CLASSNO = "1";
    private static final String DEFAULT_LESSON_TYPE = "Lecture";
    private static final String DEFAULT_WEEK_TEXT = "EVERY WEEK";
    private static final String DEFAULT_DAY_TEXT = "MONDAY";
    private static final String DEFAULT_START_TIME = "0000";
    private static final String DEFAULT_END_TIME = "2359";
    private static final String DEFAULT_VENUE = "LT17";


    private Schedule testBlank = new Schedule();
    private Schedule test = new Schedule(DEFAULT_CLASSNO, DEFAULT_LESSON_TYPE, DEFAULT_WEEK_TEXT, DEFAULT_DAY_TEXT,
            DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_VENUE);

    @Test
    public void getClassNo() {
        Assert.assertEquals("", testBlank.getClassNo());
        Assert.assertEquals("1", test.getClassNo());
    }

    @Test
    public void getLessonType() {
        Assert.assertEquals("", testBlank.getLessonType());
        Assert.assertEquals("Lecture", test.getLessonType());
    }

    @Test
    public void getWeekText() {
        Assert.assertEquals("", testBlank.getWeekText());
        Assert.assertEquals("EVERY WEEK", test.getWeekText());
    }

    @Test
    public void getDayText() {
        Assert.assertEquals("", testBlank.getDayText());
        Assert.assertEquals("MONDAY", test.getDayText());
    }

    @Test
    public void getStartTime() {
        Assert.assertEquals("", testBlank.getStartTime());
        Assert.assertEquals("0000", test.getStartTime());
    }

    @Test
    public void getEndTime() {
        Assert.assertEquals("", testBlank.getEndTime());
        Assert.assertEquals("2359", test.getEndTime());
    }

    @Test
    public void getVenue() {
        Assert.assertEquals("", testBlank.getVenue());
        Assert.assertEquals("LT17", test.getVenue());
    }

    @Test
    public void testToString() {
        String expected = "ClassNo: " + DEFAULT_CLASSNO
                + "\nLessonType: " + DEFAULT_LESSON_TYPE
                + "\nWeekText: " + DEFAULT_WEEK_TEXT
                + "\nDayText: " + DEFAULT_DAY_TEXT
                + "\nStartTime: " + DEFAULT_START_TIME
                + "\nEndTime: " + DEFAULT_END_TIME
                + "\nVenue: " + DEFAULT_VENUE + "\n";
        String expectedBlank = "ClassNo: "
                + "\nLessonType: "
                + "\nWeekText: "
                + "\nDayText: "
                + "\nStartTime: "
                + "\nEndTime: "
                + "\nVenue: " + "\n";
        Assert.assertEquals(expected, test.toString());
        Assert.assertEquals(expectedBlank, testBlank.toString());
    }

    @Test
    public void testEquals() {
        Assert.assertTrue(test.equals(test));
        Assert.assertTrue(test.equals(new Schedule(DEFAULT_CLASSNO, DEFAULT_LESSON_TYPE, DEFAULT_WEEK_TEXT,
                DEFAULT_DAY_TEXT, DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_VENUE)));
        Assert.assertFalse(test.equals(DEFAULT_LESSON_TYPE));
        Assert.assertFalse(test.equals(new Schedule("4", DEFAULT_LESSON_TYPE, DEFAULT_WEEK_TEXT,
                DEFAULT_DAY_TEXT, DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_VENUE)));

    }

}
