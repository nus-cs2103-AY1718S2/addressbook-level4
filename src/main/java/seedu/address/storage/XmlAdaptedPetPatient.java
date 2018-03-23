package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the PetPatient.
 */
public class XmlAdaptedPetPatient {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pet patient's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String species;
    @XmlElement(required = true)
    private String breed;
    @XmlElement(required = true)
    private String colour;
    @XmlElement(required = true)
    private String bloodType;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPetPatient.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPetPatient() {}

    /**
     * Constructs an {@code XmlAdaptedPetPatient} with the given pet patient details.
     */
    public XmlAdaptedPetPatient(String name, String species, String breed, String colour,
                            String bloodType, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.colour = colour;
        this.bloodType = bloodType;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given PetPatient into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPetPatient
     */
    public XmlAdaptedPetPatient(PetPatient source) {
        name = source.getName().fullName;
        species = source.getSpecies();
        breed = source.getBreed();
        colour = source.getColour();
        bloodType = source.getBloodType();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted pet patient object into the model's PetPatient object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pet patient
     */
    public PetPatient toModelType() throws IllegalValueException {
        final List<Tag> petPatientTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            petPatientTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, PetPatientName.class.getSimpleName()));
        }
        if (!PetPatientName.isValidName(this.name)) {
            throw new IllegalValueException(PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS);
        }
        final PetPatientName name = new PetPatientName(this.name);

        if (this.species == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }

        if (this.breed == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }

        if (this.colour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }

        if (this.bloodType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }

        final Set<Tag> tags = new HashSet<>(petPatientTags);
        return new PetPatient(name, species, breed, colour, bloodType, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPetPatient)) {
            return false;
        }

        XmlAdaptedPetPatient otherPetPatient = (XmlAdaptedPetPatient) other;
        return Objects.equals(name, otherPetPatient.name)
                && Objects.equals(species, otherPetPatient.species)
                && Objects.equals(breed, otherPetPatient.breed)
                && Objects.equals(colour, otherPetPatient.colour)
                && Objects.equals(bloodType, otherPetPatient.bloodType)
                && tagged.equals(otherPetPatient.tagged);
    }
}
