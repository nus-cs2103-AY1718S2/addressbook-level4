package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;

//@@author lithiumlkid
/**
 * Represents a Player's avatar in the address book. Contains filepath to avatar image file.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvatar(String)}
 */
public class Avatar {

    public static final String MESSAGE_AVATAR_CONSTRAINTS =
            "Please specify the absolute filepath for the avatar image eg. av/C:\\image.png\n (for Windows), "
            + "av//User/username/path/to/image.jpg (for MacOS). "
            + "Image file should be 200x200 and in jpg or png format";

    public static final String AVATAR_VALIDATION_PATTERN = "([^\\s]+(\\.(?i)(jpg|png))$)";

    private String value;

    /**
     * Constructs an {@code Avatar}.
     *
     * @param avatar A valid avatar.
     */
    public Avatar(String avatar) {
        requireNonNull(avatar);
        checkArgument(isValidAvatar(avatar), MESSAGE_AVATAR_CONSTRAINTS);
        this.value = avatar;
    }

    /**
     * Returns true if a given string is a valid player's avatar.
     */
    public static boolean isValidAvatar(String test) {
        Pattern pattern = Pattern.compile(AVATAR_VALIDATION_PATTERN);
        Matcher matcher = pattern.matcher(test);
        return matcher.matches() || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        return value;
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }
    */

    /**
     * Copies the image file from file path entered into images/ and renames it as [name].png
     * and saves the file path in value
     * @param player player's avatar image filepath string
     * @throws IOException  thrown when file not found
     */
    public void setFilePath(String player) throws IOException {
        if (value.equals("<UNSPECIFIED>")) {
            return;
        }
        final File file = new File(value);

        Path dest = new File("images/" + player.replaceAll("\\s+", "") + ".png").toPath();
        Files.createDirectories(Paths.get("images")); // Creates missing directories if any
        Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        this.value = dest.toString();
    }

    public String getValue() {
        return value;
    }

    public static void setUpPlaceholderForTest() {
        try {
            Files.copy(Avatar.class.getResourceAsStream("/images/placeholder_test.png"),
                    Paths.get("images/placeholder_test.png"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LogsCenter.getLogger(Avatar.class).warning("placeholder image file missing");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
