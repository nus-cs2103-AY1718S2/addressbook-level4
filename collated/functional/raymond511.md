package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;

import seedu.address.commons.core.LogsCenter;

/**
 * A Class that encrypts and decrypts XML files stored on the hard disk.
 *
 */
//@@author raymond511
public class EncryptionUtil {

    /**
     *The standard version of the JRE/JDK are under export restrictions.
     *That also includes that some cryptographic algorithms are not allowed to be shipped in the standard version.
     *Replace files in library with Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8
     */

    private static final Logger logger = LogsCenter.getLogger(EncryptionUtil.class);
    private static final String passwordToHash = "password";
    private static byte[] salt = new byte[0];
    private static final String password = getSecurePassword(passwordToHash, salt);

    /**
     * Adds salt to password cryptography
     * @throws NoSuchAlgorithmException if salt acnnot be generated
     * @throws NoSuchProviderException if salt cannot be generated
     */

    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            logger.severe("This algorithm is not supported " + e.getMessage());
        } catch (NoSuchProviderException e) {
            logger.severe("The provider is not available " + e.getMessage());
        }
        return salt;
    }

    /**
     * Generates a secure password
     *
     * @param passwordToHash used to generate a new password
     * @param salt to adds security to the new password
     * @throws NoSuchAlgorithmException if new password cannot be generated
     */

    private static String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1)); }
                generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.severe("This algorithm is not supported " + e.getMessage());
        }
        return generatedPassword;
    }

    /**
     * Encrypts an XML file
     *
     * @param file path of the file to be encrypted
     * @throws IOException if file could not be found
     */
    public static void encrypt(File file) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey privateKey = generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            fileToBytes(cipher, file);
        } catch (GeneralSecurityException gse) {
            logger.severe("Cipher or Padding might not be supported " + gse.getMessage());
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
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey privateKey = generateKey();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            fileToBytes(cipher, file);
        } catch (GeneralSecurityException gse) {
            logger.severe("Cipher or Padding might not be supported " + gse.getMessage());
        } catch (UnsupportedEncodingException use) {
            logger.info("Encoding Unsupported " + use.getMessage());
        }
    }

    /**
     * Processes the given file using the given cipher
     *
     * @param cipher cipher used for encryption or decryption
     * @param file path of the file to be encrypted or decrypted
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

    /**
     * Method to generate a SecretKey using the password provided
     *
     * @return SecretKey generated using AES encryption
     */
    public static SecretKey generateKey() {
        final String password = "EncryptionisImportant";
        SecretKeySpec secretKeySpec = null;
        try {
            salt = getSalt();
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(password.getBytes("UTF-8"));
            byte[] key = digester.digest();
            secretKeySpec = new SecretKeySpec(key , 0 , 16 ,  "AES");
        } catch (NoSuchAlgorithmException nae) {
            logger.info("Algorithm Unsupported " + nae.getMessage());
        } catch (UnsupportedEncodingException use) {
            logger.info("Encoding Unsupported " + use.getMessage());
        } catch (NoSuchProviderException e) {
            logger.severe("The provider is not available " + e.getMessage());
        }
        return secretKeySpec;
    }
}
