package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.commons.core.LogsCenter;

/**
 * A Class that encrypts and decrypts XML files stored on the hard disk.
 *
 */
//@@author raymond511
public class EncryptionUtil {
    /**
     * The standard version of the JRE/JDK are under export restrictions.
     * That also includes that some cryptographic algorithms are not allowed to be shipped in the standard version.
     * Replace files in library with Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8
     */
    private static final Logger logger = LogsCenter.getLogger(EncryptionUtil.class);
    private SecretKey key;
    private final static Cipher ecipher = null;
    private final static Cipher dcipher = null;
    private static final String password = "encryptionisimportant";

    /**
     * Initialises encrypt and decrypt keys using AES encryption
     */
    public static void initialiseKey() {
        SecretKeySpec key = null;
        try {
            MessageDigest message = MessageDigest.getInstance("SHA-256");
            message.update(password.getBytes("UTF-8"));
            byte[] keynow = message.digest();
            key = new SecretKeySpec(keynow, 0, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (GeneralSecurityException gse) {
            logger.severe("Cipher or Padding might not be supported " + gse.getMessage());
        } catch (UnsupportedEncodingException use) {
            logger.info("Encoding Unsupported " + use.getMessage());
        }
    }

    /**
     * Encrypts XML file
     *
     * @param file path of the file to be encrypted
     * @throws IOException if file could not be found
     */
    public static void encrypt(File file) throws IOException {
        try {
            fileToBytes(ecipher, file);
        } catch (UnsupportedEncodingException use) {
            logger.info("Encoding Unsupported " + use.getMessage());
        }
    }

    /**
     * Decrypts XML file
     *
     * @param file path of the file to be decrypted
     * @throws IOException if file could not be found
     */
    public static void decrypt(File file) throws IOException {
        try {
            fileToBytes(dcipher, file);
        } catch (UnsupportedEncodingException use) {
            logger.info("Encoding Unsupported " + use.getMessage());
        }
    }

    /**
     * Processes the given file using the given cipher
     *
     * @param cipher cipher used for encryption or decryption
     * @param file   path of the file to be encrypted or decrypted
     * @throws IOException if file could not be found
     */

    private static void fileToBytes(Cipher cipher, File file) throws IOException {

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] readBytes = new byte[(int) file.length()];
            fileInputStream.read(readBytes);

            byte[] writeBytes = cipher.doFinal(readBytes);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(writeBytes);

        } catch (BadPaddingException be) {
            logger.info("File might not decoded/encoded properly due to bad padding " + be.getMessage());
        } catch (IllegalBlockSizeException ibe) {
            logger.info("Input length size must be in multiple of 16  " + ibe.getMessage());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ioe) {
                logger.info("File streams could not be closed  " + ioe.getMessage());
            }
        }
    }
}
