# chialejing
###### \java\seedu\address\logic\commands\EditAppointmentDescriptorTest.java
``` java
public class EditAppointmentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditAppointmentDescriptor descriptorWithSameValues = new EditAppointmentDescriptor(DESC_APPOINTMENT_ONE);
        assertTrue(DESC_APPOINTMENT_ONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_APPOINTMENT_ONE.equals(DESC_APPOINTMENT_ONE));

        // null -> returns false
        assertFalse(DESC_APPOINTMENT_ONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_APPOINTMENT_ONE.equals(0));

        // different values -> returns false
        assertFalse(DESC_APPOINTMENT_ONE.equals(DESC_APPOINTMENT_TWO));

        // different owner -> returns false
        EditAppointmentDescriptor editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withOwnerNric(VALID_NRIC_FION).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));

        // different pet patient name -> returns false
        editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withPetPatientName(VALID_NAME_NERO).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));

        // different remark -> returns false
        editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withRemark(VALID_REMARK_TWO).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));

        // different local date time -> returns false
        editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withLocalDateTime(VALID_DATE_TWO).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));

        // different tags -> returns false
        editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withTags(VALID_TAG_VACCINATION).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));
    }
}
```
###### \java\seedu\address\logic\commands\EditPetPatientDescriptorTest.java
``` java
public class EditPetPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPetPatientDescriptor descriptorWithSameValues = new EditPetPatientDescriptor(DESC_JOKER);
        assertTrue(DESC_JOKER.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_JOKER.equals(DESC_JOKER));

        // null -> returns false
        assertFalse(DESC_JOKER.equals(null));

        // different types -> returns false
        assertFalse(DESC_JOKER.equals(0));

        // different values -> returns false
        assertFalse(DESC_JOKER.equals(DESC_NERO));

        // different name -> returns false
        EditPetPatientDescriptor editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER)
                .withName(VALID_NAME_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different species -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withSpecies(VALID_SPECIES_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different breed -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withBreed(VALID_BREED_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different colour -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withColour(VALID_COLOUR_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different bloodType -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withBloodType(VALID_BLOODTYPE_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different owner -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withOwnerNric(VALID_NRIC_FION).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different tags -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withTags(VALID_TAG_FIV).build();
        assertFalse(DESC_JOKER.equals(editedJoker));
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java

    // Tests for PetPatientName

    @Test
    public void parsePetPatientName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePetPatientName((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePetPatientName((Optional<String>) null));
    }

    @Test
    public void parsePetPatientName_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parsePetPatientName(INVALID_PET_PATIENT_NAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parsePetPatientName(Optional.of(INVALID_PET_PATIENT_NAME)));
    }

    @Test
    public void parsePetPatientName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePetPatientName(Optional.empty()).isPresent());
    }

    @Test
    public void parsePetPatientName_validValueWithoutWhitespace_returnsPetPatientName() throws Exception {
        PetPatientName expectedPetPatientName = new PetPatientName(VALID_PET_PATIENT_NAME);
        assertEquals(expectedPetPatientName, ParserUtil.parsePetPatientName(VALID_PET_PATIENT_NAME));
        assertEquals(
                Optional.of(expectedPetPatientName),
                ParserUtil.parsePetPatientName(Optional.of(VALID_PET_PATIENT_NAME))
        );
    }

    @Test
    public void parsePetPatientName_validValueWithWhitespace_returnsTrimmedPetPatientName() throws Exception {
        String petPatientNameWithWhitespace = WHITESPACE + VALID_PET_PATIENT_NAME + WHITESPACE;
        PetPatientName expectedPetPatientName = new PetPatientName(VALID_PET_PATIENT_NAME);
        assertEquals(expectedPetPatientName, ParserUtil.parsePetPatientName(petPatientNameWithWhitespace));
        assertEquals(
                Optional.of(expectedPetPatientName),
                ParserUtil.parsePetPatientName(Optional.of(petPatientNameWithWhitespace))
        );
    }

    // Tests for Species

    @Test
    public void parseSpecies_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSpecies((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSpecies((Optional<String>) null));
    }

    @Test
    public void parseSpecies_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseSpecies(INVALID_PET_PATIENT_SPECIES));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseSpecies(Optional.of(INVALID_PET_PATIENT_SPECIES)));
    }

    @Test
    public void parseSpecies_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseSpecies(Optional.empty()).isPresent());
    }

    @Test
    public void parseSpecies_validValueWithoutWhitespace_returnsSpecies() throws Exception {
        Species expectedSpecies = new Species(VALID_PET_PATIENT_SPECIES);
        assertEquals(expectedSpecies, ParserUtil.parseSpecies(VALID_PET_PATIENT_SPECIES));
        assertEquals(
                Optional.of(expectedSpecies),
                ParserUtil.parseSpecies(Optional.of(VALID_PET_PATIENT_SPECIES))
        );
    }

    @Test
    public void parseSpecies_validValueWithWhitespace_returnsTrimmedSpecies() throws Exception {
        String speciesWithWhitespace = WHITESPACE + VALID_PET_PATIENT_SPECIES + WHITESPACE;
        Species expectedSpecies = new Species(VALID_PET_PATIENT_SPECIES);
        assertEquals(expectedSpecies, ParserUtil.parseSpecies(speciesWithWhitespace));
        assertEquals(
                Optional.of(expectedSpecies),
                ParserUtil.parseSpecies(Optional.of(speciesWithWhitespace))
        );
    }

    // Tests for Breed

    @Test
    public void parseBreed_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBreed((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBreed((Optional<String>) null));
    }

    @Test
    public void parseBreed_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseBreed(INVALID_PET_PATIENT_BREED));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseBreed(Optional.of(INVALID_PET_PATIENT_BREED)));
    }

    @Test
    public void parseBreed_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBreed(Optional.empty()).isPresent());
    }

    @Test
    public void parseBreed_validValueWithoutWhitespace_returnsBreed() throws Exception {
        Breed expectedBreed = new Breed(VALID_PET_PATIENT_BREED);
        assertEquals(expectedBreed, ParserUtil.parseBreed(VALID_PET_PATIENT_BREED));
        assertEquals(
                Optional.of(expectedBreed),
                ParserUtil.parseBreed(Optional.of(VALID_PET_PATIENT_BREED))
        );
    }

    @Test
    public void parseBreed_validValueWithWhitespace_returnsTrimmedBreed() throws Exception {
        String breedWithWhitespace = WHITESPACE + VALID_PET_PATIENT_BREED + WHITESPACE;
        Breed expectedBreed = new Breed(VALID_PET_PATIENT_BREED);
        assertEquals(expectedBreed, ParserUtil.parseBreed(breedWithWhitespace));
        assertEquals(
                Optional.of(expectedBreed),
                ParserUtil.parseBreed(Optional.of(breedWithWhitespace))
        );
    }

    // Tests for Colour

    @Test
    public void parseColour_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseColour((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseColour((Optional<String>) null));
    }

    @Test
    public void parseColour_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseColour(INVALID_PET_PATIENT_COLOUR));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseColour(Optional.of(INVALID_PET_PATIENT_COLOUR)));
    }

    @Test
    public void parseColour_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseColour(Optional.empty()).isPresent());
    }

    @Test
    public void parseColour_validValueWithoutWhitespace_returnsColour() throws Exception {
        Colour expectedColour = new Colour(VALID_PET_PATIENT_COLOUR);
        assertEquals(expectedColour, ParserUtil.parseColour(VALID_PET_PATIENT_COLOUR));
        assertEquals(
                Optional.of(expectedColour),
                ParserUtil.parseColour(Optional.of(VALID_PET_PATIENT_COLOUR))
        );
    }

    @Test
    public void parseColour_validValueWithWhitespace_returnsTrimmedColour() throws Exception {
        String colourWithWhitespace = WHITESPACE + VALID_PET_PATIENT_COLOUR + WHITESPACE;
        Colour expectedColour = new Colour(VALID_PET_PATIENT_COLOUR);
        assertEquals(expectedColour, ParserUtil.parseColour(colourWithWhitespace));
        assertEquals(
                Optional.of(expectedColour),
                ParserUtil.parseColour(Optional.of(colourWithWhitespace))
        );
    }

    // Tests for Blood Type

    @Test
    public void parseBloodType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBloodType((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBloodType((Optional<String>) null));
    }

    @Test
    public void parseBloodType_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseBloodType(INVALID_PET_PATIENT_BLOOD_TYPE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseBloodType(Optional.of(INVALID_PET_PATIENT_BLOOD_TYPE)));
    }

    @Test
    public void parseBloodType_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBloodType(Optional.empty()).isPresent());
    }

    @Test
    public void parseBloodType_validValueWithoutWhitespace_returnsBloodType() throws Exception {
        BloodType expectedBloodType = new BloodType(VALID_PET_PATIENT_BLOOD_TYPE);
        assertEquals(expectedBloodType, ParserUtil.parseBloodType(VALID_PET_PATIENT_BLOOD_TYPE));
        assertEquals(
                Optional.of(expectedBloodType),
                ParserUtil.parseBloodType(Optional.of(VALID_PET_PATIENT_BLOOD_TYPE))
        );
    }

    @Test
    public void parseBloodType_validValueWithWhitespace_returnsTrimmedBloodType() throws Exception {
        String bloodTypeWithWhitespace = WHITESPACE + VALID_PET_PATIENT_BLOOD_TYPE + WHITESPACE;
        BloodType expectedBloodType = new BloodType(VALID_PET_PATIENT_BLOOD_TYPE);
        assertEquals(expectedBloodType, ParserUtil.parseBloodType(bloodTypeWithWhitespace));
        assertEquals(
                Optional.of(expectedBloodType),
                ParserUtil.parseBloodType(Optional.of(bloodTypeWithWhitespace))
        );
    }
}
```
###### \java\seedu\address\model\petpatient\BloodTypeTest.java
``` java
public class BloodTypeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidBloodType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    public void isValidBloodType() {
        // null blood type
        Assert.assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // invalid blood type
        assertFalse(BloodType.isValidBloodType("")); // empty string
        assertFalse(BloodType.isValidBloodType(" ")); // one space only
        assertFalse(BloodType.isValidBloodType("       ")); // spaces only

        // valid blood type
        assertTrue(BloodType.isValidBloodType("dea")); // alphabets only
        assertTrue(BloodType.isValidBloodType("12345")); // numbers only
        assertTrue(BloodType.isValidBloodType("dea 1")); // alphanumeric characters
        assertTrue(BloodType.isValidBloodType("DEA 1")); // with capital letters
        assertTrue(BloodType.isValidBloodType("Some Blood Type That Has A Very Long Name")); // long
        assertTrue(BloodType.isValidBloodType("DEA 1.0+")); // with special characters
        assertTrue(BloodType.isValidBloodType(
                "Some Blood Type That Has A Very Long Name DEA 1.0-")); // long with special character
    }
}
```
###### \java\seedu\address\model\petpatient\BreedTest.java
``` java
public class BreedTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Breed(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidBreed = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Breed(invalidBreed));
    }

    @Test
    public void isValidName() {
        // null breed
        Assert.assertThrows(NullPointerException.class, () -> Breed.isValidBreed(null));

        // invalid breed
        assertFalse(Breed.isValidBreed("")); // empty string
        assertFalse(Breed.isValidBreed(" ")); // spaces only
        assertFalse(Breed.isValidBreed("^")); // only non-alphanumeric characters
        assertFalse(Breed.isValidBreed("ragdoll*")); // contains non-alphanumeric characters
        assertFalse(Breed.isValidBreed("12345")); // numbers only
        assertFalse(Breed.isValidBreed("persian 234")); // alphanumeric characters

        // valid breed
        assertTrue(Breed.isValidBreed("poodle")); // alphabets only
        assertTrue(Breed.isValidBreed("Domestic Shorthair")); // with capital letters
        assertTrue(Breed.isValidBreed("Domestic Shorthair Persian Ragdoll")); // long breed
    }
}
```
###### \java\seedu\address\model\petpatient\ColourTest.java
``` java
public class ColourTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Colour(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidBreed = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Colour(invalidBreed));
    }

    @Test
    public void isValidName() {
        // null colour
        Assert.assertThrows(NullPointerException.class, () -> Colour.isValidColour(null));

        // invalid colour
        assertFalse(Colour.isValidColour("")); // empty string
        assertFalse(Colour.isValidColour(" ")); // spaces only
        assertFalse(Colour.isValidColour("^")); // only non-alphanumeric characters
        assertFalse(Colour.isValidColour("white*")); // contains non-alphanumeric characters
        assertFalse(Colour.isValidColour("12345")); // numbers only
        assertFalse(Colour.isValidColour("black 234")); // alphanumeric characters

        // valid colour
        assertTrue(Colour.isValidColour("brown")); // alphabets only
        assertTrue(Colour.isValidColour("Orange Brown")); // with capital letters
        assertTrue(Colour.isValidColour("Orange Brown White Red Blue Yellow Green")); // long colour
    }
}
```
###### \java\seedu\address\model\petpatient\PetPatientNameTest.java
``` java
public class PetPatientNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PetPatientName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PetPatientName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> PetPatientName.isValidName(null));

        // invalid name
        assertFalse(PetPatientName.isValidName("")); // empty string
        assertFalse(PetPatientName.isValidName(" ")); // spaces only
        assertFalse(PetPatientName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(PetPatientName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(PetPatientName.isValidName("joker the second")); // alphabets only
        assertTrue(PetPatientName.isValidName("12345")); // numbers only
        assertTrue(PetPatientName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(PetPatientName.isValidName("Aye Captain")); // with capital letters
        assertTrue(PetPatientName.isValidName("Aye Captain Howdy There Jr 2nd")); // long names
    }
}
```
###### \java\seedu\address\model\petpatient\SpeciesTest.java
``` java
public class SpeciesTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Species(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidSpecies = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Species(invalidSpecies));
    }

    @Test
    public void isValidName() {
        // null species
        Assert.assertThrows(NullPointerException.class, () -> Species.isValidSpecies(null));

        // invalid species
        assertFalse(Species.isValidSpecies("")); // empty string
        assertFalse(Species.isValidSpecies(" ")); // spaces only
        assertFalse(Species.isValidSpecies("^")); // only non-alphanumeric characters
        assertFalse(Species.isValidSpecies("cat*")); // contains non-alphanumeric characters
        assertFalse(Species.isValidSpecies("12345")); // numbers only
        assertFalse(Species.isValidSpecies("cat 234")); // alphanumeric characters

        // valid species
        assertTrue(Species.isValidSpecies("dog")); // alphabets only
        assertTrue(Species.isValidSpecies("Some Species")); // with capital letters
        assertTrue(Species.isValidSpecies("Some Species That Has Very Long Naming Term")); // long species
    }
}
```
###### \java\seedu\address\model\UniquePetPatientListTest.java
``` java
public class UniquePetPatientListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePetPatientList uniquePetPatientList = new UniquePetPatientList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePetPatientList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPetPatientTest.java
``` java
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
```
###### \java\seedu\address\testutil\EditAppointmentDescriptorBuilder.java
``` java
/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {

    private EditAppointmentDescriptor descriptor;

    public EditAppointmentDescriptorBuilder() {
        descriptor = new EditAppointmentDescriptor();
    }

    public EditAppointmentDescriptorBuilder(EditAppointmentDescriptor descriptor) {
        this.descriptor = new EditAppointmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAppointmentDescriptor} with fields containing {@code appointment}'s details
     */
    public EditAppointmentDescriptorBuilder(Appointment appointment) {
        descriptor = new EditAppointmentDescriptor();
        descriptor.setOwnerNric(appointment.getOwnerNric());
        descriptor.setPetPatientName(appointment.getPetPatientName());
        descriptor.setRemark(appointment.getRemark());
        descriptor.setLocalDateTime(appointment.getDateTime());
        descriptor.setTags(appointment.getAppointmentTags());
    }

    /**
     * Sets the {@code NRIC } of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withOwnerNric(String ownerNric) {
        descriptor.setOwnerNric(new Nric(ownerNric));
        return this;
    }

    /**
     * Sets the {@code PetPatientName} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withPetPatientName(String petPatientName) {
        descriptor.setPetPatientName(new PetPatientName(petPatientName));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withLocalDateTime(String localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        descriptor.setLocalDateTime(LocalDateTime.parse(localDateTime, formatter));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditAppointmentDescriptor}
     * that we are building.
     */
    public EditAppointmentDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditAppointmentDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\EditPetPatientDescriptorBuilder.java
``` java
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

```
###### \java\seedu\address\testutil\PetPatientBuilder.java
``` java
/**
 * A utility class to help with building PetPatient objects.
 */
public class PetPatientBuilder {
    public static final String DEFAULT_NAME = "Joseph";
    public static final String DEFAULT_SPECIES = "Cat";
    public static final String DEFAULT_BREED = "Persian Ragdoll";
    public static final String DEFAULT_COLOUR = "Brown";
    public static final String DEFAULT_BLOODTYPE = "AB";
    public static final String DEFAULT_OWNER = "G1234567B";
    public static final String DEFAULT_TAGS = "Injured";

    private PetPatientName name;
    private Species species;
    private Breed breed;
    private Colour colour;
    private BloodType bloodType;
    private Nric ownerNric;
    private Set<Tag> tags;

    public PetPatientBuilder() {
        name = new PetPatientName(DEFAULT_NAME);
        species = new Species(DEFAULT_SPECIES);
        breed = new Breed(DEFAULT_BREED);
        colour = new Colour(DEFAULT_COLOUR);
        bloodType = new BloodType(DEFAULT_BLOODTYPE);
        ownerNric = new Nric(DEFAULT_OWNER);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the PetPatientBuilder with the data of {@code petPatientToCopy}.
     */
    public PetPatientBuilder(PetPatient petPatientToCopy) {
        name = petPatientToCopy.getName();
        species = petPatientToCopy.getSpecies();
        breed = petPatientToCopy.getBreed();
        colour = petPatientToCopy.getColour();
        bloodType = petPatientToCopy.getBloodType();
        ownerNric = petPatientToCopy.getOwner();
        tags = new HashSet<>(petPatientToCopy.getTags());
    }

    /**
     * Sets the {@code PetPatientName} of the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withName(String name) {
        this.name = new PetPatientName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the species of the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withSpecies(String species) {
        this.species = new Species(species);
        return this;
    }

    /**
     * Sets the breed of the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withBreed(String breed) {
        this.breed = new Breed(breed);
        return this;
    }

    /**
     * Sets the colour of the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withColour(String colour) {
        this.colour = new Colour(colour);
        return this;
    }

    /**
     * Sets the blood type of the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code PetPatient} that we are building.
     * @param nric
     * @return
     */
    public PetPatientBuilder withOwnerNric(String nric) {
        this.ownerNric = new Nric(nric);
        return this;
    }

    public PetPatient build() {
        return new PetPatient(name, species, breed, colour, bloodType, ownerNric, tags);
    }

}
```
###### \java\seedu\address\testutil\TypicalPetPatients.java
``` java
/**
 * A utility class containing a list of {@code PetPatient} objects to be used in tests.
 */
public class TypicalPetPatients {
    // Pet patient with tags
    public static final PetPatient JEWEL = new PetPatientBuilder()
            .withName("Jewel")
            .withSpecies("Cat")
            .withBreed("Persian Ragdoll")
            .withColour("calico")
            .withBloodType("AB")
            .withOwnerNric(TypicalPersons.ALICE.getNric().toString())
            .withTags("depression", "test").build();

    // Pet patient with no tags
    public static final PetPatient JOKER = new PetPatientBuilder()
            .withName("Joker")
            .withSpecies("Cat")
            .withBreed("Domestic Shorthair")
            .withColour("brown and white")
            .withBloodType("A")
            .withOwnerNric(TypicalPersons.BENSON.getNric().toString())
            .withTags(new String[]{}).build();

    // Pet patient with tags
    public static final PetPatient JENN = new PetPatientBuilder()
            .withName("Jenn")
            .withSpecies("Dog")
            .withBreed("Golden Retriever")
            .withColour("golden")
            .withBloodType("DEA 4+")
            .withOwnerNric(TypicalPersons.BENSON.getNric().toString())
            .withTags("3legged").build();

    // Manually added
    public static final PetPatient KARUPIN = new PetPatientBuilder()
            .withName("Karupin")
            .withSpecies("Cat")
            .withBreed("Himalayan")
            .withColour("sealpoint")
            .withBloodType("AB")
            .withOwnerNric(TypicalPersons.ALICE.getNric().toString())
            .withTags(new String[]{}).build();

    // Manually added - Pet Patient's details found in {@code CommandTestUtil}
    public static final PetPatient NERO = new PetPatientBuilder()
            .withName(VALID_NAME_NERO)
            .withSpecies(VALID_SPECIES_NERO)
            .withBreed(VALID_BREED_NERO)
            .withColour(VALID_COLOUR_NERO)
            .withBloodType(VALID_BLOODTYPE_NERO)
            .withOwnerNric(VALID_NRIC_BOB)
            .withTags(new String[]{}).build();

    private TypicalPetPatients() {}

    public static List<PetPatient> getTypicalPetPatients() {
        return new ArrayList<>(Arrays.asList(JOKER, JEWEL, JENN));
    }
}
```
