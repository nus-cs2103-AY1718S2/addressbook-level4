package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

import seedu.address.model.person.Resume;

public class ResumeUtilTest {
    private static final String TEST_DATA_FOLDER = "src/test/data/ResumeTest/";

    @Test
    public void process_validResume_fileCopied() {
        Resume resume = new Resume(formFilePath("1 2.pdf"));
        Resume processed = null;
        try {
            processed = ResumeUtil.process(resume);
        } catch (IOException ioe) {
            throw new AssertionError("This should not be reachable.");
        }
        File resumeFile = new File(System.getProperty("user.dir") + File.separator + resume.value);
        File processedFile = new File(System.getProperty("user.dir") + File.separator + processed.value);
        assert(resumeFile.exists());
        assert(processedFile.exists());
        byte[] f1 = null;
        byte[] f2 = null;
        try {
            f1 = Files.readAllBytes(resumeFile.toPath());
            f2 = Files.readAllBytes(processedFile.toPath());
        } catch (IOException ioe) {
            throw new AssertionError("This should not be reachable.");
        }
        assertTrue(Arrays.equals(f1, f2));
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            throw new AssertionError("This should never happen.");
        }
        assert(md != null);
        md.update(f2);
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        assertEquals(hash, processed.value.split("_")[1]);
    }

    @Test
    public void process_differentResumes_differentHash() {
        Resume resume1 = new Resume(formFilePath("1 2.pdf"));
        Resume resume2 = new Resume(formFilePath("alice.pdf"));
        Resume processed1 = null;
        Resume processed2 = null;
        try {
            processed1 = ResumeUtil.process(resume1);
            processed2 = ResumeUtil.process(resume2);
        } catch (IOException ioe) {
            throw new AssertionError("This should not be reachable.");
        }
        assertTrue(processed1.value.split("_")[1] != processed2.value.split("_")[1]);
    }

    private String formFilePath(String fileName) {
        return TEST_DATA_FOLDER + fileName;
    }
}
