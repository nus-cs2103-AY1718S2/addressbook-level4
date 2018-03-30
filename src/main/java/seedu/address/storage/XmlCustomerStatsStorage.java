package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
