package seedu.address.storage;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.util.XmlUtil;
import seedu.address.model.RequiredStudentIndex;

/**
 * Stores the XML data of the required index of the particular student.
 */
public class XmlRequiredIndexStorage {

    private String filePath;

    public XmlRequiredIndexStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * Updates the XML file with the new required index.
     * @param newIndex
     * @param filePath
     * @throws IOException
     */
    public static void updateData(int newIndex, String filePath) throws IOException {
        File file = new File(filePath);
        RequiredStudentIndex ris = new RequiredStudentIndex(newIndex);
        try {
            XmlUtil.saveDataToFile(file, ris);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }

    }
}
