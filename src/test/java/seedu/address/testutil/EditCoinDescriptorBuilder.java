package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditCoinDescriptor;
import seedu.address.model.coin.Address;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.Email;
import seedu.address.model.coin.Name;
import seedu.address.model.coin.Phone;
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
        descriptor.setName(coin.getName());
        descriptor.setPhone(coin.getPhone());
        descriptor.setEmail(coin.getEmail());
        descriptor.setAddress(coin.getAddress());
        descriptor.setTags(coin.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditCoinDescriptor} that we are building.
     */
    public EditCoinDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCoinDescriptor} that we are building.
     */
    public EditCoinDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCoinDescriptor} that we are building.
     */
    public EditCoinDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCoinDescriptor} that we are building.
     */
    public EditCoinDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
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
