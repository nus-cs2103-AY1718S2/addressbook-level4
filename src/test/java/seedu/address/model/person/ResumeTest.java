package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import seedu.address.logic.parser.ResumeUtil;
import seedu.address.testutil.Assert;
//@@author mhq199657
public class ResumeTest {
    private static final String TEST_DATA_FOLDER = "src/test/data/ResumeTest/";
    @Test
    public void constructor_null_constructionSuccessValueNull() {
        assertNull(new Resume(null).value);
    }

    @Test
    public void constructor_invalidResume_throwsIllegalArgumentException() {
        String invalidResume = formFilePath("");
        Assert.assertThrows(IllegalArgumentException.class, () -> new Resume(invalidResume));
    }

    @Test
    public void constructor_validResume_constructionSuccess() {
        String validResume = formFilePath("valid.pdf");
        assertEquals(validResume, new Resume(validResume).value);
    }

    private String formFilePath(String fileName) {
        return TEST_DATA_FOLDER + fileName;
    }

    @Test
    public void isValidResume() {
        // null resume
        Assert.assertThrows(NullPointerException.class, () -> Resume.isValidResume(null));

        // invalid resume file name
        assertFalse(Resume.isValidResume(formFilePath(""))); // empty string
        assertFalse(Resume.isValidResume(formFilePath(" "))); // spaces only
        assertFalse(Resume.isValidResume(formFilePath("fileNot.exist"))); // not a existing file name
        assertFalse(Resume.isValidResume(formFilePath("largeFile.pdf"))); // greater than 1MB
        assertFalse(Resume.isValidResume(formFilePath("fake.pdf"))); // a fake pdf

        // valid resume file name
        assertTrue(Resume.isValidResume(formFilePath("1 2.pdf"))); // spaces within fileName
        assertTrue(Resume.isValidResume(formFilePath("valid.pdf")));
        assertTrue(Resume.isValidResume(formFilePath("validPdf")));
        assertTrue(Resume.isValidResume(formFilePath("alice.pdf")));
        assertTrue(Resume.isValidResume(formFilePath("benson.pdf")));
        assertTrue(Resume.isValidResume(formFilePath("bob.pdf")));
        assertTrue(Resume.isValidResume(formFilePath("daniel.pdf")));
        assertTrue(Resume.isValidResume(formFilePath("george.pdf")));
        assertTrue(Resume.isValidResume(formFilePath("hoon.pdf")));

    }

    @Test
    public void equals() {
        //neither hashed
        assertEquals(new Resume(formFilePath("1 2.pdf")), new Resume(formFilePath("1 2.pdf")));
        //one hashed
        Resume hashedResume = null;
        try {
            hashedResume = ResumeUtil.process(new Resume(formFilePath("1 2.pdf")));
        } catch (IOException ioe) {
            throw new AssertionError("This should never happen.");
        }
        assertEquals(new Resume(formFilePath("1 2.pdf")), hashedResume);
        //both hashed
        Resume anotherhashedResume = null;
        try {
            anotherhashedResume = ResumeUtil.process(new Resume(formFilePath("1 2.pdf")));
        } catch (IOException ioe) {
            throw new AssertionError("This should never happen.");
        }
        assertEquals(anotherhashedResume, hashedResume);
    }

}
