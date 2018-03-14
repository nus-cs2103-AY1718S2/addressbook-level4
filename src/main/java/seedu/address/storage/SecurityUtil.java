package seedu.address.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;

/**
 * Contains utility methods used for encrypting and decrypting files for Storage
 */
public class SecurityUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static SecretKey secretAESKey;
    private static final String AES_KEY_FILEPATH = "data/key.key";

    /**
     * Encrypts the given file using AES key located at {@code AES_KEY_FILEPATH}
     * if AES key does not exists, create one {@code loadKey}
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
     */
    public static void encrypt(File file)throws IOException {
        loadKey();
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretAESKey);
            fileProcessor(cipher, file);
        } catch (GeneralSecurityException gse) {
            logger.severe("ERROR: Wrong cipher to encrypt message " + StringUtil.getDetails(gse));
            System.exit(1);
        }
    }

    /**
     * Decrypts the given file using AES key located at {@code AES_KEY_FILEPATH}
     * if AES key does not exists, create one {@code loadKey}
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
     */
    public static void decrypt(File file) throws IOException {
        loadKey();
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretAESKey);
            fileProcessor(cipher, file);
        } catch (GeneralSecurityException gse) {
            logger.severe("ERROR: Wrong cipher to encrypt message " + StringUtil.getDetails(gse));
            System.exit(1);
        }
    }

    /**
     * Decrypts or encrypts at the file using cipher passed
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
            logger.info("Warning: Text already in plain text ");
            logger.info("Encrypting");
            //encrypt(file);
            //decrypt(file);
        }
    }

    /**
     * Loads the key from the given path located at {@code AES_KEY_FILEPATH}
     * if AES key does not exists, create one
     */
    private static void loadKey() {
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(AES_KEY_FILEPATH));
            secretAESKey = (SecretKey) ois.readObject();
        } catch (IOException oie) {
            logger.info("Error reading key from file");
            createKey();
        } catch (ClassNotFoundException cnfe) {
            logger.severe("ERROR: Cannot typecast to class SecretKey " + StringUtil.getDetails(cnfe));
            createKey();
        }
    }

    /**
     * Logs and Creates the key
     */
    private static void createKey() {
        logger.info("Creating AES key file");
        initKey();
        saveKey();
    }

    /**
     * Creates an AES key of 128 bits using {@code KeyGenerator}
     */
    private static void initKey() {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException nsae) {
            logger.severe("ERROR: Cannot generate AES keys " + StringUtil.getDetails(nsae));
            System.exit(1);
        }
        keyGen.init(128);
        secretAESKey = keyGen.generateKey();
    }
    /**
     * Saves the key at {@code AES_KEY_FILEPATH}
     * Exits the program if unable to save.
     */
    public static void saveKey() {

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(AES_KEY_FILEPATH));
            oos.writeObject(secretAESKey);
            oos.close();
        } catch (IOException oie) {
            logger.severe("ERROR: Cannot saving AES key to file " + StringUtil.getDetails(oie));
            System.exit(1);
        }
        logger.info("Public key saved to file " + AES_KEY_FILEPATH);
    }
}
