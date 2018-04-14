package seedu.address.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//@@author 592363789
/**
 *  Class for encrypt and decrypt file and key.
 */
public class CipherEngine {
    private static final Logger logger = LogsCenter.getLogger(CipherEngine.class);

    private static final String TEMP_FILE = "data/change.xml";

    private static final String defaultKey = "vsFA#%HZ1c93";
    private static final String DES = "DES";
    private static final String ENCODE = "GBK";

    /**
     *  Encrypts file at {@code fileName} using {@code key}.
     */
    public static void encryptFile(String fileName, String key) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            FileOutputStream fos = new FileOutputStream(TEMP_FILE);
            encrypt(key, fis, fos);
            replaceFile(fileName, TEMP_FILE);
        } catch (IOException ioe) {
            logger.warning("Could not find file " + fileName);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            logger.warning("Could not encrypt file " + fileName);
        }
    }

    /**
     *  Decrypts file at {@code fileName} using {@code key}.
     */
    public static void decryptFile(String fileName, String key) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            FileOutputStream fos = new FileOutputStream(TEMP_FILE);
            decrypt(key, fis, fos);
            replaceFile(fileName, TEMP_FILE);
        } catch (IOException ioe) {
            logger.warning("Could not find file " + fileName);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            logger.warning("Could not decrypt file " + fileName);
        }
    }

    /**
     * Encrypts {@code key}.
     */
    public static String encryptKey(String key) throws Exception {
        byte[] byarray = encrypt(key.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
        return new BASE64Encoder().encode(byarray);
    }

    /**
     * Decrypts {@code key}.
     */
    public static String decryptKey(String key) throws Exception {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] decodeBuffer = base64Decoder.decodeBuffer(key);
        byte[] bytes = decrypt(decodeBuffer, defaultKey.getBytes(ENCODE));
        return new String(bytes, ENCODE);
    }

    /**
     * Encrypts from {@code inputStream} and outputs to {@code outputStream}.
     * Encryption is done using {@code key}.
     */
    private static void encrypt(String key, InputStream inputStream, OutputStream outputStream)
            throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            NoSuchPaddingException, IOException {

        SecretKey desKey = getSecretKey(key);
        Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
        hiding(cipherInputStream, outputStream);
    }
    //@@author 592363789
    /**
     * Encrypts {@code content} using {@code key}.
     *
     * @param content content to encrypt.
     * @param key key to encrypt with.
     */
    private static byte[] encrypt(byte[] content, byte[] key) throws Exception {
        SecureRandom secureRandom = new SecureRandom();

        DESKeySpec desKeySpec = new DESKeySpec(key);

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = secretKeyFactory.generateSecret(desKeySpec);

        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(DES);

        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, securekey, secureRandom);

        return cipher.doFinal(content);
    }

    /**
     * Decrypts from {@code inputStream} and outputs to {@code outputStream}.
     * Decryption is done using {@code key}.
     */
    private static void decrypt(String key, InputStream inputStream, OutputStream outputStream)
            throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            NoSuchPaddingException, IOException {

        SecretKey desKey = getSecretKey(key);
        Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

        cipher.init(Cipher.DECRYPT_MODE, desKey);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
        hiding(inputStream, cipherOutputStream);
    }

    /**
     * Decrypts {@code content} using {@code key}.
     *
     * @param content content to decrypt.
     * @param key key to decrypt with.
     */
    private static byte[] decrypt(byte[] content, byte[] key) throws Exception {

        SecureRandom secureRandom = new SecureRandom();

        DESKeySpec desKeySpec = new DESKeySpec(key);

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = secretKeyFactory.generateSecret(desKeySpec);

        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(DES);

        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, securekey, secureRandom);

        return cipher.doFinal(content);
    }

    /**
     * Obtains a {@link SecretKey} from {@code key} to be used in encryption and decryption.
     */
    private static SecretKey getSecretKey(String key) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        String paddedKey = StringUtil.rightPad(key, ' ', 8);
        DESKeySpec desKeySpec = new DESKeySpec(paddedKey.getBytes());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        return secretKeyFactory.generateSecret(desKeySpec);
    }

    /**
     *  Cover the file (hiding the normal file)
     */
    private static void hiding(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[64];
        int num;
        while ((num = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, num);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     * Replaces the file at {@code toReplace} with the file at {@code replacement}.
     */
    private static void replaceFile(String toReplace, String replacement) throws IOException {

        File dest = new File(toReplace);
        File src = new File(replacement);

        copyFileUsingStream(src, dest);
        src.delete();
    }

    // Reused from https://www.journaldev.com/861/java-copy-file
    /**
     * Copies the content of {@code source} into {@code dest}.
     */
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

}
