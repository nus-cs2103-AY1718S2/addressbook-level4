package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.model.Password;

//@@author yeggasd
public class SecurityUtilTest {
    private static final File TEST_DATA_FILE = new File("./src/test/data/sandbox/temp");
    private static final File VALID_DATA_FILE = new File(
            "./src/test/data/XmlAddressBookStorageTest/validAddressBook.xml");
    private static final String TEST_DATA = "<xml>";
    private static final String TEST_PASSWORD =  "test";
    private static final String WRONG_PASSWORD = "wrong";
    private static final byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void decrypt_noPassword_success() throws Exception {

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.decrypt(TEST_DATA_FILE);

        Scanner reader = new Scanner(TEST_DATA_FILE);
        String read = reader.nextLine();
        reader.close();

        assertEquals(TEST_DATA, read);
    }

    @Test
    public void decrypt_fileProcessorPlainText_success() throws Exception {
        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.decrypt(TEST_DATA_FILE, hashedPassword);

        Scanner reader = new Scanner(TEST_DATA_FILE);
        String read = reader.nextLine();
        reader.close();

        assertEquals(TEST_DATA, read);
    }

    @Test
    public void encryptDecrypt_customisedPassword_success() throws Exception {

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encrypt(TEST_DATA_FILE, hashedPassword);
        SecurityUtil.decrypt(TEST_DATA_FILE, hashedPassword);

        Scanner reader = new Scanner(TEST_DATA_FILE);
        String read = reader.nextLine();
        reader.close();

        assertEquals(TEST_DATA, read);
    }

    @Test
    public void decrypt_withPassword_throwsWrongPasswordException() throws Exception {
        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encrypt(TEST_DATA_FILE, hashedPassword);
        thrown.expect(WrongPasswordException.class);
        SecurityUtil.decrypt(TEST_DATA_FILE);
    }

    @Test
    public void encryptDecrypt_wrongPassword_throwsWrongPasswordException() throws Exception {

        SecurityUtil.encrypt(VALID_DATA_FILE, hashedPassword);
        thrown.expect(WrongPasswordException.class);
        SecurityUtil.decryptFile(VALID_DATA_FILE, new Password(WRONG_PASSWORD));
    }

    @Test
    public void encryptDecrypt_wrongPasswordBadPadding_throwsWrongPasswordException() throws Exception {
        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encryptFile(TEST_DATA_FILE, new Password(TEST_PASSWORD));
        thrown.expect(WrongPasswordException.class);
        SecurityUtil.decryptFile(TEST_DATA_FILE, new Password(WRONG_PASSWORD));
    }

    @Test
    public void encryptDecryptFile_wrongPassword_throwsWrongPasswordException() throws Exception {
        byte[] hashedWrong = SecurityUtil.hashPassword(WRONG_PASSWORD);

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encrypt(TEST_DATA_FILE, hashedPassword);
        thrown.expect(WrongPasswordException.class);
        SecurityUtil.decrypt(TEST_DATA_FILE, hashedWrong);
    }

    @Test
    public void encrypt_wrongPasswordLength_throwsAssertionError() throws Exception {

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();
        byte[] truncatedHashedPassword = Arrays.copyOf(hashedPassword, 13);

        thrown.expect(AssertionError.class);
        SecurityUtil.encrypt(TEST_DATA_FILE, truncatedHashedPassword);
    }

    @Test
    public void decrypt_wrongPasswordLength_throwsAssertionError() throws Exception {

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();
        byte[] truncatedHashedPassword = Arrays.copyOf(hashedPassword, 13);

        thrown.expect(AssertionError.class);
        SecurityUtil.decrypt(TEST_DATA_FILE, truncatedHashedPassword);
    }

    @After
    public void reset() throws Exception {
        SecurityUtil.decrypt(VALID_DATA_FILE, hashedPassword);
    }
}
