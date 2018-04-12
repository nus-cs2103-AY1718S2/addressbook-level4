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

import seedu.address.model.CoinBook;
import seedu.address.storage.XmlAdaptedCoin;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableCoinBook;
import seedu.address.testutil.CoinBookBuilder;
import seedu.address.testutil.CoinBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validCoinBook.xml");
    private static final File MISSING_COIN_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingCoinField.xml");
    private static final File INVALID_COIN_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidCoinField.xml");
    private static final File VALID_COIN_FILE = new File(TEST_DATA_FOLDER + "validCoin.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempCoinBook.xml"));

    private static final String VALID_NAME = "ABC";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("favs"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, CoinBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, CoinBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, CoinBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        CoinBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableCoinBook.class).toModelType();
        assertEquals(9, dataFromFile.getCoinList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedCoinFromFile_fileWithMissingCoinField_validResult() throws Exception {
        XmlAdaptedCoin actualCoin = XmlUtil.getDataFromFile(
                MISSING_COIN_FIELD_FILE, XmlAdaptedCoinWithRootElement.class);
        XmlAdaptedCoin expectedCoin = new XmlAdaptedCoin(
                null, VALID_TAGS);
        assertEquals(expectedCoin, actualCoin);
    }

    @Test
    public void xmlAdaptedCoinFromFile_fileWithValidCoin_validResult() throws Exception {
        XmlAdaptedCoin actualCoin = XmlUtil.getDataFromFile(
                VALID_COIN_FILE, XmlAdaptedCoinWithRootElement.class);
        XmlAdaptedCoin expectedCoin = new XmlAdaptedCoin(
                VALID_NAME, VALID_TAGS);
        assertEquals(expectedCoin, actualCoin);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws
            Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new CoinBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new CoinBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableCoinBook dataToWrite = new XmlSerializableCoinBook(new CoinBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableCoinBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableCoinBook.class);
        assertEquals(dataToWrite, dataFromFile);

        CoinBookBuilder builder = new CoinBookBuilder(new CoinBook());
        dataToWrite = new XmlSerializableCoinBook(
                builder.withCoin(new CoinBuilder().build()).withTag("active").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableCoinBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedCoin}
     * objects.
     */
    @XmlRootElement(name = "coin")
    private static class XmlAdaptedCoinWithRootElement extends XmlAdaptedCoin {}
}
