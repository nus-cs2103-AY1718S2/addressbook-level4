package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Coin objects.
 */
public class CoinBuilder {

    public static final String DEFAULT_NAME = "XTC";
    public static final String DEFAULT_TAGS = "friends";

    private Code code;
    private Set<Tag> tags;

    public CoinBuilder() {
        code = new Code(DEFAULT_NAME);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the CoinBuilder with the data of {@code coinToCopy}.
     */
    public CoinBuilder(Coin coinToCopy) {
        code = coinToCopy.getCode();
        tags = new HashSet<>(coinToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Coin} that we are building.
     */
    public CoinBuilder withName(String name) {
        this.code = new Code(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Coin} that we are building.
     */
    public CoinBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Coin build() {
        return new Coin(code, tags);
    }

}
