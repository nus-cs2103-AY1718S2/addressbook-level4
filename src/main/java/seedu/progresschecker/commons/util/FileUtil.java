package seedu.progresschecker.commons.util;

import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Writes and reads files
 */
public class FileUtil {

    public static final String REGEX_VALID_IMAGE = "([^\\s]+(\\.(?i)(jpg|jpeg|png))$)";
    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(File file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories
     *
     * @return true if file is created, false if file already exists
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }

        createParentDirsOfFile(file);

        return file.createNewFile();
    }

    /**
     * Creates the given directory along with its parent directories
     *
     * @param dir the directory to be created; assumed not null
     * @throws IOException if the directory or a parent directory cannot be created
     */
    public static void createDirs(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to make directories of " + dir.getName());
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(File file) throws IOException {
        File parentDir = file.getParentFile();

        if (parentDir != null) {
            createDirs(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(CHARSET));
    }

    /**
     * Copies all the contents from the file in original path to the one in destination path.
     * @param oriPath of the file to be copied
     * @param destPath of the file to be pasted
     * @return true if the file is successfully copied to the specified place.
     */
    public static boolean copyFile(String oriPath, String destPath) throws IOException {

        //create a buffer to store content
        byte[] buffer = new byte[1024];

        //bufferedInputStream
        FileInputStream fis = new FileInputStream(oriPath);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //bufferedOutputStream
        FileOutputStream fos = new FileOutputStream(destPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int numBytes = bis.read(buffer);
        while (numBytes > 0) {
            bos.write(buffer, 0, numBytes);
            numBytes = bis.read(buffer);
        }

        //close input,output stream
        bis.close();
        bos.close();

        return true;
    }

    /**
     * Converts a string to a platform-specific file path
     * @param pathWithForwardSlash A String representing a file path but using '/' as the separator
     * @return {@code pathWithForwardSlash} but '/' replaced with {@code File.separator}
     */
    public static String getPath(String pathWithForwardSlash) {
        checkArgument(pathWithForwardSlash.contains("/"));
        return pathWithForwardSlash.replace("/", File.separator);
    }

    /**
     * Returns the extension information from the file path
     * @param filePath
     * @return extension String
     */
    public static String getFileExtension(String filePath) {
        return "." + filePath.split("\\.")[1];
    }

    /**
     * Creates a new file if it does not exist
     * @param file to created
     * @throws IOException if the file cannot be created
     */
    public static void createMissing(File file) throws IOException {
        if (!file.exists()) {
            createFile(file);
        }
    }

    /**
     * Returns whether the uploaded file is a valid image file
     * Valid image file should have extension: '.jpg', '.jepg' or 'png'.
     * @param path of the uploaded image file
     * @return true if the uploaded file has valid extension
     */
    public static boolean isValidImageFile(String path) {
        return path.matches(REGEX_VALID_IMAGE);
    }

    /**
     * Returns whether the path of uploaded file is under the specific folder
     * @param path of the uploaded file
     * @param parentFolder of the specific folder
     * @return true if the file is under this specific folder
     */
    public static boolean isUnderFolder(String path, String parentFolder) {
        return path.startsWith(parentFolder);
    }
}
