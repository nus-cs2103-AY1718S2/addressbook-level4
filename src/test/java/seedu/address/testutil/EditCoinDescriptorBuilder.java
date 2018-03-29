package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.TagCommand.EditCoinDescriptor;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCoinDescriptor objects.
 */
public class EditCoinDescriptorBuilder {

    private EditCoinDescriptor descriptor;

    public EditCoinDescriptorBuilder() {
        descriptor = new EditCoinDescriptor();
    }

    public EditCoinDescriptorBuilder(EditCoinDescriptor descriptor) {
        this.descriptor = new EditCoinDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCoinDescriptor} with fields containing {@code coin}'s details
     */
    public EditCoinDescriptorBuilder(Coin coin) {
        descriptor = new EditCoinDescriptor();
        descriptor.setCode(coin.getCode());
        descriptor.setTags(coin.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditCoinDescriptor} that we are building.
     */
    public EditCoinDescriptorBuilder withName(String name) {
        descriptor.setCode(new Code(name));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCoinDescriptor}
     * that we are building.
     */
    public EditCoinDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCoinDescriptor build() {
        return descriptor;
    }
}
