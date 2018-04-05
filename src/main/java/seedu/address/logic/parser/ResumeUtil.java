package seedu.address.logic.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.xml.bind.DatatypeConverter;

import seedu.address.model.person.Resume;
import seedu.address.storage.ResumeFileStorage;

/**
 * Handles resume file in the data folder
 */
public class ResumeUtil {
    /**
     * Processes the resume into the correct resume name
     * @param resume
     * @return processed resume
     * @throws IOException
     */
    public static Resume process(Resume resume) throws IOException {
        String filePath = resume.value;
        if (filePath == null) {
            return new Resume(null);
        }
        String fullPath = System.getProperty("user.dir") + File.separator + filePath;
        assert(new File(fullPath).exists());
        String newFileName = getNewFileName(fullPath);
        transferResumeFileToDataFolder(fullPath, newFileName);
        return new Resume("data" + File.separator + newFileName, resume.value);
    }
    private static String getNewFileName(String fullPath) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            throw new AssertionError("This should never happen.");
        }
        assert(md != null);
        File currResumeFile = new File(fullPath);
        assert(currResumeFile.exists() && currResumeFile.isFile());
        try {
            md.update(Files.readAllBytes(Paths.get(fullPath)));
        } catch (IOException ioe) {
            throw new AssertionError("This should never happen.");
        }
        byte[] digest = md.digest();
        return Long.toString(new Timestamp(System.currentTimeMillis()).getTime())
                + "_" + DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
    private static void transferResumeFileToDataFolder(String fullPath, String newFileName) throws IOException {
        ResumeFileStorage.copyResumeFileToDataFolder(fullPath, newFileName);
    }

    /**
     * cleans Data Folder when there is a DuplicatePersonException
     * @param resume the resume to be deleted in folder
     */
    public static void cleanUpDataFolder(Resume resume) {
        if (resume.isHashed()) {
            ResumeFileStorage.deleteUnreferencedResume(resume.value);
        }
    }
}
