package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedPair.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPairs.RANDOM_PAIR_A;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pair.PairHash;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;
import seedu.address.testutil.Assert;

//@@author alexawangzi
public class XmlAdaptedPairTest {
    private static final String INVALID_STUDENT_NAME = "R@chel";
    private static final String INVALID_TUTOR_NAME = "Bens[]n";

    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRICE = "-50";
    private static final String INVALID_LEVEL = "kindergarden";
    private static final String INVALID_SUBJECT = "fake news";

    private static final String VALID_STUDENT_NAME = RANDOM_PAIR_A.getStudentName().toString();
    private static final String VALID_TUTOR_NAME = RANDOM_PAIR_A.getTutorName().toString();
    private static final String VALID_SUBJECT = RANDOM_PAIR_A.getSubject().toString();
    private static final String VALID_LEVEL = RANDOM_PAIR_A.getLevel().toString();
    private static final String VALID_PRICE = RANDOM_PAIR_A.getPrice().toString();
    private static final String VALID_PAIRHASH = RANDOM_PAIR_A.getPairHash().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = RANDOM_PAIR_A.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPairDetails_returnsPair() throws Exception {
        XmlAdaptedPair pair = new XmlAdaptedPair(RANDOM_PAIR_A);
        assertEquals(RANDOM_PAIR_A, pair.toModelType());
    }

    @Test
    public void toModelType_invalidStudentName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(INVALID_STUDENT_NAME, INVALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullStudentName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(null, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidTutorName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, INVALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullTutorName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, null,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, invalidTags, VALID_PAIRHASH);
        Assert.assertThrows(IllegalValueException.class, pair::toModelType);
    }

    @Test
    public void toModelType_invalidLevel_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, INVALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Level.MESSAGE_LEVEL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullLevel_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, null, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                INVALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Subject.MESSAGE_SUBJECT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                null, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, INVALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, null, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullPairHash_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PairHash.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void testXmlAdaptedPairEquality() {
        XmlAdaptedPair alice = new XmlAdaptedPair(RANDOM_PAIR_A);
        XmlAdaptedPair copy = new XmlAdaptedPair(RANDOM_PAIR_A);
        assertTrue(alice.equals(alice));
        assertTrue(alice.equals(copy)); //check equality if values are equal
        assertFalse(alice.equals(RANDOM_PAIR_A)); //check not equal if type is different
    }

}
