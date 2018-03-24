package seedu.address.commons.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * This class supports the naming of files to be stored.
 */
public class NamingUtil {

    /**
     * This method uses SHA-256 to hash the 2 input strings and returns it.
     */
    public static String generateUniqueName(String fileName, String details) {
        try {
            String toHash = fileName + details;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashValue = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
            String hashedChar = Base64.getEncoder().encodeToString(hashValue);
            return hashedChar;
        } catch (NoSuchAlgorithmException nae) {
            return fileName + details;
        }
    }

}
