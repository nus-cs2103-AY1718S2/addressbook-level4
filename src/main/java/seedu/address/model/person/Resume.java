package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
//@@author mhq199657
/**
 * Represents a Person's resume's file name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidResume(String)}
 */
public class Resume {
    public static final String MESSAGE_RESUME_CONSTRAINTS =
            "Resume file should be at least 1 character long, exist in the same directory as the jar programme, "
                    + "smaller than 1MB and should be a valid PDF.";
    private static final int ONEMEGABYTE = 1 * 1024 * 1024;
    private static final String RESUME_VALIDATION_REGEX = ".*\\S.*";
    public final String value;
    public final String userInput;
    private boolean isHashed;
    /**
     * Constructs a {@code Resume}.
     *
     * @param fileName A valid fileName.
     */
    public Resume(String fileName) {
        isHashed = false;
        if (isNull(fileName)) {
            this.value = null;
            this.userInput = null;
        } else {
            checkArgument(isValidResume(fileName), MESSAGE_RESUME_CONSTRAINTS);
            this.value = fileName;
            userInput = fileName;
        }
    }

    public Resume(String storageFileName, String userFileName) {
        isHashed = true;
        if (isNull(storageFileName)) {
            this.value = null;
            this.userInput = null;
        } else {
            checkArgument(isValidResume(storageFileName), MESSAGE_RESUME_CONSTRAINTS);
            this.value = storageFileName;
            userInput = userFileName;
        }
    }

    /**
     * Returns true if a given string is a valid person resume.
     */
    public static boolean isValidResume(String test) {
        requireNonNull(test);
        if (!test.matches(RESUME_VALIDATION_REGEX)) {
            return false;
        }
        String userDir = System.getProperty("user.dir");
        File resumeFile = new File(userDir + File.separator + test);
        if (resumeFile.isDirectory()) {
            return false;
        } else if (!resumeFile.exists()) {
            return false;
        } else {
            if (resumeFile.length() > ONEMEGABYTE) {
                return false;
            } else {
                try {
                    byte[] resumeBytes = Files.readAllBytes(resumeFile.toPath());
                    return isPdf(resumeBytes);
                } catch (IOException ioe) {
                    return false;
                }
            }
        }
    }
    /**
     * Returns true if a given byte array is of pdf type.
     */
    private static boolean isPdf(byte[] data) {
        if (data != null && data.length > 4
                && data[0] == 0x25 && // %
                data[1] == 0x50 && // P
                data[2] == 0x44 && // D
                data[3] == 0x46 && // F
                data[4] == 0x2D) { // -
            // version 1.3 file terminator
            if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x33
                    && data[data.length - 7] == 0x25 && // %
                    data[data.length - 6] == 0x25 && // %
                    data[data.length - 5] == 0x45 && // E
                    data[data.length - 4] == 0x4F && // O
                    data[data.length - 3] == 0x46 && // F
                    data[data.length - 2] == 0x20 && (// SPACE
                    data[data.length - 1] == 0x0A || data[data.length - 1] == 0x0D)) { // EOL or CR
                return true;
            }
            // version 1.3 file terminator
            if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x33
                    && data[data.length - 6] == 0x25 && // %
                    data[data.length - 5] == 0x25 && // %
                    data[data.length - 4] == 0x45 && // E
                    data[data.length - 3] == 0x4F && // O
                    data[data.length - 2] == 0x46 && (// F
                    data[data.length - 1] == 0x0A || data[data.length - 1] == 0x0D)) { // EOL
                return true;
            }
            // version 1.4 file terminator
            if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x34
                    && data[data.length - 6] == 0x25 && // %
                    data[data.length - 5] == 0x25 && // %
                    data[data.length - 4] == 0x45 && // E
                    data[data.length - 3] == 0x4F && // O
                    data[data.length - 2] == 0x46 && (// F
                    data[data.length - 1] == 0x0A || data[data.length - 1] == 0x0D)) { // EOL
                return true;
            }
            // version 1.5 file terminator
            if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x35
                    && data[data.length - 6] == 0x25 && // %
                    data[data.length - 5] == 0x25 && // %
                    data[data.length - 4] == 0x45 && // E
                    data[data.length - 3] == 0x4F && // O
                    data[data.length - 2] == 0x46 && (// F
                    data[data.length - 1] == 0x0A || data[data.length - 1] == 0x0D)) { // EOL
                return true;
            }
            if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x35
                    && data[data.length - 5] == 0x25 && // %
                    data[data.length - 4] == 0x25 && // %
                    data[data.length - 3] == 0x45 && // E
                    data[data.length - 2] == 0x4F && // O
                    data[data.length - 1] == 0x46) { // F
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return userInput;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Resume // instanceof handles nulls
                && ((isNull(this.value) && isNull(((Resume) other).value)) //both value are null
                    || (isHashed && ((Resume) other).isHashed) ? isHashEqual(this.value, ((Resume) other).value)
                                : this.userInput.equals(((Resume) other).userInput))); // state check
    }

    private boolean isHashEqual(String first, String second) {
        assert(first.split("_").length == 2);
        String firstHash = first.split("_")[1];
        String secondHash = second.split("_")[1];
        return firstHash.equals(secondHash);
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
