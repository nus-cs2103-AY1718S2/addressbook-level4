package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.eventbus.Subscribe;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.DecryptionRequestEvent;
import seedu.address.commons.events.storage.EncryptionRequestEvent;
//@@author tinyjy94
/**
 * Contains encryption and decryption functions
 */
public class SecurityUtil {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final int AES_Key_Size = 128;
    private static final int iteration = 65536;

    public static void encrypt(String filepath, String encryptedFilePath, String password) throws IOException {
        encrypt(new File(filepath), new File(encryptedFilePath), password);
    }

    /**
     * Encrypts the given file using AES key using Key given.
     */
    public static void encrypt(File inputFile, File outputFile, String password) throws IOException {
        try {
            Key pw = generateKey(password);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pw);
            processFile(cipher, inputFile, outputFile);
        } catch (InvalidKeyException ike) {
            logger.severe("Invalid key length provided " + StringUtil.getDetails(ike));
            throw new AssertionError("Invalid key length.");
        } catch (NoSuchAlgorithmException nsae) {
            logger.severe("Invalid algorithm provided " + StringUtil.getDetails(nsae));
            throw new AssertionError("Invalid algorithm.");
        } catch (NoSuchPaddingException bpe) {
            logger.severe("Invalid padding provided " + StringUtil.getDetails(bpe));
            throw new AssertionError("Invalid padding.");
        }
    }

    public static void decrypt(String filepath, String encryptedFilePath, String password) throws IOException {
        decrypt(new File(encryptedFilePath), new File(filepath), password);
    }

    /**
     * Decrypts the given file using AES key using Key given.
     */
    public static void decrypt(File inputFile, File outputFile, String password) throws IOException {
        try {
            Key pw = generateKey(password);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, pw);
            processFile(cipher, inputFile, outputFile); //decrypt takes in encrypted file as input
        } catch (InvalidKeyException ike) {
            logger.severe("Invalid key length provided " + StringUtil.getDetails(ike));
            throw new AssertionError("Invalid key length.");
        } catch (NoSuchAlgorithmException nsae) {
            logger.severe("Invalid algorithm provided " + StringUtil.getDetails(nsae));
            throw new AssertionError("Invalid algorithm.");
        } catch (NoSuchPaddingException bpe) {
            logger.severe("Invalid padding provided " + StringUtil.getDetails(bpe));
            throw new AssertionError("Invalid padding.");
        }
    }

    /**
     * Encrypts or decrypts the {@code inputFile} and write out into the same {@code inputFile} based on cipher given.
     */
    private static void processFile(Cipher cipher, File inputFile, File outputFile) throws IOException {

        try {
            byte[] inputByteArray = new byte[(int) inputFile.length()];
            FileInputStream fis = new FileInputStream(inputFile); //create file input stream
            fis.read(inputByteArray); //reads from fis to byte[]

            byte[] outputBytes = cipher.doFinal(inputByteArray); //encrypt or decrypt the byte[]
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(outputBytes);

            fis.close();
            fos.close();

        } catch (BadPaddingException bpe) {
            logger.severe("Bad padding provided" + StringUtil.getDetails(bpe));
            throw new AssertionError("Bad padding provided");
        } catch (IllegalBlockSizeException ibse) {
            // user decrypt from plaintext
            logger.info("File is in plain text, no decryption required." + StringUtil.getDetails(ibse));
        }
    }

    /**
     * Generate a secret AES key
     */
    public static Key generateKey(String password) {
        try {
            byte[] inputByte = new byte[16];
            KeySpec spec = new PBEKeySpec(password.toCharArray(), inputByte, iteration, AES_Key_Size);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey secretkey = factory.generateSecret(spec);
            return new SecretKeySpec(secretkey.getEncoded(), "AES");

        } catch (NoSuchAlgorithmException nsae) {
            logger.severe("Invalid algorithm provided " + StringUtil.getDetails(nsae));
            throw new AssertionError("Invalid algorithm.");
        } catch (InvalidKeySpecException ikse) {
            logger.severe("Invalid key specifications provided " + StringUtil.getDetails(ikse));
            throw new AssertionError("Invalid key specifications.");
        }
    }

    @Subscribe
    public void handleEncryptionRequestEvent(EncryptionRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        System.out.println("testing" + event.getPassword());
    }

    @Subscribe
    public void handleDecryptionRequestEvent(DecryptionRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        System.out.println("testing" + event.getPassword());
    }

}
