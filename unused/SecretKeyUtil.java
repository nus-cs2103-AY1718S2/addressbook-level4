package seedu.address.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.model.UserPrefs;
/*
Initial idea was to generate a secret key and store it in a keystore is the data folder
However, the secret key will still be exposed, thus I changed secretKey the implementation to a
password based one, remove the need to use this SecretKeyUtil. Some functions are not fully refactored
since they won't be used.
*/
//@@author limzk1994-unused
public class SecretKeyUtil {

    private static final char[]KEYSTORE_PASSWORD = {'p','a','s','s'};
    private static final String keyAlias = "secretKeyAlias";

    /**
     *
     * @param file secretKey is stored as a file
     * @return file if it exists
     */
    public static boolean isKeyExists(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(File file) throws IOException {
        if (!isKeyExists(file)) {
            UserPrefs userPrefs = new UserPrefs();
            saveSecretKey(createSecretKey(), userPrefs.getSecretKeyFilePath());
        }
        createParentDirsOfFile(file);
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
     * Generate secret key for given algorithm
     * @return SecretKey
     */
    public static SecretKey createSecretKey() {
        KeyGenerator keyGenerator=null;
        keyGenerator.init(128);
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyGenerator.generateKey();
    }

    /**
     * Save secret key to a file
     *
     * @param secretKey Secret key to save into file
     * @param fileName File name to store
     */
    public static void saveSecretKey(SecretKey secretKey, String fileName) {
        byte[] keyBytes = secretKey.getEncoded();
        File keyFile = new File(fileName);
        FileOutputStream fOutStream = null;
        try {
            fOutStream = new FileOutputStream(keyFile);
            fOutStream.write(keyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fOutStream != null) {
                try {
                    fOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns secret key to a file
     * @param keyFile File name
     */

    public static SecretKey getSecretKey(String keyFile) throws Exception{

        File secretKeyFile = new File(keyFile);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(secretKeyFile));
        byte[] secretKeyByteFile = new byte[(int)secretKeyFile.length()];
        try {
            bis.read(secretKeyByteFile);
            bis.close();
        }catch(IOException e){

        }
        //Recover the secret key
        SecretKey secretKey = new SecretKeySpec(secretKeyByteFile,0,secretKeyByteFile.length,"AES");
        return secretKey;
    }

    /**
     * Save secret key to a file
     * @param secretKey Secret key to save into file
     * @param fileName File name to store
     */
    
    private static void saveSecretKey(SecretKey secretKey, String fileName) throws IOException{

        byte[] keyBytes = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
        byte[] byteFile = encodedKey.getBytes();

        File keyFile = new File(fileName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(keyFile);
            fos.write(byteFile);
            fos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (fos != null) {
                    fos.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Load a keystore and add secret key
     * @param storeFile: File name to store
     *
     * @throws Exception
     */
    
    private static void addKeyStore(String storeFile, String keyFile) throws Exception {
        FileInputStream fis = null;
        KeyStore keyStore = KeyStore.getInstance("JKS");

        try {
            fis = new FileInputStream(storeFile);
            keyStore.load(fis, KEYSTORE_PASSWORD);
        }catch(IOException e){
            e.printStackTrace();
        }catch(GeneralSecurityException e){
            e.printStackTrace();
        }finally{
            if(fis != null){
                fis.close();
            }
        }

        //Read from file
        File secretKeyFile = new File(keyFile);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(secretKeyFile));
        byte[] secretKeyByteFile = new byte[(int)secretKeyFile.length()];
        bis.read(secretKeyByteFile);
        bis.close();
        byte[] encodedKey = Base64.getDecoder().decode(secretKeyByteFile);

        //Recover the secret key
        SecretKey secretKey = new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");

        //Save and store secret key
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(KEYSTORE_PASSWORD);
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
        keyStore.setEntry(keyAlias,secretKeyEntry,protectionParameter);

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(storeFile);
            keyStore.store(fos,KEYSTORE_PASSWORD);
        }finally{
            if(fos!=null){
                fos.close();
            }
        }
    }
    public static SecretKey getSecretKey(String storeFile) throws Exception{
        FileInputStream fis = new FileInputStream(storeFile);
        KeyStore keyStore = KeyStore.getInstance("JKS");

        keyStore.load(fis,KEYSTORE_PASSWORD);
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(KEYSTORE_PASSWORD);
        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry)keyStore.getEntry(keyAlias,protectionParameter);
        SecretKey secretKey = secretKeyEntry.getSecretKey();

        return secretKey;
    }

}
