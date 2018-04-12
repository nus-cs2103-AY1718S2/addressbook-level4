package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCoin.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCoins.BTCZ;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.coin.Code;
import seedu.address.testutil.Assert;

public class XmlAdaptedCoinTest {
    private static final String INVALID_NAME = "B@TC";
    private static final String INVALID_TAG = "#fav";

    private static final String VALID_NAME = BTCZ.getCode().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BTCZ.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validCoinDetails_returnsCoin() throws Exception {
        XmlAdaptedCoin coin = new XmlAdaptedCoin(BTCZ);
        assertEquals(BTCZ, coin.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedCoin coin =
                new XmlAdaptedCoin(INVALID_NAME, VALID_TAGS);
        String expectedMessage = Code.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, coin::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedCoin coin = new XmlAdaptedCoin(null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, coin::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedCoin coin =
                new XmlAdaptedCoin(VALID_NAME, invalidTags);
        Assert.assertThrows(IllegalValueException.class, coin::toModelType);
    }

}
