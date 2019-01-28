package seedu.address.logic.descriptors;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.BloodType;
import seedu.address.model.petpatient.Breed;
import seedu.address.model.petpatient.Colour;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.Species;
import seedu.address.model.tag.Tag;

//@@author chialejing
/**
 * Stores the details to edit the pet patient with. Each non-empty field value will replace the
 * corresponding field value of the pet patient.
 */
public class EditPetPatientDescriptor {
    private PetPatientName name;
    private Species species;
    private Breed breed;
    private Colour colour;
    private BloodType bloodType;
    private Nric ownerNric;
    private Set<Tag> tags;

    public EditPetPatientDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditPetPatientDescriptor(EditPetPatientDescriptor toCopy) {
        setName(toCopy.name);
        setSpecies(toCopy.species);
        setBreed(toCopy.breed);
        setColour(toCopy.colour);
        setBloodType(toCopy.bloodType);
        setOwnerNric(toCopy.ownerNric);
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(this.name, this.species, this.breed,
                this.colour, this.bloodType, this.ownerNric, this.tags);
    }

    public void setName(PetPatientName name) {
        this.name = name;
    }

    public Optional<PetPatientName> getName() {
        return Optional.ofNullable(name);
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Optional<Species> getSpecies() {
        return Optional.ofNullable(species);
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Optional<Breed> getBreed() {
        return Optional.ofNullable(breed);
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Optional<Colour> getColour() {
        return Optional.ofNullable(colour);
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Optional<BloodType> getBloodType() {
        return Optional.ofNullable(bloodType);
    }

    public void setOwnerNric(Nric nric) {
        this.ownerNric = nric;
    }

    public Optional<Nric> getOwnerNric() {
        return Optional.ofNullable(ownerNric);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPetPatientDescriptor)) {
            return false;
        }

        // state check
        EditPetPatientDescriptor e = (EditPetPatientDescriptor) other;

        return getName().equals(e.getName())
                && getSpecies().equals(e.getSpecies())
                && getBreed().equals(e.getBreed())
                && getColour().equals(e.getColour())
                && getBloodType().equals(e.getBloodType())
                && getOwnerNric().equals(e.getOwnerNric())
                && getTags().equals(e.getTags());
    }
}
