# traceurgan
###### \java\seedu\address\logic\commands\NewJournalCommandTest.java
``` java
public class NewJournalCommandTest {

    @Test
    public void execute_newjournal_success() {
        CommandResult result = new NewJournalCommand().execute();
        assertEquals(NEW_JOURNAL_ENTRY_CREATED, result.feedbackToUser);
    }
}
```
###### \java\seedu\address\model\journalentry\DateTest.java
``` java
public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid phone numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("91")); // less than 3 numbers
        assertFalse(Date.isValidDate("phone")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Date.isValidDate("93121534")); //exactly 8 digits
    }
}
```
###### \java\seedu\address\model\JournalTest.java
``` java
public class JournalTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Journal journal = new Journal();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), journal.getJournalEntryList());
    }
    @Test
    public void resetJournalData_nullData() throws Exception {
        thrown.expect(NullPointerException.class);
        journal.resetJournalData(null);
    }

    @Test
    public void resetData_withValidReadOnlyJournal_replacesData() {
        Journal newData = getTypicalJournal();
        journal.resetJournalData(newData);
        assertEquals(newData, journal);
    }

    @Test
    public void getJournalEntryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        journal.getJournalEntryList().remove(0);
    }
}
```
###### \java\seedu\address\storage\XmlJournalStorageTest.java
``` java
public class XmlJournalStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlJournalStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private java.util.Optional<ReadOnlyJournal> readJournal(String filePath) throws Exception {
        return new XmlJournalStorage(filePath).readJournal(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void readJournal_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readJournal(null);

    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readJournal("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readJournal("NotXmlFormatJournal.xml");
    }

    @Test
    public void readAndSaveJournal_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempJournal.xml";
        Journal original = getTypicalJournal();
        XmlJournalStorage xmlJournalStorage = new XmlJournalStorage(filePath);

        //Save in new file and read back
        xmlJournalStorage.saveJournal(original, filePath);
        ReadOnlyJournal readBack = xmlJournalStorage.readJournal(filePath).get();
        assertEquals(original, new Journal(readBack));

        //Save and read without specifying file path
        original.addJournalEntry(TEST);
        xmlJournalStorage.saveJournal(original); //file path not specified
        readBack = xmlJournalStorage.readJournal().get(); //file path not specified
        assertEquals(original, new Journal(readBack));

    }

    @Test
    public void saveJournal_nullJournal_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveJournal(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveJournal(ReadOnlyJournal journal, String filePath) {
        try {
            new XmlJournalStorage(filePath).saveJournal(journal, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveJournal_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveJournal(new Journal(), null);
    }


}
```
###### \java\seedu\address\storage\XmlSerializableJournalTest.java
``` java
public class XmlSerializableJournalTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath(
            "src/test/data/XmlSerializableJournalTest/");
    private static final File TYPICAL_JOURNALENTRIES_FILE =
            new File(TEST_DATA_FOLDER + "typicalJournalEntriesJournalTest.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalJournalEntriesFile_success() throws Exception {
        XmlSerializableJournal dataFromFile = XmlUtil.getDataFromFile(TYPICAL_JOURNALENTRIES_FILE,
                XmlSerializableJournal.class);
        Journal journalFromFile = dataFromFile.toModelType();
        Journal typicalJournalEntriesJournal = TypicalJournalEntries.getTypicalJournal();
        assertEquals(journalFromFile, typicalJournalEntriesJournal);
    }
}
```
###### \java\seedu\address\testutil\TypicalJournalEntries.java
``` java
/**
 * A utility class containing a list of {@code JournalEntry} objects to be used in tests.
 */
public class TypicalJournalEntries {

    public static final JournalEntry SAMPLE_ONE = new JournalEntry(new Date("20180101"), "Sample text here.");

    public static final JournalEntry SAMPLE_TWO = new JournalEntry(new Date("20181001"), "Sample two here.");

    public static final JournalEntry SAMPLE_THREE = new JournalEntry(new Date("20181111"), "Sample three text.");

    //for manual adding during tests
    public static final JournalEntry TEST = new JournalEntry(new Date("20180328"), "Testing.");

    private TypicalJournalEntries() {} // prevents instantiation

    /**
     * Returns an {@code Journal} with all the typical journal entries.
     */
    public static Journal getTypicalJournal() {
        Journal j = new Journal();
        for (JournalEntry journalEntry : getTypicalJournalEntries()) {
            try {
                j.addJournalEntry(journalEntry);
            } catch (Exception e) {
                throw new AssertionError("not possible");
            }
        }
        return j;
    }

    public static List<JournalEntry> getTypicalJournalEntries() {
        return new ArrayList<>(Arrays.asList(SAMPLE_ONE, SAMPLE_TWO, SAMPLE_THREE));
    }


}
```
###### \java\seedu\address\ui\JournalWindowTest.java
``` java
public class JournalWindowTest extends GuiUnitTest {

    private JournalWindow journalWindow;
    private JournalWindowHandle journalWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> journalWindow = new JournalWindow(new Date("20180405")));
        Stage journalWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(journalWindow.getRoot().getScene()));
        FxToolkit.showStage();
        journalWindowHandle = new JournalWindowHandle(journalWindowStage);
    }

    @Test
    public void isShowing_journalWindowIsShowing_true() {
        guiRobot.interact(() -> journalWindow.show());
        assertTrue(journalWindow.isShowing());
    }
}
```
