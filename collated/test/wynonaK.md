# wynonaK
###### \java\seedu\address\model\appointment\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null remark
        Assert.assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remarks
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(" ")); // spaces only

        // valid remarks
        assertTrue(Remark.isValidRemark("Might need a house visit."));
        assertTrue(Remark.isValidRemark("-")); // one character
        assertTrue(Remark.isValidRemark("Might need a house visit, and medication.")); // long address
    }
}
```
###### \java\seedu\address\model\appointment\UniqueAppointmentListTest.java
``` java
public class UniqueAppointmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAppointmentList uniquePersonList = new UniqueAppointmentList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedAppointmentTest.java
``` java
public class XmlAdaptedAppointmentTest {

    private static final String INVALID_OWNER_NRIC = "S012345AB";
    private static final String INVALID_PET_PATIENT_NAME = "L@osai";
    private static final String INVALID_REMARK = " ";
    private static final String INVALID_DATETIME = "MAAAY 2018 8PM";
    private static final String INVALID_APPOINTMENT_TAG = "#checkup";

    private static final String VALID_OWNER_NRIC = ALICE_APP.getOwnerNric().toString();
    private static final String VALID_PET_PATIENT_NAME = ALICE_APP.getPetPatientName().toString();
    private static final String VALID_REMARK = ALICE_APP.getRemark().toString();
    private static final String VALID_DATETIME = ALICE_APP.getFormattedLocalDateTime();
    private static final List<XmlAdaptedTag> VALID_APPOINTMENT_TAGS = ALICE_APP.getAppointmentTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(ALICE_APP);
        assertEquals(ALICE_APP, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidOwnerNric_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(
                        INVALID_OWNER_NRIC,
                        VALID_PET_PATIENT_NAME,
                        VALID_REMARK,
                        VALID_DATETIME,
                        VALID_APPOINTMENT_TAGS);
        String expectedMessage = Nric.MESSAGE_NRIC_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullOwnerNric_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(
                        null,
                        VALID_PET_PATIENT_NAME,
                        VALID_REMARK,
                        VALID_DATETIME,
                        VALID_APPOINTMENT_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidPetName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(
                        VALID_OWNER_NRIC,
                        INVALID_PET_PATIENT_NAME,
                        VALID_REMARK,
                        VALID_DATETIME,
                        VALID_APPOINTMENT_TAGS);
        String expectedMessage = PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullPetName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(
                        VALID_OWNER_NRIC,
                        null,
                        VALID_REMARK,
                        VALID_DATETIME,
                        VALID_APPOINTMENT_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PetPatientName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(
                        VALID_OWNER_NRIC,
                        VALID_PET_PATIENT_NAME,
                        INVALID_REMARK,
                        VALID_DATETIME,
                        VALID_APPOINTMENT_TAGS);
        String expectedMessage = Remark.MESSAGE_REMARK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(
                        VALID_OWNER_NRIC,
                        VALID_PET_PATIENT_NAME,
                        null,
                        VALID_DATETIME,
                        VALID_APPOINTMENT_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_OWNER_NRIC,
                        VALID_PET_PATIENT_NAME,
                        VALID_REMARK,
                        INVALID_DATETIME,
                        VALID_APPOINTMENT_TAGS);
        String expectedMessage = "Please follow the format of yyyy-MM-dd HH:mm";
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(VALID_OWNER_NRIC,
                VALID_PET_PATIENT_NAME,
                VALID_REMARK,
                null,
                VALID_APPOINTMENT_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_APPOINTMENT_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_APPOINTMENT_TAG));
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_OWNER_NRIC,
                        VALID_PET_PATIENT_NAME,
                        VALID_REMARK,
                        VALID_DATETIME,
                        invalidTags);
        Assert.assertThrows(IllegalValueException.class, appointment::toModelType);
    }

}
```
###### \java\seedu\address\testutil\AppointmentBuilder.java
``` java
/**
 * A utility class to help with building Appointment Objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_OWNER_NRIC = "S1012341B";
    public static final String DEFAULT_PET_PATIENT_NAME = "Joker";
    public static final String DEFAULT_REMARK = "Requires home visit";
    public static final String DEFAULT_DATE = "2018-12-31 12:30";
    public static final String DEFAULT_APPOINTMENT_TAG = "surgery";

    private Nric ownerNric;
    private PetPatientName petPatientName;
    private Remark remark;
    private LocalDateTime localDateTime;
    private Set<Tag> appointmentTags;

    public AppointmentBuilder() {
        ownerNric = new Nric(DEFAULT_OWNER_NRIC);
        petPatientName = new PetPatientName(DEFAULT_PET_PATIENT_NAME);
        remark = new Remark(DEFAULT_REMARK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        localDateTime = LocalDateTime.parse(DEFAULT_DATE, formatter);
        appointmentTags = SampleDataUtil.getTagSet(DEFAULT_APPOINTMENT_TAG);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        ownerNric = appointmentToCopy.getOwnerNric();
        petPatientName = appointmentToCopy.getPetPatientName();
        remark = appointmentToCopy.getRemark();
        localDateTime = appointmentToCopy.getDateTime();
        appointmentTags = new HashSet<>(appointmentToCopy.getAppointmentTags());
    }

    /**
     * Sets the {@code Nric} of the person of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withOwnerNric(String ownerNric) {
        this.ownerNric = new Nric(ownerNric);
        return this;
    }

    /**
     * Sets the {@code PetPatientName} of the pet of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPetPatientName(String petPatientName) {
        this.petPatientName = new PetPatientName(petPatientName);
        return this;
    }

    /**
     * Parses the {@code appointmentTags} into a {@code Set<Tag>}
     * and set it to the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withAppointmentTags(String ... appointmentTags) {
        this.appointmentTags = SampleDataUtil.getTagSet(appointmentTags);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDateTime(String stringDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(stringDateTime, formatter);
        this.localDateTime = dateTime;
        return this;
    }

    public Appointment build() {
        return new Appointment(ownerNric, petPatientName, remark, localDateTime, appointmentTags);
    }
}
```
###### \java\seedu\address\testutil\AppointmentUtil.java
``` java
/**
 * A utility class for Appointment.
 */
public class AppointmentUtil {
    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NRIC + appointment.getOwnerNric().toString() + " ");
        sb.append(PREFIX_NAME + appointment.getPetPatientName().toString() + " ");
        sb.append(PREFIX_REMARK + appointment.getRemark().value + " ");
        sb.append(PREFIX_DATE + appointment.getFormattedLocalDateTime() + " ");
        appointment.getAppointmentTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalAppointments.java
``` java
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment ALICE_APP = new AppointmentBuilder()
            .withOwnerNric(TypicalPersons.ALICE.getNric().toString())
            .withPetPatientName(TypicalPetPatients.JEWEL.getName().toString())
            .withRemark("Requires Home Visit")
            .withDateTime("2018-05-28 12:30")
            .withAppointmentTags("checkup").build();
    public static final Appointment BENSON_APP = new AppointmentBuilder()
            .withOwnerNric(TypicalPersons.BENSON.getNric().toString())
            .withPetPatientName(TypicalPetPatients.JOKER.getName().toString())
            .withRemark("May require isolation")
            .withDateTime("2018-04-22 14:30")
            .withAppointmentTags("vaccination").build();

    private TypicalAppointments() {} // prevents instantiation

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(ALICE_APP, BENSON_APP));
    }
}
```
