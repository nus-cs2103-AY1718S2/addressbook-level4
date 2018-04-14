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
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;

//@@author 592363789
/**
 *  Class for encrypt and decrypt file and key.
 */
public class CipherEngine {
    private static final Logger logger = LogsCenter.getLogger(CipherEngine.class);

    private static final String TEMP_FILE = "data/__temp.xml";

    private static final String PBKDF_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int PBKDF_ITERATION_COUNT = 22000;
    private static final int PBKDF_KEY_LENGTH = 512;
    private static final int SALT_BYTE_LENGTH = 64;
    private static final SecureRandom RANDOM = new SecureRandom();

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

    //@@author
    /**
     * Returns a salted and hashed password to be used for storage.
     */
    public static String hashPassword(String password) throws Exception {
        byte[] salt = new byte[SALT_BYTE_LENGTH];
        RANDOM.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF_ITERATION_COUNT, PBKDF_KEY_LENGTH);
        byte[] hash = SecretKeyFactory.getInstance(PBKDF_ALGORITHM).generateSecret(spec).getEncoded();

        Base64.Encoder enc = Base64.getEncoder();
        return PBKDF_ALGORITHM + "$" + PBKDF_ITERATION_COUNT + "$" + PBKDF_KEY_LENGTH + "$"
                + enc.encodeToString(hash) + "$" + enc.encodeToString(salt);
    }

    /**
     * Returns true if the given {@code password} matches the {@code passwordHash}, or false if otherwise.
     */
    protected static boolean checkPassword(String password, String passwordHash) throws Exception {
        String[] split = passwordHash.split("\\$");
        if (split.length != 5) {
            throw new IllegalArgumentException("Invalid hash.");
        }

        int iterationCount = Integer.parseInt(split[1]);
        int keyLength = Integer.parseInt(split[2]);
        Base64.Decoder dec = Base64.getDecoder();

        KeySpec spec = new PBEKeySpec(password.toCharArray(), dec.decode(split[4]), iterationCount, keyLength);
        byte[] hash = SecretKeyFactory.getInstance(PBKDF_ALGORITHM).generateSecret(spec).getEncoded();
        return Arrays.equals(dec.decode(split[3]), hash);
    }

    /**
     * Returns true if the given {@code hash} is a valid password hash, or false if otherwise.
     */
    protected static boolean isValidPasswordHash(String hash) {
        String[] split = hash.split("\\$");
        if (split.length != 5) {
            return false;
        }
        return split[1].matches("\\d+") && split[2].matches("\\d+");
    }
}
