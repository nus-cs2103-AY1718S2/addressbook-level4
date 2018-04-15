package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.MoviePlanner;
import seedu.address.testutil.TypicalCinemas;

public class XmlSerializableMoviePlannerTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableMoviePlannerTest/");
    private static final File TYPICAL_CINEMAS_FILE = new File(TEST_DATA_FOLDER + "typicalCinemasMoviePlanner.xml");
    private static final File INVALID_CINEMA_FILE = new File(TEST_DATA_FOLDER + "invalidCinemaMoviePlanner.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagMoviePlanner.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalCinemasFile_success() throws Exception {
        XmlSerializableMoviePlanner dataFromFile = XmlUtil.getDataFromFile(TYPICAL_CINEMAS_FILE,
                XmlSerializableMoviePlanner.class);
        MoviePlanner moviePlannerFromFile = dataFromFile.toModelType();
        MoviePlanner typicalCinemasMoviePlanner = TypicalCinemas.getTypicalMoviePlanner();
        assertEquals(moviePlannerFromFile, typicalCinemasMoviePlanner);
    }

    @Test
    public void toModelType_invalidCinemaFile_throwsIllegalValueException() throws Exception {
        XmlSerializableMoviePlanner dataFromFile = XmlUtil.getDataFromFile(INVALID_CINEMA_FILE,
                XmlSerializableMoviePlanner.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableMoviePlanner dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableMoviePlanner.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
