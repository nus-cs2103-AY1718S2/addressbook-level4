package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.WrongPasswordException;

public class SecurityUtilTest {
    private static final File TEST_DATA_FILE = new File("./src/test/data/sandbox/temp");
    private static final String TEST_DATA = new String("Test Data");
    private static final String TEST_PASSWORD =  new String("test");
    private static final String WRONG_PASSWORD = new String("wrong");


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void encryptDecrypt_defaultPassword_success() throws Exception {

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encrypt(TEST_DATA_FILE);
        SecurityUtil.decrypt(TEST_DATA_FILE);

        Scanner reader = new Scanner(TEST_DATA_FILE);
        String read = reader.nextLine();
        reader.close();

        assertEquals(TEST_DATA, read);
    }

    @Test
    public void encryptDecrypt_customisedPassword_success() throws Exception {
        byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);

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
    public void encryptDecrypt_wrongPassword_throwsWrongPasswordException() throws Exception {

        byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);
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

        byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);
        hashedPassword = Arrays.copyOf(hashedPassword, 13);

        thrown.expect(AssertionError.class);
        SecurityUtil.encrypt(TEST_DATA_FILE, hashedPassword);
    }

    @Test
    public void decrypt_wrongPasswordLength_throwsAssertionError() throws Exception {

        byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();
        hashedPassword = Arrays.copyOf(hashedPassword, 13);

        thrown.expect(AssertionError.class);
        SecurityUtil.decrypt(TEST_DATA_FILE, hashedPassword);
    }
}
