package seedu.address.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;

/**
 * Contains utility methods used for encrypting and decrypting files for Storage
 */
public class SecurityUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static String password = new String("test");
    private static byte[] hashedPassword;
    private static Key secretAESKey;

    /**
     * Encrypts the given file using AES key created by password.
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
     */
    public static void encrypt(File file)throws IOException {
        try {
            hashPassword();
            secretAESKey = createKey(hashedPassword);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretAESKey);
            fileProcessor(cipher, file);
        } catch (GeneralSecurityException gse) {
            logger.severe("ERROR: Wrong cipher to encrypt message " + StringUtil.getDetails(gse));
            System.exit(1);
        }
    }

    /**
     * Decrypts the given file using AES key created by password.
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
     */
    public static void decrypt(File file) throws IOException {
        try {
            hashPassword();
            secretAESKey = createKey(hashedPassword);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretAESKey);
            fileProcessor(cipher, file);
        } catch (GeneralSecurityException gse) {
            logger.severe("ERROR: Wrong cipher to encrypt message " + StringUtil.getDetails(gse));
            System.exit(1);
        }
    }

    /**
     * Decrypts or encrypts at the file using cipher passed.
     *
     * @param cipher Encrypts or Decrypts file given mode
     * @param file Points to a valid file containing data
     * @throws IOException if cannot open file
     */
    private static void fileProcessor(Cipher cipher, File file) throws IOException {
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
            logger.severe("ERROR: Wrong password length used " + StringUtil.getDetails(e));
        } catch (IllegalBlockSizeException e) {
            logger.info("Warning: Text already in plain text.");
        }
    }

    /**
     * Hashes the password to meet the required length for AES.
     */
    private static void hashPassword() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        try {
            md.update(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.severe("UTF-8 not supported, using default but may not be able to decrypt in other computer.");
            md.update(password.getBytes());
        }
        hashedPassword = md.digest();
        hashedPassword = Arrays.copyOf(hashedPassword, 16);
    }

    /**
     * Use the hash to generate a key.
     */
    private static Key createKey(byte[] hashedPassword) {
        return new SecretKeySpec(hashedPassword, "AES");
    }
}
