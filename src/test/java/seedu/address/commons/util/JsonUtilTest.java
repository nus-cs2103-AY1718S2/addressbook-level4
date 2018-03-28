package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.testutil.SerializableTestClass;
import seedu.address.testutil.TestUtil;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    private static final File SERIALIZATION_FILE = new File(TestUtil.getFilePathInSandboxFolder("serialize.json"));
    private static final File SERIALIZATION_ARRAY_FILE = new File(TestUtil.getFilePathInSandboxFolder(
            "serializeArray.json"));

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = JsonUtil
                .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    @Test
    public void deserializeArrayFromJsonFile_noExceptionThrown() throws DataConversionException, IOException {
        FileUtil.writeToFile(SERIALIZATION_ARRAY_FILE, "[" + SerializableTestClass.JSON_STRING_REPRESENTATION
                + "," + SerializableTestClass.JSON_STRING_REPRESENTATION + "]");

        List<SerializableTestClass> serializableTestClassList = JsonUtil
                .readJsonArrayFromFile(SERIALIZATION_ARRAY_FILE.getPath(), SerializableTestClass.class);
        List<SerializableTestClass> expected = new ArrayList<>();

        SerializableTestClass serializableTestClass1 = new SerializableTestClass();
        SerializableTestClass serializableTestClass2 = new SerializableTestClass();
        serializableTestClass1.setTestValues();
        serializableTestClass2.setTestValues();
        expected.add(serializableTestClass1);
        expected.add(serializableTestClass2);

        assertEquals(expected.size(), serializableTestClassList.size());

        for (int i = 0; i < expected.size(); i++) {
            SerializableTestClass expectedObject = expected.get(i);
            SerializableTestClass actualObject = serializableTestClassList.get(i);

            assertEquals(expectedObject.getName(), actualObject.getName());
            assertEquals(expectedObject.getListOfLocalDateTimes(), actualObject.getListOfLocalDateTimes());
            assertEquals(expectedObject.getMapOfIntegerToString(), actualObject.getMapOfIntegerToString());
        }
    }

    //TODO: @Test jsonUtil_readJsonStringToObjectInstance_correctObject()

    //TODO: @Test jsonUtil_writeThenReadObjectToJson_correctObject()
}
