package seedu.flashy.testutil;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import seedu.flashy.logic.commands.EditCardCommand;
import seedu.flashy.logic.commands.EditCardCommand.EditCardDescriptor;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.tag.Tag;

/**
 * A utility class to help with building EditCardDescriptor objects.
 */
public class EditCardDescriptorBuilder {
    //@@author shawnclq
    private EditCardDescriptor descriptor;

    public EditCardDescriptorBuilder() {
        descriptor = new EditCardCommand.EditCardDescriptor();
    }

    public EditCardDescriptorBuilder(EditCardDescriptor descriptor) {
        this.descriptor = new EditCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCardDescriptor} with fields containing {@code card}'s details
     */
    public EditCardDescriptorBuilder(Card card) {
        descriptor = new EditCardDescriptor();
        descriptor.setFront(card.getFront());
        descriptor.setBack(card.getBack());
    }

    /**
     * Sets the {@code Front} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withFront(String front) {
        descriptor.setFront(front);
        return this;
    }

    /**
     * Sets the {@code Back} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withBack(String back) {
        descriptor.setBack(back);
        return this;
    }

    /**
     * Sets the {@code UUID} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withUuid(UUID uuid) {
        descriptor.setId(uuid);
        return this;
    }

    /**
     * Sets the {@code Options} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withOptions(List<String> options) {
        descriptor.setOptions(options);
        return this;
    }
    //@@author

    //@@author jethrokuan
    /**
     * Sets the {@code tagsToAdd} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withTagsToAdd(Set<Tag> tags) {
        descriptor.setTagsToAdd(tags);
        return this;
    }

    /**
     * Sets the {@code tagsToRemove} of the {@code EditCardDescriptor} t hat we are building.
     */
    public EditCardDescriptorBuilder withTagsToRemove(Set<Tag> tags) {
        descriptor.setTagsToRemove(tags);
        return this;
    }
    //@@author

    public EditCardDescriptor build() {
        return descriptor;
    }
}
