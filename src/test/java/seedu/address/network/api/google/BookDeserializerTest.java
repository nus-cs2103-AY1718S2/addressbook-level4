package seedu.address.network.api.google;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.book.Book;

public class BookDeserializerTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/BookDeserializerTest/");
    public static final File VALID_RESPONSE_FILE = new File(TEST_DATA_FOLDER + "ValidResponse.json");
    public static final File VALID_RESPONSE_NO_DESC_FILE = new File(TEST_DATA_FOLDER + "ValidResponseNoDesc.json");
    public static final File INVALID_RESPONSE_WRONG_TYPE_FILE =
            new File(TEST_DATA_FOLDER + "InvalidResponseWrongType.json");
    public static final File INVALID_RESPONSE_NO_ISBN_FILE =
            new File(TEST_DATA_FOLDER + "InvalidResponseNoIsbn.json");
    public static final File ERROR_RESPONSE_FILE = new File(TEST_DATA_FOLDER + "ErrorResponse.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Book.class, new BookDeserializer());
        mapper.registerModule(module);
    }

    @Test
    public void deserialize_validResponse_success() throws Exception {
        String json = FileUtil.readFromFile(VALID_RESPONSE_FILE);
        Book book = mapper.readValue(json, Book.class);
        assertEquals("The Book Without a Title", book.getTitle().title);
        assertEquals("This is a valid description.", book.getDescription().description);
    }

    @Test
    public void deserialize_validResponseNoDesc_success() throws Exception {
        String json = FileUtil.readFromFile(VALID_RESPONSE_NO_DESC_FILE);
        Book book = mapper.readValue(json, Book.class);
        assertEquals("The Book Without a Title", book.getTitle().title);
        assertEquals("", book.getDescription().description);
    }

    @Test
    public void deserialize_invalidResponseWrongType_throwsJsonMappingException() throws Exception {
        thrown.expect(JsonMappingException.class);
        String json = FileUtil.readFromFile(INVALID_RESPONSE_WRONG_TYPE_FILE);
        mapper.readValue(json, Book.class);
    }

    @Test
    public void deserialize_invalidResponseNoIsbn_throwsIOException() throws Exception {
        thrown.expect(IOException.class);
        String json = FileUtil.readFromFile(INVALID_RESPONSE_NO_ISBN_FILE);
        mapper.readValue(json, Book.class);
    }

    @Test
    public void deserialize_errorResponse_throwsJsonMappingException() throws Exception {
        thrown.expect(JsonMappingException.class);
        String json = FileUtil.readFromFile(ERROR_RESPONSE_FILE);
        mapper.readValue(json, Book.class);
    }

}
