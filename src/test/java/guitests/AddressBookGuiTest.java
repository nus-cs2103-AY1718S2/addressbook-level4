package guitests;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.PersonPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.testutil.TypicalCalendarEntries;
import seedu.address.testutil.TypicalPersons;

/**
 * A GUI Test class for AddressBook.
 * Copied from CS2103T AddressBook of AY17/18 S1
 */
public abstract class AddressBookGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    protected GuiRobot guiRobot = new GuiRobot();

    protected Stage stage;

    protected MainWindowHandle mainWindowHandle;

    @BeforeClass
    public static void setupOnce() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            this.stage = stage;
        });
        FxToolkit.setupApplication(() -> new TestApp(this::getInitialAbData, this::getInitialCmData,
                getAbDataFileLocation(), getCmDataFileLocation()));
        FxToolkit.showStage();

        mainWindowHandle = new MainWindowHandle(stage);
        mainWindowHandle.focus();
    }

    /**
     * Override this in child classes to set the initial local data of address book.
     * Return null to use the data in the file specified in {@link #getAbDataFileLocation()}
     */
    protected AddressBook getInitialAbData() {
        return TypicalPersons.getTypicalAddressBook();
    }

    /**
     * Override this in child classes to set the initial local data of calendar manager.
     * Return null to use the data in the file specified in {@link #getAbDataFileLocation()}
     */
    protected CalendarManager getInitialCmData() {
        return TypicalCalendarEntries.getTypicalCalendarManagerWithEntries();
    }

    protected CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    protected PersonListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    protected MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    protected PersonPanelHandle getPersonPanel() {
        return mainWindowHandle.getPersonPanel();
    }

    protected StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    protected ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }


    /**
     * Runs {@code command} in the application's {@code CommandBox}.
     * @return true if the command was executed successfully.
     */
    protected boolean runCommand(String command) {
        return mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Override this in child classes to set the Address book data file location.
     */
    protected String getAbDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    /**
     * Override this in child classes to set the Calendar Manager data file location.
     */
    protected String getCmDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_CALENDAR_TESTING;
    }

    @After
    public void cleanup() throws Exception {
        EventsCenter.clearSubscribers();
        FxToolkit.cleanupStages();
    }
}
