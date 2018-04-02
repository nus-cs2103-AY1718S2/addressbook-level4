package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class XmlAdaptedQueueTest {

    @Test
    public void toModetlType_validQueueNo_returnInt() {
        XmlAdaptedQueue queueNo = new XmlAdaptedQueue(1);
        assertEquals(1, queueNo.toModelType());
    }

    @Test
    public void equals() {
        XmlAdaptedQueue firstQueue = new XmlAdaptedQueue(1);
        XmlAdaptedQueue secondQueue = new XmlAdaptedQueue(2);

        //same object --> return true
        assertTrue(firstQueue.equals(firstQueue));

        //same value --> return true
        XmlAdaptedQueue compareQueue = new XmlAdaptedQueue(1);
        assertTrue(firstQueue.equals(compareQueue));

        // different types -> returns false
        assertFalse(firstQueue.equals(1));

        //null -> returns false
        assertFalse(firstQueue.equals(null));

        //different XmlAdaptedQueue --> return false
        assertFalse(firstQueue.equals(secondQueue));
    }
}
