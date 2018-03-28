package seedu.address.model.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ModuleTest {
    private Module blank = new Module();
    private Module module1 = new Module("CS2101", "Effective Communication");
    private Module module2 = new Module("CS2103", "Software Engineer");

    @Test
    public void constructor_similarObject_returnsTrue() throws Exception {
        assertEquals(blank, new Module("", ""));
    }

    @Test
    public void getModuleCode() {
        assertEquals(module1.getModuleCode(), "CS2101");
        assertEquals(module2.getModuleCode(), "CS2103");
        assertNotEquals(module1.getModuleCode(), module2.getModuleCode());
    }

    @Test
    public void getModuleTitle() {
        assertEquals(module1.getModuleTitle(), "Effective Communication");
        assertEquals(module2.getModuleTitle(), "Software Engineer");
        assertNotEquals(module1.getModuleTitle(), module2.getModuleTitle());
    }

    @Test
    public void getTimetable() {
        assertEquals(module1.getTimetable(), blank.getTimetable());
    }

    @Test
    public void equals() {
        assertEquals(blank, blank);
        assertEquals(blank, new Module());
        assertEquals(module2, new Module("CS2103", "Software Engineer"));
        assertNotEquals(module1, 1);
        assertNotEquals(blank, module1);
        assertNotEquals(module1, module2);
    }
}
