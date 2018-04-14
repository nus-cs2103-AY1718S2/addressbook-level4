package seedu.address.database.module;

import org.junit.Assert;
import org.junit.Test;

//@@author Isaaaca
public class ScheduleTest {
    private static final String DEFAULT_CLASSNO = "9";
    private static final String DEFAULT_LESSON_TYPE = "Tutorial";
    private static final String DEFAULT_WEEK_TEXT = "EVERY WEEK";
    private static final String DEFAULT_DAY_TEXT = "WEDNESDAY";
    private static final String DEFAULT_START_TIME = "1500";
    private static final String DEFAULT_END_TIME = "1600";
    private static final String DEFAULT_VENUE = "COM1 B1-04";


    private Schedule testBlank = new Schedule();
    private Schedule test = new Schedule(DEFAULT_CLASSNO, DEFAULT_LESSON_TYPE, DEFAULT_WEEK_TEXT, DEFAULT_DAY_TEXT,
            DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_VENUE);

    @Test
    public void getClassNo() {
        Assert.assertEquals("1", testBlank.getClassNo());
        Assert.assertEquals(DEFAULT_CLASSNO, test.getClassNo());
    }

    @Test
    public void getLessonType() {
        Assert.assertEquals("Lecture", testBlank.getLessonType());
        Assert.assertEquals(DEFAULT_LESSON_TYPE, test.getLessonType());
    }

    @Test
    public void getWeekText() {
        Assert.assertEquals("1", testBlank.getWeekText());
        Assert.assertEquals(DEFAULT_WEEK_TEXT, test.getWeekText());
    }

    @Test
    public void getDayText() {
        Assert.assertEquals("Monday", testBlank.getDayText());
        Assert.assertEquals(DEFAULT_DAY_TEXT, test.getDayText());
    }

    @Test
    public void getStartTime() {
        Assert.assertEquals("0000", testBlank.getStartTime());
        Assert.assertEquals(DEFAULT_START_TIME, test.getStartTime());
    }

    @Test
    public void getEndTime() {
        Assert.assertEquals("2359", testBlank.getEndTime());
        Assert.assertEquals(DEFAULT_END_TIME, test.getEndTime());
    }

    @Test
    public void getVenue() {
        Assert.assertEquals("COM1 01-01", testBlank.getVenue());
        Assert.assertEquals(DEFAULT_VENUE, test.getVenue());
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
        String expectedBlank = "ClassNo: 1"
                + "\nLessonType: Lecture"
                + "\nWeekText: 1"
                + "\nDayText: Monday"
                + "\nStartTime: 0000"
                + "\nEndTime: 2359"
                + "\nVenue: COM1 01-01" + "\n";
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
