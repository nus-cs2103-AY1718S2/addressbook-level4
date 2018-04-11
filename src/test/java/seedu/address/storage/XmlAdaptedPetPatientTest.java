package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPetPatient.MISSING_BLOODTYPE_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.XmlAdaptedPetPatient.MISSING_BREED_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.XmlAdaptedPetPatient.MISSING_COLOUR_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.XmlAdaptedPetPatient.MISSING_NAME_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.XmlAdaptedPetPatient.MISSING_OWNER_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.XmlAdaptedPetPatient.MISSING_SPECIES_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPetPatients.JEWEL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.testutil.Assert;

//@@author chialejing
public class XmlAdaptedPetPatientTest {
    private static final String INVALID_NAME = "H@zel";
    private static final String EMPTY_FIELD = "";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = JEWEL.getName().toString();
    private static final String VALID_SPECIES = JEWEL.getSpecies().toString();
    private static final String VALID_BREED = JEWEL.getBreed().toString();
    private static final String VALID_COLOUR = JEWEL.getColour().toString();
    private static final String VALID_BLOODTYPE = JEWEL.getBloodType().toString();
    private static final String VALID_OWNER = JEWEL.getOwner().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = JEWEL.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPetPatientDetails_returnsPetPatient() throws Exception {
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(JEWEL);
        assertEquals(JEWEL, petPatient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(
                INVALID_NAME,
                VALID_SPECIES,
                VALID_BREED,
                VALID_COLOUR,
                VALID_BLOODTYPE,
                VALID_OWNER,
                VALID_TAGS
        );
        String expectedMessage = PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, petPatient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(
                null,
                VALID_SPECIES,
                VALID_BREED,
                VALID_COLOUR,
                VALID_BLOODTYPE,
                VALID_OWNER,
                VALID_TAGS
        );
        String expectedMessage = String.format(
                MISSING_NAME_FIELD_MESSAGE_FORMAT,
                PetPatientName.class.getSimpleName()
        );
        Assert.assertThrows(IllegalValueException.class, expectedMessage, petPatient::toModelType);
    }

    @Test
    public void toModelType_nullSpecies_throwsIllegalValueException() {
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(
                VALID_NAME,
                null,
                VALID_BREED,
                VALID_COLOUR,
                VALID_BLOODTYPE,
                VALID_OWNER,
                VALID_TAGS
        );
        String expectedMessage = String.format(
                MISSING_SPECIES_FIELD_MESSAGE_FORMAT,
                PetPatientName.class.getSimpleName()
        );
        Assert.assertThrows(IllegalValueException.class, expectedMessage, petPatient::toModelType);
    }

    @Test
    public void toModelType_nullBreed_throwsIllegalValueException() {
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(
                VALID_NAME,
                VALID_SPECIES,
                null,
                VALID_COLOUR,
                VALID_BLOODTYPE,
                VALID_OWNER,
                VALID_TAGS
        );
        String expectedMessage = String.format(
                MISSING_BREED_FIELD_MESSAGE_FORMAT,
                PetPatientName.class.getSimpleName()
        );
        Assert.assertThrows(IllegalValueException.class, expectedMessage, petPatient::toModelType);
    }

    @Test
    public void toModelType_nullColour_throwsIllegalValueException() {
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(
                VALID_NAME,
                VALID_SPECIES,
                VALID_BREED,
                null,
                VALID_BLOODTYPE,
                VALID_OWNER,
                VALID_TAGS
        );
        String expectedMessage = String.format(
                MISSING_COLOUR_FIELD_MESSAGE_FORMAT,
                PetPatientName.class.getSimpleName()
        );
        Assert.assertThrows(IllegalValueException.class, expectedMessage, petPatient::toModelType);
    }

    @Test
    public void toModelType_nullBloodType_throwsIllegalValueException() {
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(
                VALID_NAME,
                VALID_SPECIES,
                VALID_BREED,
                VALID_COLOUR,
                null,
                VALID_OWNER,
                VALID_TAGS
        );
        String expectedMessage = String.format(
                MISSING_BLOODTYPE_FIELD_MESSAGE_FORMAT,
                PetPatientName.class.getSimpleName()
        );
        Assert.assertThrows(IllegalValueException.class, expectedMessage, petPatient::toModelType);
    }

    @Test
    public void toModelType_nullOwner_throwsIllegalValueException() {
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(
                VALID_NAME,
                VALID_SPECIES,
                VALID_BREED,
                VALID_COLOUR,
                VALID_BLOODTYPE,
                null,
                VALID_TAGS
        );
        String expectedMessage = String.format(
                MISSING_OWNER_FIELD_MESSAGE_FORMAT,
                PetPatientName.class.getSimpleName()
        );
        Assert.assertThrows(IllegalValueException.class, expectedMessage, petPatient::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPetPatient petPatient = new XmlAdaptedPetPatient(
                VALID_NAME,
                VALID_SPECIES,
                VALID_BREED,
                VALID_COLOUR,
                VALID_BLOODTYPE,
                VALID_OWNER,
                invalidTags
        );
        Assert.assertThrows(IllegalValueException.class, petPatient::toModelType);
    }
}
