# chialejing
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
    private String species;
    private String breed;
    private String colour;
    private String bloodType;
    private Nric ownerNric;
    private Set<Tag> tags;

    public PetPatientBuilder() {
        name = new PetPatientName(DEFAULT_NAME);
        species = DEFAULT_SPECIES;
        breed = DEFAULT_BREED;
        colour = DEFAULT_COLOUR;
        bloodType = DEFAULT_BLOODTYPE;
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
        this.species = species;
        return this;
    }

    /**
     * Sets the breed of the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withBreed(String breed) {
        this.breed = breed;
        return this;
    }

    /**
     * Sets the colour of the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withColour(String colour) {
        this.colour = colour;
        return this;
    }

    /**
     * Sets the blood type of the {@code PetPatient} that we are building.
     */
    public PetPatientBuilder withBloodType(String bloodType) {
        this.bloodType = bloodType;
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
            .withColour("Calico")
            .withBloodType("AB")
            .withOwnerNric(TypicalPersons.ALICE.getNric().toString())
            .withTags("Depression", "Test").build();

    // Pet patient with no tags
    public static final PetPatient JOKER = new PetPatientBuilder()
            .withName("Joker")
            .withSpecies("Cat")
            .withBreed("Domestic Shorthair")
            .withColour("Brown and White")
            .withBloodType("O")
            .withOwnerNric(TypicalPersons.BENSON.getNric().toString())
            .withTags(new String[]{}).build();

    // Manually added
    public static final PetPatient KARUPIN = new PetPatientBuilder()
            .withName("Karupin")
            .withSpecies("Cat")
            .withBreed("Himalayan")
            .withColour("Sealpoint")
            .withBloodType("AB")
            .withOwnerNric(TypicalPersons.HOON.getNric().toString())
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
        return new ArrayList<>(Arrays.asList(JOKER, JEWEL));
    }
}
```
