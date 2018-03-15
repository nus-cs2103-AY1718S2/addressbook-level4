package seedu.address.model.petpatient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a PetPatient in the address book.
 * Guarantees: details are present, field values are validated.
 */
public class PetPatient {
    private final PetPatientName name;
    private final String species; // e.g. dogs, cats, birds, etc.
    private final String breed; // different varieties of the same species
    private final String colour;
    private final String bloodType;
    private final Person owner; // maybe we can link by NRIC?

    private final UniqueTagList tags;

    private final Optional<Date> dateOfBirth; // can be null
    private StringBuilder medicalHistory; // can be null (initially)

    public PetPatient(PetPatientName name,
                      String species,
                      String breed,
                      String colour,
                      String bloodType,
                      Set<Tag> tags) {
        requireAllNonNull(name, species, breed, colour, bloodType, tags);
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.colour = colour;
        this.bloodType = bloodType;
        this.tags = new UniqueTagList(tags);

        this.dateOfBirth = null;
        this.medicalHistory = new StringBuilder();
        this.owner = null;
    }

    public PetPatient(PetPatientName name,
                      String species,
                      String breed,
                      String colour,
                      String bloodType,
                      Person owner,
                      Optional<Date> dateOfBirth,
                      Set<Tag> tags) {
        requireAllNonNull(name, species, breed, colour, bloodType, owner, dateOfBirth, tags);
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.colour = colour;
        this.bloodType = bloodType;
        this.owner = owner;
        this.dateOfBirth = dateOfBirth;
        this.tags = new UniqueTagList(tags);

        this.medicalHistory = new StringBuilder();
    }

    public PetPatientName getName() {
        return name;
    }

    public Optional<Date> getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    public String getColour() {
        return colour;
    }

    public String getBloodType() {
        return bloodType;
    }

    public Person getOwner() {
        return owner;
    }

    public StringBuilder getMedicalHistory() {
        return medicalHistory;
    }

    public void updateMedicalHistory(String newContent) {
        this.medicalHistory.append(newContent);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PetPatient)) {
            return false;
        }

        PetPatient otherPetPatient = (PetPatient) other;
        return otherPetPatient.getName().equals(this.getName())
                && otherPetPatient.getSpecies().equals(this.getSpecies())
                && otherPetPatient.getBreed().equals(this.getBreed())
                && otherPetPatient.getColour().equals(this.getColour())
                && otherPetPatient.getBloodType().equals(this.getBloodType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, species, breed, colour, bloodType, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("")
                // .append(" Owner's Name: ")
                // .append(getOwner().getName())
                .append(" Name: ")
                .append(getName())
                .append(" Species: ")
                .append(getSpecies())
                .append(" Breed: ")
                .append(getBreed())
                .append(" Color: ")
                .append(getColour())
                .append(" Blood Type: ")
                .append(getBloodType())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
