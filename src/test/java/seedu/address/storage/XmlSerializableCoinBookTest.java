package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.CoinBook;
import seedu.address.testutil.TypicalCoins;

public class XmlSerializableCoinBookTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableCoinBookTest/");
    private static final File TYPICAL_COINS_FILE = new File(TEST_DATA_FOLDER + "typicalCoinsCoinBook.xml");
    private static final File INVALID_COIN_FILE = new File(TEST_DATA_FOLDER + "invalidCoinCoinBook.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagCoinBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalCoinsFile_success() throws Exception {
        XmlSerializableCoinBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_COINS_FILE,
                XmlSerializableCoinBook.class);
        CoinBook addressBookFromFile = dataFromFile.toModelType();
        CoinBook typicalCoinsAddressBook = TypicalCoins.getTypicalCoinBook();
        assertEquals(addressBookFromFile, typicalCoinsAddressBook);
    }

    @Test
    public void toModelType_invalidCoinFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCoinBook dataFromFile = XmlUtil.getDataFromFile(INVALID_COIN_FILE,
                XmlSerializableCoinBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCoinBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableCoinBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
