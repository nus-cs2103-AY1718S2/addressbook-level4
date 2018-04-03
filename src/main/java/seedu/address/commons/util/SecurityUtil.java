package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.model.Password;

//@@author yeggasd
/**
 * Contains utility methods used for encrypting and decrypting files for Storage
 */
public class SecurityUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String DEFAULT_PASSWORD = "";
    private static final String XML = "xml";

    /**
     * Encrypts the given file using AES key created by DEFAULT_PASSWORD.
     *
     * @param file Points to a valid file containing data
     * @param password Used to decrypt file
     * @throws IOException if file cannot be opened
     * @throws WrongPasswordException if password used is wrong
     */
    public static void encrypt(String file, String password)throws IOException, WrongPasswordException {
        byte[] hashedPassword = hashPassword(password);
        encrypt(new File(file), hashedPassword);
    }

    /**
     * Encrypts the given file using AES key created by DEFAULT_PASSWORD.
     *
     * @param file Points to a valid file containing data
     * @param password Used to decrypt file
     * @throws IOException if file cannot be opened
     * @throws WrongPasswordException if password used is wrong
     */
    public static void encrypt(File file, byte[] password)throws IOException, WrongPasswordException {
        try {
            Key secretAesKey = createKey(password);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretAesKey);
            fileProcessor(cipher, file);
        } catch (InvalidKeyException ike) {
            logger.severe("ERROR: Wrong key length " + StringUtil.getDetails(ike));
            throw new AssertionError("Wrong key length");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            logger.severe("ERROR: Cannot find AES or padding in library.");
            throw new AssertionError("Cannot find AES or padding");
        }
    }

    /**
     * Test to see if the given file is plaintext using AES key created by DEFAULT_PASSWORD.
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
     * @throws WrongPasswordException if password used is wrong
     */
    public static void decrypt(File file)throws IOException, WrongPasswordException {
        if (!checkPlainText(file)) {
            throw new WrongPasswordException("File Encrypted!");
        }
    }

    /**
     * Decrypts the given file using AES key created by DEFAULT_PASSWORD.
     *
     * @param file Points to a file to be decrypted
     * @param password Used to decrypt file
     * @throws IOException if file cannot be opened
     * @throws WrongPasswordException if password used is wrong
     */
    public static void decrypt(File file, byte[] password) throws IOException, WrongPasswordException {
        try {
            Key secretAesKey = createKey(password);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretAesKey);
            fileProcessor(cipher, file);
        } catch (InvalidKeyException ike) {
            logger.severe("ERROR: Wrong key length " + StringUtil.getDetails(ike));
            throw new AssertionError("Wrong key length");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            logger.severe("ERROR: Cannot find AES or padding in library.");
            throw new AssertionError("Cannot find AES or padding");
        }
    }

    /**
     * Decrypts or encrypts at the file using cipher passed.
     *
     * @param cipher Encrypts or Decrypts file given mode
     * @param file Points to a valid file containing data
     * @throws IOException if cannot open file
     * @throws WrongPasswordException if password used is wrong
     */
    private static void fileProcessor(Cipher cipher, File file) throws IOException, WrongPasswordException {
        byte[] inputBytes = "Dummy".getBytes();
        try {

            FileInputStream inputStream = new FileInputStream(file);
            inputBytes = new byte[(int) file.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(outputBytes);
            checkPlainText(outputBytes);
            inputStream.close();
            outputStream.close();

        } catch (BadPaddingException e) {
            handleBadPaddingException(inputBytes, e);
        } catch (IllegalBlockSizeException e) {
            logger.info("Warning: Text already in plain text.");
        }
    }

    /**
     * Hashes the DEFAULT_PASSWORD to meet the required length for AES.
     */
    public static byte[] hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            try {
                md.update(password.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.severe("UTF-8 not supported, using default but may not be able to decrypt in other computer.");
                md.update(password.getBytes());
            }
            byte[] hashedPassword = md.digest();
            return Arrays.copyOf(hashedPassword, 16);
        } catch (NoSuchAlgorithmException nsae) {
            throw new AssertionError("SHA-1 should exist");
        }
    }

    /**
     * Decrypt the given file with the current and if it fails the previous password
     * if password is null, will not try to decrypt
     * @param file Points to the file to be decrypted
     * @param password Used to decrypt file
     * @throws IOException if file cannot be opened
     * @throws WrongPasswordException if password used is wrong
     */
    public static void decryptFile (File file, Password password) throws IOException, WrongPasswordException {
        if (password.getPassword() != null) {
            try {
                decrypt(file, password.getPassword());
            } catch (WrongPasswordException e) {
                logger.info("Current Password don't work, trying previous password.");
                decrypt(file, password.getPrevPassword());
            }
        }
    }

    /**
     * Encrypt the given file with the current
     * if password is null, will not try to encrypt
     * @param file Points to the file to be decrypted
     * @param password Used to decrypt file
     * @throws IOException if file cannot be opened
     * @throws WrongPasswordException if password used is wrong
     */
    public static void encryptFile (File file, Password password) throws IOException, WrongPasswordException {
        if (password.getPassword() != null) {
            encrypt(file, password.getPassword());
        }
    }
    /**
     * Generates a key.
     */
    private static Key createKey(byte[] password) {
        return new SecretKeySpec(password, "AES");
    }

    /**
     * Handles {@code BadPaddingException} by determining whether it is plain text or other case
     * @param inputBytes Input data that caused this
     * @param e Contains the exception details to throw with WrongPasswordException
     * @throws WrongPasswordException if it is wrong password
     */
    private static void  handleBadPaddingException(byte[] inputBytes, BadPaddingException e)
                                                                            throws WrongPasswordException {
        if (!checkPlainText(inputBytes)) {
            logger.severe("ERROR: Wrong PASSWORD length used ");
            throw new WrongPasswordException("Wrong PASSWORD.");

        } else {
            logger.info("Warning: Text already in plain text.");
        }
    }

    /**
     * Checks whether it is plain text by checking whether it is in the range of characters commonly used for the
     *  the whole data
     * @param data Contains the file data
     * @return true if it is highly likely to be plain text
     */
    private static boolean checkPlainText(byte[] data) {
        String string = new String(data);
        return string.contains(XML);
    }

    /**
     * Checks whether it is plain text by checking whether it is in the range of characters commonly used for the
     *  the whole data
     * @param file Points to file path
     * @return true if it is highly likely to be plain text
     */
    private static boolean checkPlainText(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] inputBytes = new byte[(int) file.length()];
        inputStream.read(inputBytes);
        inputStream.close();
        return checkPlainText(inputBytes);
    }
}
