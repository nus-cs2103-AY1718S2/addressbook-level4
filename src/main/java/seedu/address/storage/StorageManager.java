package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CoinBookChangedEvent;
import seedu.address.commons.events.model.RuleBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCoinBook;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of CoinBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CoinBookStorage coinBookStorage;
    private RuleBookStorage ruleBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CoinBookStorage coinBookStorage,
                          RuleBookStorage ruleBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.coinBookStorage = coinBookStorage;
        this.ruleBookStorage = ruleBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CoinBook methods ==============================

    @Override
    public String getCoinBookFilePath() {
        return coinBookStorage.getCoinBookFilePath();
    }

    @Override
    public Optional<ReadOnlyCoinBook> readCoinBook() throws DataConversionException, IOException {
        return readCoinBook(coinBookStorage.getCoinBookFilePath());
    }

    @Override
    public Optional<ReadOnlyCoinBook> readCoinBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return coinBookStorage.readCoinBook(filePath);
    }

    @Override
    public void saveCoinBook(ReadOnlyCoinBook coinBook) throws IOException {
        saveCoinBook(coinBook, coinBookStorage.getCoinBookFilePath());
    }

    @Override
    public void saveCoinBook(ReadOnlyCoinBook coinBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        coinBookStorage.saveCoinBook(coinBook, filePath);
    }

    //@@author laichengyu
    @Override
    public void backupCoinBook(ReadOnlyCoinBook coinBook) throws IOException {
        coinBookStorage.backupCoinBook(coinBook);
    }
    //@@author

    @Override
    @Subscribe
    public void handleCoinBookChangedEvent(CoinBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveCoinBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }


    // ================ RuleBook methods ==============================

    @Override
    public String getRuleBookFilePath() {
        return ruleBookStorage.getRuleBookFilePath();
    }

    @Override
    public Optional<ReadOnlyRuleBook> readRuleBook() throws DataConversionException, IOException {
        return readRuleBook(ruleBookStorage.getRuleBookFilePath());
    }

    @Override
    public Optional<ReadOnlyRuleBook> readRuleBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return ruleBookStorage.readRuleBook(filePath);
    }

    @Override
    public void saveRuleBook(ReadOnlyRuleBook ruleBook) throws IOException {
        saveRuleBook(ruleBook, ruleBookStorage.getRuleBookFilePath());
    }

    @Override
    public void saveRuleBook(ReadOnlyRuleBook ruleBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        ruleBookStorage.saveRuleBook(ruleBook, filePath);
    }

    @Override
    public void backupRuleBook(ReadOnlyRuleBook ruleBook) throws IOException {
        ruleBookStorage.backupRuleBook(ruleBook);
    }

    @Override
    @Subscribe
    public void handleRuleBookChangedEvent(RuleBookChangedEvent rbce) {
        logger.info(LogsCenter.getEventHandlingLogMessage(rbce, "Local data changed, saving to file"));
        try {
            saveRuleBook(rbce.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
