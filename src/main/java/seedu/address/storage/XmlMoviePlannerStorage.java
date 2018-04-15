package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyMoviePlanner;

/**
 * A class to access MoviePlanner data stored as an xml file on the hard disk.
 */
public class XmlMoviePlannerStorage implements MoviePlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlMoviePlannerStorage.class);

    private String filePath;
    private String encryptedFilePath;

    public XmlMoviePlannerStorage(String filePath) {
        this.filePath = filePath;
    }
    //@@author tinyjy94
    public XmlMoviePlannerStorage(String filePath, String encryptedFilePath) {
        this.filePath = filePath;
        this.encryptedFilePath = encryptedFilePath;
    }
    //@@author
    public String getMoviePlannerFilePath() {
        return filePath;
    }

    public String getEncryptedMoviePlannerFilePath() {
        return encryptedFilePath;
    }

    @Override
    public Optional<ReadOnlyMoviePlanner> readMoviePlanner() throws DataConversionException, IOException {
        return readMoviePlanner(filePath, encryptedFilePath);
    }

    /**
     * Similar to {@link #readMoviePlanner()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
    */
    public Optional<ReadOnlyMoviePlanner> readMoviePlanner(String filePath) throws DataConversionException,
            IOException {
        requireNonNull(filePath);

        File moviePlannerFile = new File(filePath);

        if (!moviePlannerFile.exists()) {
            logger.info("MoviePlanner file " + moviePlannerFile + " not found");
            return Optional.empty();
        }

        XmlSerializableMoviePlanner xmlMoviePlanner = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlMoviePlanner.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + moviePlannerFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
    //@@author tinyjy94
    /**
     * Similar to {@link #readMoviePlanner()}
     *
     * @param filePath location of the data. Cannot be null
     * @param encryptedFilePath location of the encrypted data. Returns empty String if null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMoviePlanner> readMoviePlanner(String filePath, String encryptedFilePath)
            throws DataConversionException, IOException {
        requireNonNull(filePath);

        File moviePlannerFile = new File(filePath);
        if (encryptedFilePath == null) {
            encryptedFilePath = "";
        }
        File moviePlannerEncryptedFile = new File(encryptedFilePath);

        if (!moviePlannerFile.exists()) {
            if (!moviePlannerEncryptedFile.exists()) {
                logger.info("MoviePlanner file " + moviePlannerFile + " not found");
                return Optional.empty();
            } else {
                return Optional.empty();
            }
        }

        XmlSerializableMoviePlanner xmlMoviePlanner = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlMoviePlanner.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + moviePlannerFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
    //@@author
    @Override
    public void saveMoviePlanner(ReadOnlyMoviePlanner moviePlanner) throws IOException {
        saveMoviePlanner(moviePlanner, filePath);
    }

    /**
     * Similar to {@link #saveMoviePlanner(ReadOnlyMoviePlanner)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveMoviePlanner(ReadOnlyMoviePlanner moviePlanner, String filePath) throws IOException {
        requireNonNull(moviePlanner);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableMoviePlanner(moviePlanner));
    }

    @Override
    public void backupMoviePlanner(ReadOnlyMoviePlanner moviePlanner) throws IOException {
        saveMoviePlanner(moviePlanner, filePath + ".backup");
    }

}
