package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.time.LocalDateTime;

public class InterviewDateTest {

    private LocalDateTime timeNow = LocalDateTime.now().withNano(0);
    private long timeNowInEpoch = timeNow.toEpochSecond(InterviewDate.LOCAL_ZONE_OFFSET);

    private final InterviewDate validInterviewDate = new InterviewDate(timeNow);
    private final InterviewDate nullInterviewDate = new InterviewDate();

    @Test
    public void setup_sameTimeNow_returnsTrue() {
        LocalDateTime convertedTime = LocalDateTime.ofEpochSecond(timeNowInEpoch, 0, InterviewDate.LOCAL_ZONE_OFFSET);
        assertTrue(convertedTime.equals(timeNow));
    }

    @Test
    public void constructor_sameInterviewDate_returnsTrue() {
        InterviewDate validInterviewDateInEpoch = new InterviewDate(timeNowInEpoch);
        assertTrue(validInterviewDate.equals(validInterviewDateInEpoch));
    }

    @Test
    public void constructor_unscheduledInterviewDate_returnsTrue() {
        assertNotNull(nullInterviewDate);
        assertNull(nullInterviewDate.getDateTime());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(validInterviewDate.equals(validInterviewDate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        InterviewDate validInterviewDateCopy = new InterviewDate(validInterviewDate.getDateTime());
        assertTrue(validInterviewDate.equals(validInterviewDateCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(validInterviewDate.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(validInterviewDate.equals(null));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        assertFalse(validInterviewDate.equals(nullInterviewDate));
    }
}