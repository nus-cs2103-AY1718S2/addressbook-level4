package seedu.address;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendarManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.storage.XmlSerializableCalendarManager;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleAbData.xml");
    public static final String SAVE_LOCATION_FOR_CALENDAR_TESTING =
            TestUtil.getFilePathInSandboxFolder("sampleCalendarData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected static final String ADDRESS_BOOK_NAME = "Test";
    protected static final String CALENDAR_MANAGER_NAME = "Calendar Test";
    protected Supplier<ReadOnlyAddressBook> initialDataSupplier = () -> null;
    protected Supplier<ReadOnlyCalendarManager> initialCalDataSupplier = () -> null;
    protected String saveAbFileLocation = SAVE_LOCATION_FOR_TESTING;
    protected String saveCmFileLocation = SAVE_LOCATION_FOR_CALENDAR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyAddressBook> initialDataSupplier,
                   Supplier<ReadOnlyCalendarManager> initialCalDataSupplier, String saveAbFileLocation,
                   String saveCmFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.initialCalDataSupplier = initialCalDataSupplier;
        this.saveAbFileLocation = saveAbFileLocation;
        this.saveCmFileLocation = saveCmFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableAddressBook(this.initialDataSupplier.get()),
                    this.saveAbFileLocation);
        }

        if (initialCalDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableCalendarManager(this.initialCalDataSupplier.get()),
                    this.saveCmFileLocation);
        }
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(1350.0, 600.0, (int) x, (int) y));
        userPrefs.setAddressBookFilePath(saveAbFileLocation);
        userPrefs.setCalendarManagerFilePath(saveCmFileLocation);
        userPrefs.setAddressBookName(ADDRESS_BOOK_NAME);
        userPrefs.setCalendarManagerName(CALENDAR_MANAGER_NAME);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public AddressBook readStorageAddressBook() {
        try {
            return new AddressBook(storage.readAddressBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the AddressBook format.");
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.");
        }
    }

    /**
     * Returns a defensive copy of the calendar manager data stored inside the storage file.
     */
    public CalendarManager readStorageCalendarManager() {
        try {
            return new CalendarManager(storage.readCalendarManager().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the CalendarManager format.");
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.");
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public String getStorageSaveLocation() {
        return storage.getAddressBookFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getAddressBook()), model.getCalendarManager(), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
