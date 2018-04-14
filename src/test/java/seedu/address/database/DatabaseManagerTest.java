package seedu.address.database;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.person.TimeTableLink;


//@@author Isaaaca
public class DatabaseManagerTest {

    private DatabaseManager test = DatabaseManager.getInstance();

    @Test
    public void getQuery() {
        String actual = DatabaseManager.getQuery(new TimeTableLink("http://modsn.us/MYwiD"));
        assertEquals("CS2101=SEC:3&CS2103T=TUT:T3&CS2105=LEC:1,TUT:7&CS3242=LEC:1,TUT:3&ST2334=LEC:SL1,TUT:T4",
                actual);
        actual = DatabaseManager.getQuery(new TimeTableLink("http://modsn.us/MYwid"));
        assertEquals(null,
                actual);
    }

    @Test
    public void parseEvents_success() {
        ArrayList<WeeklyEvent> expected = new ArrayList<>();
        Module cs2103t = new Module("CS2103T", "Software Engineering");
        Schedule tutT3 = new Schedule("T3", "Tutorial", "Every Week", "Wednesday",
                "1500", "1600", "COM1-B103");
        WeeklyEvent weeklyEvent = new WeeklyEvent(cs2103t, tutT3);
        expected.add(weeklyEvent);
        ArrayList<WeeklyEvent> actual = DatabaseManager.parseEvents(new TimeTableLink("http://modsn.us/Oy25S"));
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void parseEvents_invalidLink_returnsEmptyList() {
        ArrayList<WeeklyEvent> expected = new ArrayList<>();
        ArrayList<WeeklyEvent> actual = DatabaseManager.parseEvents(new TimeTableLink("http://modsn.us/abcde"));
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void parseEvents_incompatibleLink_returnsEmptyList() {
        ArrayList<WeeklyEvent> expected = new ArrayList<>();
        ArrayList<WeeklyEvent> actual = DatabaseManager.parseEvents(new TimeTableLink("http://modsn.us/oEZ0r"));
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }



}
