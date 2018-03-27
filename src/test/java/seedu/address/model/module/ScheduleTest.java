package seedu.address.model.module;

import org.junit.Assert;
import org.junit.Test;

public class ScheduleTest {

    private Schedule testBlank = new Schedule();
    private Schedule test = new Schedule("1", "Lecture", "EVERY WEEK", "MONDAY",
            "0000", "2359", "LT17");


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
    public void equals_test() {
        Assert.assertTrue(test.equals(test));
        Assert.assertTrue(testBlank.equals(new Schedule()));
        Assert.assertTrue(test.equals(new Schedule("1", "Lecture", "EVERY WEEK", "MONDAY",
                "0000", "2359", "LT17")));
        Assert.assertFalse(test.equals(1));
        Assert.assertFalse(test.equals(testBlank));
    }

    @Test
    public void toString_test() {
        Assert.assertNotEquals(testBlank.toString(), test.toString());
        String expectedString = "ClassNo: 1\nLessonType: Lecture\nWeekText: EVERY WEEK"
                + "\nDayText: MONDAY\nStartTime: 0000\nEndTime: 2359\n";
        Assert.assertEquals(test.toString(), expectedString);
    }

}
