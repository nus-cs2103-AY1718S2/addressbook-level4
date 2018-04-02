package seedu.address.storage;

import java.io.File;

/**
 * Stores the profile pictures of students
 */
public class ProfilePictureStorage {

    private final String filePath;

    public ProfilePictureStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns true is the current storage file with {@code filePath} exists
     */
    public boolean storageFileExist(){
        File pictureStorage = new File(this.filePath);
        return pictureStorage.exists();
    }

    public String getFilePath(){
        return this.filePath;
    }


}
