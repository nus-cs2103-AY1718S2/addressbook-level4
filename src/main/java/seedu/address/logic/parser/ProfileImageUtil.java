package seedu.address.logic.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.xml.bind.DatatypeConverter;

import seedu.address.model.person.ProfileImage;
import seedu.address.storage.DataFileStorage;

/**
 * Resolves the unreferenced profile image files in data folder
 */
public class ProfileImageUtil {
    /**
     * Processes the profileImage into the correct profileImage name
     * @param profileImage
     * @return processed profileImage
     * @throws IOException
     */
    public static ProfileImage process(ProfileImage profileImage) throws IOException {
        String filePath = profileImage.value;
        if (filePath == null) {
            return new ProfileImage(null);
        }
        String fullPath = System.getProperty("user.dir") + File.separator + filePath;
        assert(new File(fullPath).exists());
        String newFileName = getNewFileName(fullPath);
        transferProfileImageFileToDataFolder(fullPath, newFileName);
        return new ProfileImage("data" + File.separator + newFileName, profileImage.value);
    }
    private static String getNewFileName(String fullPath) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            throw new AssertionError("This should never happen.");
        }
        assert(md != null);
        File currprofileImageFile = new File(fullPath);
        assert(currprofileImageFile.exists() && currprofileImageFile.isFile());
        try {
            md.update(Files.readAllBytes(Paths.get(fullPath)));
        } catch (IOException ioe) {
            throw new AssertionError("This should never happen.");
        }
        byte[] digest = md.digest();
        return Long.toString(new Timestamp(System.currentTimeMillis()).getTime())
                + "_" + DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
    private static void transferProfileImageFileToDataFolder(String fullPath, String newFileName) throws IOException {
        DataFileStorage.copyResumeFileToDataFolder(fullPath, newFileName);
    }

    /**
     * cleans Data Folder when there is a DuplicatePersonException
     * @param profileImage the resume to be deleted in folder
     */
    public static void cleanUpDataFolder(ProfileImage profileImage) {
        if (profileImage.isHashed()) {
            DataFileStorage.deleteUnreferencedDataFile(profileImage.value);
        }
    }
}
