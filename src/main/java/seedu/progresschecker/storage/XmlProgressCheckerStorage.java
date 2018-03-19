package seedu.progresschecker.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.exceptions.DataConversionException;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.commons.util.FileUtil;
import seedu.progresschecker.model.ReadOnlyProgressChecker;

/**
 * A class to access ProgressChecker data stored as an xml file on the hard disk.
 */
public class XmlProgressCheckerStorage implements ProgressCheckerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlProgressCheckerStorage.class);

    private String filePath;

    public XmlProgressCheckerStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getProgressCheckerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyProgressChecker> readProgressChecker() throws DataConversionException, IOException {
        return readProgressChecker(filePath);
    }

    /**
     * Similar to {@link #readProgressChecker()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyProgressChecker> readProgressChecker(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File progressCheckerFile = new File(filePath);

        if (!progressCheckerFile.exists()) {
            logger.info("ProgressChecker file "  + progressCheckerFile + " not found");
            return Optional.empty();
        }

        XmlSerializableProgressChecker xmlProgressChecker = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlProgressChecker.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + progressCheckerFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveProgressChecker(ReadOnlyProgressChecker progressChecker) throws IOException {
        saveProgressChecker(progressChecker, filePath);
    }

    /**
     * Similar to {@link #saveProgressChecker(ReadOnlyProgressChecker)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveProgressChecker(ReadOnlyProgressChecker progressChecker, String filePath) throws IOException {
        requireNonNull(progressChecker);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableProgressChecker(progressChecker));
    }

}
