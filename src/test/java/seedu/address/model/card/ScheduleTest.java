package seedu.address.model.card;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScheduleTest {

    @Test
    public void feedback_getSuccessRate() {
        Schedule s = new Schedule();
        s.feedback(true);
        s.feedback(false);
        s.feedback(true);
        assertEquals(s.getSuccessRate(), 2.0 / 3.0, 1);
    }

    @Test
    public void feedback_getEasingFactor() {
        Schedule s = new Schedule();
        assertEquals(s.getEasingFactor(), 1.0, 0.000001);
        s.feedback(true);
        assertEquals(s.getEasingFactor(), 1.0, 0.000001);
        s.feedback(false);
        assertEquals(s.getEasingFactor(), 1.0, 0.000001);
        s.feedback(true);
        assertEquals(s.getEasingFactor(), 1.0, 0.000001);
        s.feedback(true);
        assertEquals(s.getEasingFactor(), 0.5649254682876186, 0.000001);
    }
}
