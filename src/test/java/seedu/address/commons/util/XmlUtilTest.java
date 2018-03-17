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

import seedu.address.model.IMDB;
import seedu.address.storage.XmlAdaptedPatient;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableIMDB;
import seedu.address.testutil.IMDBBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validIMDB.xml");
    private static final File MISSING_PERSON_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingPatientField.xml");
    private static final File INVALID_PERSON_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidPatientField.xml");
    private static final File VALID_PERSON_FILE = new File(TEST_DATA_FOLDER + "validPatient.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempIMDB.xml"));

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, IMDB.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, IMDB.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, IMDB.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        IMDB dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableIMDB.class).toModelType();
        assertEquals(9, dataFromFile.getPersonList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithMissingPersonField_validResult() throws Exception {
        XmlAdaptedPatient actualPerson = XmlUtil.getDataFromFile(
                MISSING_PERSON_FIELD_FILE, XmlAdaptedPatientWithRootElement.class);
        XmlAdaptedPatient expectedPerson = new XmlAdaptedPatient(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithInvalidPersonField_validResult() throws Exception {
        XmlAdaptedPatient actualPerson = XmlUtil.getDataFromFile(
                INVALID_PERSON_FIELD_FILE, XmlAdaptedPatientWithRootElement.class);
        XmlAdaptedPatient expectedPerson = new XmlAdaptedPatient(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithValidPerson_validResult() throws Exception {
        XmlAdaptedPatient actualPerson = XmlUtil.getDataFromFile(
                VALID_PERSON_FILE, XmlAdaptedPatientWithRootElement.class);
        XmlAdaptedPatient expectedPerson = new XmlAdaptedPatient(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new IMDB());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new IMDB());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableIMDB dataToWrite = new XmlSerializableIMDB(new IMDB());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableIMDB dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableIMDB.class);
        assertEquals(dataToWrite, dataFromFile);

        IMDBBuilder builder = new IMDBBuilder(new IMDB());
        dataToWrite = new XmlSerializableIMDB(
                builder.withPerson(new PatientBuilder().build()).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableIMDB.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedPatient}
     * objects.
     */
    @XmlRootElement(name = "patient")
    private static class XmlAdaptedPatientWithRootElement extends XmlAdaptedPatient {}
}
