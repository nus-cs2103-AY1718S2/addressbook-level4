package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PasswordAcceptedEvent;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.commons.util.EncryptionUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.UserPrefs;

//@@author limzk1994
public class PasswordManger {

    /**
     *
     * @param password user's password
     * @throws IOException if file could not be found or created
     */
    public static void savePassword(String password) throws IOException {
        requireNonNull(password);
        File file = new File(getFilePath());
        FileUtil.createIfMissing(file);
        FileUtil.writeToFile(file, password);
        EncryptionUtil.encrypt(file);
    }

    /**
     * Check whether to unlock the program
     * @param password
     * @return
     * @throws IOException
     */
    public static boolean verifyPassword(String password) throws IOException {
        boolean unlock = passwordCheck(password);
        if (unlock) {
           EventsCenter.getInstance().post(new PasswordAcceptedEvent());
        }
        return unlock;
    }

    /**
     * Removes existing password if user input the correct password
     * @param password oldpassword to be checked
     * @throws IOException if file does not exists
     */
    public static void removePassword(String password) throws IOException, WrongPasswordException {
        File file = new File(getFilePath());
        if (passwordCheck(password) && FileUtil.isFileExists(file)) {
            file.delete();
        } else {
            throw new WrongPasswordException();
        }
    }
    /**
     * Check if password is correct
     * @param password to be checked against records
     * @return true if password exists, vice-versa
     */
    public static boolean passwordCheck(String password) throws IOException {
        String storedPassword = getPassword();
        return storedPassword.equals(password);
    }
    /**
     * Check if the password exists
     * @return true if password exists, vice-versa
     */
    public static boolean passwordExists() {
        File file = new File(getFilePath());
        return FileUtil.isFileExists(file);
    }
    /**
     * Method to get the password
     * @return password
     * @throws IOException if file could not be found
     */
    public static String getPassword() throws IOException {
        File file = new File(getFilePath());
        EncryptionUtil.decrypt(file);
        String password = FileUtil.readFromFile(file);
        EncryptionUtil.encrypt(file);
        return password;
    }

    /**
     * Method to get the file path of password
     * @return file path
     */
    public static String getFilePath() {
        UserPrefs userPrefs = new UserPrefs();
        String filePath = userPrefs.getPasswordFilePath();

        return filePath;
    }
}
