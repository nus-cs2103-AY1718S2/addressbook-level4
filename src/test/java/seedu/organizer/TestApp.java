package seedu.organizer;

import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.organizer.commons.core.Config;
import seedu.organizer.commons.core.GuiSettings;
import seedu.organizer.commons.exceptions.DataConversionException;
import seedu.organizer.commons.util.FileUtil;
import seedu.organizer.commons.util.XmlUtil;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;
import seedu.organizer.storage.UserPrefsStorage;
import seedu.organizer.storage.XmlSerializableOrganizer;
import seedu.organizer.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected static final String ORGANIZER_NAME = "Test";
    protected Supplier<ReadOnlyOrganizer> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyOrganizer> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableOrganizer(this.initialDataSupplier.get()),
                    this.saveFileLocation);
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
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setOrganizerFilePath(saveFileLocation);
        userPrefs.setOrganizerName(ORGANIZER_NAME);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the organizer data stored inside the storage file.
     */
    public Organizer readStorageOrganizer() {
        try {
            return new Organizer(storage.readOrganizer().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Organizer format.");
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.");
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public String getStorageSaveLocation() {
        return storage.getOrganizerFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getOrganizer()), new UserPrefs());
        try {
            copy.loginUser(ADMIN_USER);
        } catch (UserNotFoundException unf) {
            throw new AssertionError("Admin user should exist");
        } catch (CurrentlyLoggedInException cli) {
            throw new AssertionError("No user should be currently logged in");
        } catch (UserPasswordWrongException upw) {
            throw new AssertionError("Admin user password should not be wrong");
        }
        ModelHelper.setFilteredList(copy, model.getFilteredTaskList());
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

    /**
     * Login to admin
     */
    public void loginAdmin() {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException unf) {
            throw new AssertionError("Admin user should exist");
        } catch (CurrentlyLoggedInException cli) {
            throw new AssertionError("No user should be currently logged in");
        } catch (UserPasswordWrongException upw) {
            throw new AssertionError("Admin user password should not be wrong");
        }
    }
}
