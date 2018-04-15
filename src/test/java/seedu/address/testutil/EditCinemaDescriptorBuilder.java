package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditCinemaDescriptor;
import seedu.address.model.cinema.Address;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Email;
import seedu.address.model.cinema.Name;
import seedu.address.model.cinema.Phone;

/**
 * A utility class to help with building EditCinemaDescriptor objects.
 */
public class EditCinemaDescriptorBuilder {

    private EditCinemaDescriptor descriptor;

    public EditCinemaDescriptorBuilder() {
        descriptor = new EditCinemaDescriptor();
    }

    public EditCinemaDescriptorBuilder(EditCinemaDescriptor descriptor) {
        this.descriptor = new EditCinemaDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCinemaDescriptor} with fields containing {@code cinema}'s details
     */
    public EditCinemaDescriptorBuilder(Cinema cinema) {
        descriptor = new EditCinemaDescriptor();
        descriptor.setName(cinema.getName());
        descriptor.setPhone(cinema.getPhone());
        descriptor.setEmail(cinema.getEmail());
        descriptor.setAddress(cinema.getAddress());
    }

    /**
     * Sets the {@code Name} of the {@code EditCinemaDescriptor} that we are building.
     */
    public EditCinemaDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCinemaDescriptor} that we are building.
     */
    public EditCinemaDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCinemaDescriptor} that we are building.
     */
    public EditCinemaDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCinemaDescriptor} that we are building.
     */
    public EditCinemaDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    public EditCinemaDescriptor build() {
        return descriptor;
    }
}
