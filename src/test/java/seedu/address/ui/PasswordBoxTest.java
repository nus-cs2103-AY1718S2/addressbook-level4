package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import guitests.guihandles.PasswordBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.commons.events.ui.PasswordCorrectEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Password;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.ReadOnlyJsonVenueInformation;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.ui.testutil.EventsCollectorRule;

public class PasswordBoxTest extends GuiUnitTest {
    private static final String CORRECT_PASSWORD = "test";
    private static final String WRONG_PASSWORD = "wrong";
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/PasswordBoxTest/");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private ArrayList<String> defaultStyleOfPasswordBox;
    private ArrayList<String> errorStyleOfPasswordBox;
    private PasswordBoxHandle passwordBoxHandle;


    @Before
    public void setUp() throws Exception {

        Storage storageManager = setUpStorage();
        Model model = new ModelManager(storageManager.readAddressBook(new Password(CORRECT_PASSWORD)).get());

        PasswordBox commandBox = new PasswordBox(storageManager, model);
        passwordBoxHandle = new PasswordBoxHandle(getChildNode(commandBox.getRoot(),
                PasswordBoxHandle.PASSWORD_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfPasswordBox = new ArrayList<>(passwordBoxHandle.getStyleClass());

        errorStyleOfPasswordBox = new ArrayList<>(defaultStyleOfPasswordBox);
        errorStyleOfPasswordBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    private String getTestFilePath(String fileName) {
        return TEST_DATA_FOLDER + fileName;
    }
    private Storage setUpStorage() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTestFilePath(
                                                                            "encryptedAddressBook.xml"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTestFilePath("prefs"));
        ReadOnlyJsonVenueInformation venueInformationStorage = new ReadOnlyJsonVenueInformation("vi");
        return new StorageManager(addressBookStorage, userPrefsStorage, venueInformationStorage);
    }

    @Test
    public void passwordBox_startingWithWrongPassword() {
        assertBehaviorForWrongPassword();
    }

    @Test
    public void passwordBox_startingWithCorrectPassword() {
        assertBehaviorForCorrectPassword();
    }

    @Test
    public void passwordBox_handleKeyPress() {
        passwordBoxHandle.run(WRONG_PASSWORD);
        assertEquals(errorStyleOfPasswordBox, passwordBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfPasswordBox, passwordBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfPasswordBox, passwordBoxHandle.getStyleClass());
    }

    /**
     * Use a wrong password, then verifies that <br>
     *      - the text remains resets <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForWrongPassword() {
        passwordBoxHandle.run(WRONG_PASSWORD);
        assertEquals("", passwordBoxHandle.getInput());
        assertEquals(errorStyleOfPasswordBox, passwordBoxHandle.getStyleClass());
    }

    /**
     * Enters the correct password, then verifies that <br>
     *      - the text is cleared <br>
     *      - the event {@code PasswordCorrectEvent} is raised.
     */
    private void assertBehaviorForCorrectPassword() {
        passwordBoxHandle.run(CORRECT_PASSWORD);
        assertEquals("", passwordBoxHandle.getInput());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof PasswordCorrectEvent);
    }
}
