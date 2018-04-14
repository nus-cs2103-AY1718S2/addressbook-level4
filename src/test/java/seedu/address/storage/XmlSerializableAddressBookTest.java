package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalAliases;
import seedu.address.testutil.TypicalPersons;

public class XmlSerializableAddressBookTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableAddressBookTest/");
    private static final File TYPICAL_PERSONS_FILE = new File(TEST_DATA_FOLDER + "typicalPersonsAddressBook.xml");
    private static final File TYPICAL_ALIASES_FILE = new File(TEST_DATA_FOLDER + "typicalAliasesAddressBook.xml");
    private static final File INVALID_PERSON_FILE = new File(TEST_DATA_FOLDER + "invalidPersonAddressBook.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagAddressBook.xml");
    private static final File INVALID_ALIAS_FILE = new File(TEST_DATA_FOLDER + "invalidAliasAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    //@@author Caijun7
    @Test
    public void toModelType_typicalAliasesFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ALIASES_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalAliasesAddressBook = TypicalAliases.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalAliasesAddressBook);
    }
    //@@author

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    //@@author Caijun7
    @Test
    public void toModelType_invalidAliasFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ALIAS_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void addToAddressBook_typicalPersonsFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = new AddressBook();
        dataFromFile.addToAddressBook(addressBookFromFile);
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void addToAddressBook_typicalAliasesFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ALIASES_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = new AddressBook();
        dataFromFile.addToAddressBook(addressBookFromFile);
        AddressBook typicalAliasesAddressBook = TypicalAliases.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalAliasesAddressBook);
    }

    @Test
    public void addToAddressBook_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.addToAddressBook(new AddressBook());
    }

    @Test
    public void addToAddressBook_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.addToAddressBook(new AddressBook());
    }

    @Test
    public void addToAddressBook_invalidAliasFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ALIAS_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.addToAddressBook(new AddressBook());
    }
    //@@author
}
