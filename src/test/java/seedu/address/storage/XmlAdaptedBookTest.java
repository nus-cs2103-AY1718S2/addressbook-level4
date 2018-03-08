package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.Description;
import seedu.address.model.book.Title;
import seedu.address.testutil.Assert;

public class XmlAdaptedBookTest {

    private static final String VALID_TITLE = ARTEMIS.getTitle().toString();
    private static final String VALID_DESCRIPTION = ARTEMIS.getDescription().toString();
    private static final List<XmlAdaptedAuthor> VALID_AUTHORS = ARTEMIS.getAuthors().stream()
            .map(XmlAdaptedAuthor::new).collect(Collectors.toList());
    private static final List<XmlAdaptedCategory> VALID_CATEGORIES = ARTEMIS.getCategories().stream()
            .map(XmlAdaptedCategory::new).collect(Collectors.toList());

    @Test
    public void toModelType_validBookDetails_returnsBook() throws Exception {
        XmlAdaptedBook book = new XmlAdaptedBook(ARTEMIS);
        assertEquals(ARTEMIS, book.toModelType());
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(null, VALID_DESCRIPTION, VALID_AUTHORS, VALID_CATEGORIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_TITLE, null, VALID_AUTHORS, VALID_CATEGORIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

}
