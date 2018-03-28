package seedu.address.model.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class ModuleTest {

    private static final String DEFAULT_MODULE_CODE = "CS2013T";
    private static final String DEFAULT_MODULE_TITLE = "Software Engineering";
    private static final Module test = new Module(DEFAULT_MODULE_CODE, DEFAULT_MODULE_TITLE, new ArrayList<>());

    @Test
    public void constructor_nullArgument_throwsNullPointerException() {
        String invalidArg = null;
        Assert.assertThrows(NullPointerException.class, () -> new Module(invalidArg, invalidArg, null));

    }

    @Test
    public void getModuleCode() {
        assertEquals(test.getModuleCode(), DEFAULT_MODULE_CODE);
    }

    @Test
    public void getModuleTitle() {
        assertEquals(test.getModuleTitle(), DEFAULT_MODULE_TITLE);
    }

    @Test
    public void getScheduleList() {
        assertEquals(test.getScheduleList(), new ArrayList<>());
    }

    @Test
    public void testToString() {
        assertEquals(test.toString(),
                "moduleCode: " + DEFAULT_MODULE_CODE + " moduleTitle: " + DEFAULT_MODULE_TITLE + "\n"
                        + new ArrayList<>().toString());

    }

    @Test
    public void testEquals() {
        assertTrue(test.equals(test));
        assertTrue(test.equals(new Module(DEFAULT_MODULE_CODE, DEFAULT_MODULE_TITLE)));
        assertFalse(test.equals(new Module("CS1101", DEFAULT_MODULE_TITLE)));
        assertFalse(test.equals(DEFAULT_MODULE_CODE));
    }
}
