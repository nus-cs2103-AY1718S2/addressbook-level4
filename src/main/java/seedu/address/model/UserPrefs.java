package seedu.address.model;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.google.common.hash.Hashing;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;

    private String addressBookFilePath = "data/myteammanager.xml";
    private String addressBookName = "MyTeamManager";
    private String addressBookTheme = "DarkTheme.css";
    private boolean addressBookLockState = false;
    private String addressBookHashedPass = Hashing.sha256()
            .hashString("ilikesports", StandardCharsets.UTF_8).toString();

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(String addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    //@@author lohtianwei
    public void lockAddressBook() {
        this.addressBookLockState = true;
    }

    public void unlockAddressBook() {
        this.addressBookLockState = false;
    }

    public String getAddressBookHashedPass() {
        return addressBookHashedPass;
    }

    public boolean getAddressBookLockState() {
        return this.addressBookLockState;
    }

    //@@author Codee
    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    public String getAddressBookTheme() {
        return addressBookTheme;
    }
    //@@author

    public void setAddressBookTheme(String addressBookTheme) {
        this.addressBookTheme = addressBookTheme;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(addressBookFilePath, o.addressBookFilePath)
                && Objects.equals(addressBookName, o.addressBookName)
                && Objects.equals(addressBookHashedPass, o.addressBookHashedPass)
                && Objects.equals(addressBookLockState, o.addressBookLockState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, addressBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nAddressBook name : " + addressBookName);
        sb.append("\nPassword: " + addressBookHashedPass);
        sb.append("\nLock State: " + Boolean.toString(this.addressBookLockState));
        return sb.toString();
    }

}
