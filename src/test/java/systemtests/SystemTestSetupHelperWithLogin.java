package systemtests;

import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.testfx.api.FxToolkit;

import guitests.guihandles.MainWindowHandle;
import javafx.stage.Stage;
import seedu.address.TestAppWithLogin;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Contains helper methods that system tests require.
 */
public class SystemTestSetupHelperWithLogin {
    private TestAppWithLogin testAppWithLogin;
    private MainWindowHandle mainWindowHandle;

    /**
     * Sets up a new {@code TestApp} and returns it.
     */
    public TestAppWithLogin setupApplicationWithLogin(Supplier<ReadOnlyAddressBook> addressBook,
                                                      String saveFileLocation) {
        try {
            FxToolkit.registerStage(Stage::new);
            FxToolkit.setupApplication(() -> testAppWithLogin =
                    new TestAppWithLogin(addressBook, saveFileLocation));
        } catch (TimeoutException te) {
            throw new AssertionError("Application takes too long to set up.");
        }

        return testAppWithLogin;
    }

    /**
     * Initializes TestFX.
     */
    public static void initialize() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Encapsulates the primary stage of {@code TestApp} in a {@code MainWindowHandle} and returns it.
     */
    public MainWindowHandle setupMainWindowHandle() {
        try {
            FxToolkit.setupStage((stage) -> {
                mainWindowHandle = new MainWindowHandle(stage);
                mainWindowHandle.focus();
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to set up.");
        }

        return mainWindowHandle;
    }

    /**
     * Tears down existing stages.
     */
    public void tearDownStage() {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to tear down.");
        }
    }
}
