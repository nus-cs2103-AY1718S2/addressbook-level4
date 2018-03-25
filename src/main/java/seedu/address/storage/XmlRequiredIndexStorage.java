package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.util.XmlUtil;
import seedu.address.model.RequiredStudentIndex;


public class XmlRequiredIndexStorage {

    private String filePath;

    public XmlRequiredIndexStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public static void updateData(int newIndex, String filePath) throws IOException {
        File file = new File(filePath);
        RequiredStudentIndex ris = new RequiredStudentIndex(newIndex);
        try{
            XmlUtil.saveDataToFile(file,ris );
        } catch (JAXBException e){
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }

    }
}
