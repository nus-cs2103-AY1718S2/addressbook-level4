//@@author SuxianAlicia
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.CalendarManager;
import seedu.address.testutil.TypicalCalendarEntries;

public class XmlSerializableCalendarManagerTest {

    private static final String TEST_DATA_FOLDER =
            FileUtil.getPath("src/test/data/XmlSerializableCalendarManagerTest/");
    private static final File TYPICAL_EVENTS_FILE =
            new File(TEST_DATA_FOLDER + "typicalEntriesCalendarManager.xml");
    private static final File INVALID_EVENT_FILE =
            new File(TEST_DATA_FOLDER + "invalidEntriesCalendarManager.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalEntriesFile_success() throws Exception {
        XmlSerializableCalendarManager dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EVENTS_FILE,
                XmlSerializableCalendarManager.class);
        CalendarManager calendarManagerFromFile = dataFromFile.toModelType();
        CalendarManager typicalCalendarEntriesCalendarManager =
                TypicalCalendarEntries.getTypicalCalendarManagerWithEntries();
        assertEquals(calendarManagerFromFile, typicalCalendarEntriesCalendarManager);
    }

    @Test
    public void toModelType_invalidEventsFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCalendarManager dataFromFile = XmlUtil.getDataFromFile(INVALID_EVENT_FILE,
                XmlSerializableCalendarManager.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
