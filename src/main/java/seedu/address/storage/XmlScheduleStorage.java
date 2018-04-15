package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlySchedule;

//@@author demitycho
/**
 * A class to access Schedule data stored as an xml file on the hard disk.
 */
public class XmlScheduleStorage implements ScheduleStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlScheduleStorage.class);

    private String filePath;

    public XmlScheduleStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getScheduleFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySchedule> readSchedule() throws DataConversionException, IOException {
        return readSchedule(filePath);
    }

    /**
     * Similar to {@link #readSchedule()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySchedule> readSchedule(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File scheduleFile = new File(filePath);

        if (!scheduleFile.exists()) {
            logger.info("Schedule file "  + scheduleFile + " not found");
            return Optional.empty();
        }

        XmlSerializableSchedule xmlSchedule = XmlFileStorage.loadScheduleDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlSchedule.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + scheduleFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSchedule(ReadOnlySchedule schedule) throws IOException {
        saveSchedule(schedule, filePath);
    }

    /**
     * Similar to {@link #saveSchedule(ReadOnlySchedule)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSchedule(ReadOnlySchedule schedule, String filePath) throws IOException {
        requireNonNull(schedule);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveScheduleDataToFile(file, new XmlSerializableSchedule(schedule));
    }

    @Override
    public void backupSchedule(ReadOnlySchedule schedule) throws IOException {
        saveSchedule(schedule, filePath + ".backup");
    }
}
