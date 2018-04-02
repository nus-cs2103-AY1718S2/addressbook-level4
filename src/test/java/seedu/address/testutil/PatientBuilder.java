package seedu.address.testutil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.RecordList;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_NRIC = "S1234567A";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DOB = "11/11/1991";
    public static final String DEFAULT_BLOODTYPE = "A";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_RECORDLIST =
            new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " s/ i/ t/\n";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_APPOINTMENTS = "";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Address address;
    private DateOfBirth dob;
    private BloodType bloodType;
    private Remark remark;
    private RecordList recordList;
    private Set<Tag> tags;
    private Set<Appointment> appointments;

    public PatientBuilder() throws ParseException {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        dob = new DateOfBirth(DEFAULT_DOB);
        bloodType = new BloodType(DEFAULT_BLOODTYPE);
        remark = new Remark(DEFAULT_REMARK);
        recordList = new RecordList(DEFAULT_RECORDLIST);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        appointments = SampleDataUtil.getAppointmentSet(DEFAULT_APPOINTMENTS);
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        nric = patientToCopy.getNric();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        dob = patientToCopy.getDob();
        bloodType = patientToCopy.getBloodType();
        remark = patientToCopy.getRemark();
        recordList = patientToCopy.getRecordList();
        tags = new HashSet<>(patientToCopy.getTags());
        appointments = new HashSet<>(patientToCopy.getAppointments());
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code appointments} into a {@code Set<Appointment>}
     * and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withAppointments(String ... appointments) {
        this.appointments = SampleDataUtil.getAppointmentSet(appointments);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code DOB} of the {@code Patient} that we are building.
     */
    public PatientBuilder withDob(String dob) {
        this.dob = new DateOfBirth(dob);
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Patient} that we are building.
     */
    public PatientBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Record} of the {@code Patient} that we are building.
     */
    public PatientBuilder withRecordList(String commandString) throws ParseException {
        this.recordList = new RecordList(commandString);
        return this;
    }

    public Patient build() {
        return new Patient(name, nric, phone, email, address, dob, bloodType, remark, recordList, tags, appointments);
    }

}
