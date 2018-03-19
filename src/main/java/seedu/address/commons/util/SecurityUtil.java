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

/**
 * Contains utility methods used for encrypting and decrypting files for Storage
 */
public class SecurityUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String defaultPassword = new String("test");

    /**
     * Encrypts the given file using AES key created by defaultPassword.
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
     */
    public static void encrypt(File file)throws IOException, WrongPasswordException {
        byte[] hashedPassword = hashPassword(defaultPassword);
        encrypt(file, hashedPassword);
    }

    /**
     * Encrypts the given file using AES key created by defaultPassword.
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
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
     * Encrypts the given file using AES key created by defaultPassword.
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
     */
    public static void decrypt(File file)throws IOException, WrongPasswordException {
        byte[] hashedPassword = hashPassword(defaultPassword);
        decrypt(file, hashedPassword);
    }

    /**
     * Decrypts the given file using AES key created by defaultPassword.
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
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
     */
    private static void fileProcessor(Cipher cipher, File file) throws IOException, WrongPasswordException {
        try {

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (BadPaddingException e) {
            logger.severe("ERROR: Wrong defaultPassword length used " + StringUtil.getDetails(e));
            throw new WrongPasswordException("Wrong defaultPassword.");
        } catch (IllegalBlockSizeException e) {
            logger.info("Warning: Text already in plain text.");
        }
    }

    /**
     * Hashes the defaultPassword to meet the required length for AES.
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
     * Generates a key.
     */
    private static Key createKey(byte[] password) {
        return new SecretKeySpec(password, "AES");
    }
}
