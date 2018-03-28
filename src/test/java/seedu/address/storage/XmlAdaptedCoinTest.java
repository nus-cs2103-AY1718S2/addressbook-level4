package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCoin.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCoins.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedCoinTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getCode().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validCoinDetails_returnsCoin() throws Exception {
        XmlAdaptedCoin coin = new XmlAdaptedCoin(BENSON);
        assertEquals(BENSON, coin.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedCoin coin =
                new XmlAdaptedCoin(INVALID_NAME, VALID_PHONE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, coin::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedCoin coin = new XmlAdaptedCoin(null, VALID_PHONE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, coin::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedCoin coin =
                new XmlAdaptedCoin(VALID_NAME, INVALID_PHONE, VALID_TAGS);
        String expectedMessage = Code.MESSAGE_CODE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, coin::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedCoin coin = new XmlAdaptedCoin(VALID_NAME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, coin::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedCoin coin =
                new XmlAdaptedCoin(VALID_NAME, VALID_PHONE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, coin::toModelType);
    }

}
