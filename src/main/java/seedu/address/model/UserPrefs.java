package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String bookShelfFilePath = "data/bookshelf.xml";
    private String bookShelfName = "MyBookShelf";

    public UserPrefs() {
        guiSettings = new GuiSettings();
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

    public String getBookShelfFilePath() {
        return bookShelfFilePath;
    }

    public void setBookShelfFilePath(String bookShelfFilePath) {
        this.bookShelfFilePath = bookShelfFilePath;
    }

    public String getBookShelfName() {
        return bookShelfName;
    }

    public void setBookShelfName(String bookShelfName) {
        this.bookShelfName = bookShelfName;
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
                && Objects.equals(bookShelfFilePath, o.bookShelfFilePath)
                && Objects.equals(bookShelfName, o.bookShelfName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, bookShelfFilePath, bookShelfName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + bookShelfFilePath);
        sb.append("\nBookShelf name : " + bookShelfName);
        return sb.toString();
    }

}
