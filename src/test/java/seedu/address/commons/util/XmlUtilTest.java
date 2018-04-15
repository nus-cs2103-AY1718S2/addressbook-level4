package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.storage.XmlAdaptedGroup;
import seedu.address.storage.XmlAdaptedPerson;
import seedu.address.storage.XmlAdaptedPreference;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.storage.XmlSerializableCalendarManager;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.CalendarEntryBuilder;
import seedu.address.testutil.CalendarManagerBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validAddressBook.xml");
    private static final File MISSING_PERSON_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingPersonField.xml");
    private static final File INVALID_PERSON_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidPersonField.xml");
    private static final File VALID_PERSON_FILE = new File(TEST_DATA_FOLDER + "validPerson.xml");
    private static final File TEMP_ADDRESSBOOK_FILE =
            new File(TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml"));
    private static final File TEMP_CALENDARMANAGER_FILE =
            new File(TestUtil.getFilePathInSandboxFolder("tempCalendarManager.xml"));

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedGroup> VALID_GROUPS =
            Collections.singletonList(new XmlAdaptedGroup("friends"));
    private static final List<XmlAdaptedPreference> VALID_PREFERENCES =
            Collections.singletonList(new XmlAdaptedPreference("shoes"));
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, AddressBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        AddressBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableAddressBook.class).toModelType();
        assertEquals(9, dataFromFile.getPersonList().size());
        assertEquals(0, dataFromFile.getGroupList().size());
        assertEquals(0, dataFromFile.getPreferenceList().size());

    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithMissingPersonField_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                MISSING_PERSON_FIELD_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_GROUPS, VALID_PREFERENCES);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithInvalidPersonField_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                INVALID_PERSON_FIELD_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_GROUPS, VALID_PREFERENCES);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithValidPerson_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                VALID_PERSON_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_GROUPS, VALID_PREFERENCES);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new AddressBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new AddressBook());
    }

    @Test
    public void saveDataToFile_validAddressBookFile_dataSaved() throws Exception {
        TEMP_ADDRESSBOOK_FILE.createNewFile();
        XmlSerializableAddressBook dataToWrite = new XmlSerializableAddressBook(new AddressBook());
        XmlUtil.saveDataToFile(TEMP_ADDRESSBOOK_FILE, dataToWrite);
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TEMP_ADDRESSBOOK_FILE,
                XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);

        AddressBookBuilder builder = new AddressBookBuilder(new AddressBook());
        dataToWrite = new XmlSerializableAddressBook(
                builder.withPerson(new PersonBuilder().build()).withGroup("Friends")
                        .withPreference("shoes").build());

        XmlUtil.saveDataToFile(TEMP_ADDRESSBOOK_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_ADDRESSBOOK_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    //@@author SuxianAlicia
    @Test
    public void saveDateToFile_validCalendarManagerFile_dataSaved() throws Exception {
        TEMP_CALENDARMANAGER_FILE.createNewFile();
        XmlSerializableCalendarManager dataToWrite = new XmlSerializableCalendarManager(new CalendarManager());
        XmlUtil.saveDataToFile(TEMP_CALENDARMANAGER_FILE, dataToWrite);
        XmlSerializableCalendarManager dataFromFile = XmlUtil.getDataFromFile(
                TEMP_CALENDARMANAGER_FILE, XmlSerializableCalendarManager.class);
        assertEquals(dataToWrite, dataFromFile);

        CalendarManagerBuilder builder = new CalendarManagerBuilder(new CalendarManager());
        dataToWrite = new XmlSerializableCalendarManager(builder.withEntry(new CalendarEntryBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_CALENDARMANAGER_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_CALENDARMANAGER_FILE, XmlSerializableCalendarManager.class);
        assertEquals(dataToWrite, dataFromFile);
    }
    //@@author

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedPerson}
     * objects.
     */
    @XmlRootElement(name = "person")
    private static class XmlAdaptedPersonWithRootElement extends XmlAdaptedPerson {}
}
