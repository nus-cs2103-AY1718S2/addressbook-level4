package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.RecipeBook;
import seedu.address.testutil.TypicalPersons;

public class XmlSerializableRecipeBookTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableRecipeBookTest/");
    private static final File TYPICAL_PERSONS_FILE = new File(TEST_DATA_FOLDER + "typicalPersonsRecipeBook.xml");
    private static final File INVALID_PERSON_FILE = new File(TEST_DATA_FOLDER + "invalidPersonRecipeBook.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagRecipeBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableRecipeBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableRecipeBook.class);
        RecipeBook recipeBookFromFile = dataFromFile.toModelType();
        RecipeBook typicalPersonsRecipeBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(recipeBookFromFile, typicalPersonsRecipeBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRecipeBook dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableRecipeBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRecipeBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableRecipeBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
