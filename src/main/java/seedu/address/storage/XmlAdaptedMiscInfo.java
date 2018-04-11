package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.MiscellaneousInfo;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.Remarks;
//@@author samuelloh
/**
 * JAXB-friendly version of the Miscellaneous info of a student.
 */
public class XmlAdaptedMiscInfo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    @XmlElement(required = true)
    private String allergies;
    @XmlElement(required = true)
    private String nextOfKinName;
    @XmlElement(required = true)
    private String nextOfKinPhone;
    @XmlElement(required = true)
    private String remarks;

    /**
     * Constructs a {@code XmlAdaptedMiscInfo}.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMiscInfo() {}

    /**
     * Constructs a {@code XmlAdaptedMiscInfo} with the given student details.
     */
    public XmlAdaptedMiscInfo(String allergies, String nextOfKinName, String nextOfKinPhone, String remarks) {
        this.allergies = allergies;
        this.nextOfKinName = nextOfKinName;
        this.nextOfKinPhone = nextOfKinPhone;
        this.remarks = remarks;
    }

    /**
     * Converts a given Milestone into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMilestone
     */
    public XmlAdaptedMiscInfo(MiscellaneousInfo source) {
        this.allergies = source.getAllergies().toString();
        this.nextOfKinName = source.getNextOfKinName().toString();
        this.nextOfKinPhone = source.getNextOfKinPhone().toString();
        this.remarks = source.getRemarks().toString();
    }

    /**
     * Converts this jaxb-friendly adapted milestone object into the model's Milestone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted milestone
     */
    public MiscellaneousInfo toModelType() throws IllegalValueException {

        if (this.allergies == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Allergies.class.getSimpleName()));
        }
        final Allergies modelAllergies = new Allergies(this.allergies);

        if (this.nextOfKinName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NextOfKinName.class.getSimpleName()));
        }
        final NextOfKinName modelNextOfKinName = new NextOfKinName(this.nextOfKinName);

        if (this.nextOfKinPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NextOfKinPhone.class.getSimpleName()));
        }
        final NextOfKinPhone modelNextOfKinPhone = new NextOfKinPhone(this.nextOfKinPhone);

        if (this.remarks == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Remarks.class.getSimpleName()));
        }
        final Remarks modelRemarks = new Remarks(this.remarks);

        return new MiscellaneousInfo(modelAllergies, modelNextOfKinName, modelNextOfKinPhone, modelRemarks);
    }
}
//author
