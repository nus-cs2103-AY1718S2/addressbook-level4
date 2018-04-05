package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import seedu.address.commons.core.LogsCenter;

/**
 * Handles file operation for resume folder
 */
public class ResumeFileStorage {
    private static final String JAR_FOLDER = System.getProperty("user.dir");
    private static final String DATA_FOLDER = JAR_FOLDER + File.separator + "data";

    /**
     * Copies the resume file to the data folder
     * @param fullPath of resume file
     * @param newFileName of destination file
     * @throws IOException
     */
    public static void copyResumeFileToDataFolder(String fullPath, String newFileName) throws IOException {
        File srcFile = new File(fullPath);
        assert(srcFile.exists());
        ensureDataFolderExists();
        Files.copy(srcFile.toPath(), formDestPath(newFileName), StandardCopyOption.REPLACE_EXISTING);
    }

    private static Path formDestPath(String newFileName) {
        return new File(DATA_FOLDER + File.separator + newFileName).toPath();
    }

    /**
     * Ensures the data folder exists
     * @throws IOException
     */
    private static void ensureDataFolderExists() throws IOException {
        File dataFolder = new File(DATA_FOLDER);
        if (dataFolder.exists() && dataFolder.isDirectory()) {
            return;
        } else {
            boolean hasCreated = dataFolder.mkdirs();
            if (!hasCreated) {
                throw new IOException("HR+ cannot create data folder in the same directory as the jar file!");
            }
        }
    }

    /**
     * Deletes the unreferenced resume
     * @param resumeFileName
     */
    public static void deleteUnreferencedResume(String resumeFileName) {
        File unreferencedResume = new File(JAR_FOLDER + File.separator + resumeFileName);
        assert(unreferencedResume.exists() && unreferencedResume.isFile());
        boolean isDeleted = unreferencedResume.delete();
        if (!isDeleted) {
            LogsCenter.getLogger(ResumeFileStorage.class)
                    .warning("Clean up of Unreferenced Resume file unsuccessful.");
        }
    }
}
