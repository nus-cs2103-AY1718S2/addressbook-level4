package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import org.junit.Test;

public class SecurityUtilTest {
    @Test
    public void encrypt_decrypt_success() throws Exception {
        File testFile = new File("./src/test/data/sandbox/temp");
        String checker = new String("This is a test String.");

        FileWriter writer = new FileWriter(testFile);
        writer.write(checker);
        writer.close();

        SecurityUtil.encrypt(testFile);
        SecurityUtil.decrypt(testFile);

        Scanner reader = new Scanner(testFile);
        String read = reader.nextLine();
        reader.close();

        assertEquals(checker, read);
    }
}
