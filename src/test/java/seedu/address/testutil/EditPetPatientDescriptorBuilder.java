package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.descriptors.EditPetPatientDescriptor;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.BloodType;
import seedu.address.model.petpatient.Breed;
import seedu.address.model.petpatient.Colour;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.Species;
import seedu.address.model.tag.Tag;

//@@author chialejing
/**
 * A utility class to help with building EditPetPatientDescriptor objects.
 */
public class EditPetPatientDescriptorBuilder {

    private EditPetPatientDescriptor descriptor;

    public EditPetPatientDescriptorBuilder() {
        descriptor = new EditPetPatientDescriptor();
    }

    public EditPetPatientDescriptorBuilder(EditPetPatientDescriptor descriptor) {
        this.descriptor = new EditPetPatientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPetPatientDescriptor} with fields containing {@code petPatient}'s details
     */
    public EditPetPatientDescriptorBuilder(PetPatient petPatient) {
        descriptor = new EditPetPatientDescriptor();
        descriptor.setName(petPatient.getName());
        descriptor.setSpecies(petPatient.getSpecies());
        descriptor.setBreed(petPatient.getBreed());
        descriptor.setColour(petPatient.getColour());
        descriptor.setBloodType(petPatient.getBloodType());
        descriptor.setOwnerNric(petPatient.getOwner());
        descriptor.setTags(petPatient.getTags());
    }

    /**
     * Sets the {@code PetPatientName} of the {@code EditPetPatientDescriptor} that we are building.
     */
    public EditPetPatientDescriptorBuilder withName(String name) {
        descriptor.setName(new PetPatientName(name));
        return this;
    }

    /**
     * Sets the {@code Species} of the {@code EditPetPatientDescriptor} that we are building.
     */
    public EditPetPatientDescriptorBuilder withSpecies(String species) {
        descriptor.setSpecies(new Species(species));
        return this;
    }

    /**
     * Sets the {@code Breed} of the {@code EditPetPatientDescriptor} that we are building.
     */
    public EditPetPatientDescriptorBuilder withBreed(String breed) {
        descriptor.setBreed(new Breed(breed));
        return this;
    }

    /**
     * Sets the {@code Colour} of the {@code EditPetPatientDescriptor} that we are building.
     */
    public EditPetPatientDescriptorBuilder withColour(String colour) {
        descriptor.setColour(new Colour(colour));
        return this;
    }

    /**
     * Sets the {@code BloodType } of the {@code EditPetPatientDescriptor} that we are building.
     */
    public EditPetPatientDescriptorBuilder withBloodType(String bloodType) {
        descriptor.setBloodType(new BloodType(bloodType));
        return this;
    }

    /**
     * Sets the {@code NRIC } of the {@code EditPetPatientDescriptor} that we are building.
     */
    public EditPetPatientDescriptorBuilder withOwnerNric(String nric) {
        descriptor.setOwnerNric(new Nric(nric));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPetPatientDescriptor}
     * that we are building.
     */
    public EditPetPatientDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPetPatientDescriptor build() {
        return descriptor;
    }
}

