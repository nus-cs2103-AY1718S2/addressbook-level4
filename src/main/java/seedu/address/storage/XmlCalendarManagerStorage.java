package seedu.address.storage;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyCalendarManager;

/**
 * A class to access CalendarManager data stored as an xml file on the hard disk.
 */
public class XmlCalendarManagerStorage implements CalendarManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlCalendarManagerStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getCalendarManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCalendarManager> readCalendarManager() throws DataConversionException, IOException {
        return readCalendarManager(filePath);
    }

    /**
     * Similar to {@link #readCalendarManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCalendarManager> readCalendarManager(String filePath)
            throws DataConversionException, IOException {
        requireNonNull(filePath);

        File calendarManagerFile = new File(filePath);

        if (!calendarManagerFile.exists()) {
            logger.info("CalendarManager file "  + calendarManagerFile + " not found");
            return Optional.empty();
        }

        XmlSerializableCalendarManager xmlCalManager =
                XmlFileStorage.loadCalendarManagerDataFromSaveFile(new File(filePath));

        try {
            return Optional.of(xmlCalManager.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + calendarManagerFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCalendarManager(ReadOnlyCalendarManager calendarManager) throws IOException {
        saveCalendarManager(calendarManager, filePath);
    }

    @Override
    public void saveCalendarManager(ReadOnlyCalendarManager calendarManager, String filePath) throws IOException {
        requireNonNull(calendarManager);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveCalendarManagerDataToFile(file, new XmlSerializableCalendarManager(calendarManager));
    }
}
