package seedu.address.storage;

import static seedu.address.ui.BrowserPanel.STUDENT_INFO_PAGE_STYLESHEET;
import static seedu.address.ui.BrowserPanel.STUDENT_MISC_INFO_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import seedu.address.MainApp;
import seedu.address.commons.util.FileUtil;
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

    /**
     * Creates the resource files needed to display the student info page.
     */
    private static void createViewResourceFile(String requiredIndexStorageFilePath) throws IOException {
        FileUtil.createIfMissing(new File(requiredIndexStorageFilePath));
        FileUtil.createIfMissing(new File("data/view/" + STUDENT_MISC_INFO_PAGE));
        FileUtil.createIfMissing(new File("data/view/" + STUDENT_INFO_PAGE_STYLESHEET));
        FileUtil.createIfMissing(new File("data/view/profile_photo_placeholder.png"));
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
        if (!file.exists()) {
            createViewResourceFile(filePath);
            exportResource("data/view/" + STUDENT_MISC_INFO_PAGE);
            exportResource("data/view/" + STUDENT_INFO_PAGE_STYLESHEET);
            exportResource("data/view/" + "profile_photo_placeholder.png");

        }
        RequiredStudentIndex ris = new RequiredStudentIndex(newIndex);
        try {
            XmlUtil.saveDataToFile(file, ris);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }

    }

    /**
     * Exports the resources from the jar file to the directory of the  addressBook data
     */
    private static void exportResource(String resourceName) throws IOException {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        String resourcePage = resourceName.substring(10);
        try {
            stream = MainApp.class.getResourceAsStream(FXML_FILE_FOLDER + resourcePage);
            if (stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath()).getParentFile().getPath().replace('\\', '/');

            resStreamOut = new FileOutputStream(jarFolder + "/" + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            stream.close();
            resStreamOut.close();
        }

    }

}
