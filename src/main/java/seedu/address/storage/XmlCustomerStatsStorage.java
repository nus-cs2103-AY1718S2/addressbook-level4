package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyCustomerStats;

/**
 * A class to access CustomerStats data stored as an xml file on the hard disk.
 */
public class XmlCustomerStatsStorage implements CustomerStatsStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCustomerStatsStorage.class);

    private String filePath;

    public XmlCustomerStatsStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getCustomerStatsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCustomerStats> readCustomerStats() throws DataConversionException, IOException {
        return readCustomerStats(filePath);
    }

    /**
     * Similar to {@link #readCustomerStats()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCustomerStats> readCustomerStats(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File customerStatsFile = new File(filePath);

        if (!customerStatsFile.exists()) {
            logger.info("CustomerStats file "  + customerStatsFile + " not found");
            return Optional.empty();
        }

        XmlSerializableCustomerStats xmlCustomerStats = XmlFileStorage.loadCustomerDataFromSaveFile(new File(filePath));
        return Optional.of(xmlCustomerStats.toModelType());
    }

    @Override
    public void saveCustomerStats(ReadOnlyCustomerStats customerStats) throws IOException {
        saveCustomerStats(customerStats, filePath);
    }

    /**
     * Similar to {@link #saveCustomerStats(ReadOnlyCustomerStats)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveCustomerStats(ReadOnlyCustomerStats customerStats, String filePath) throws IOException {
        requireNonNull(customerStats);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveCustomerDataToFile(file, new XmlSerializableCustomerStats(customerStats));
    }
}
