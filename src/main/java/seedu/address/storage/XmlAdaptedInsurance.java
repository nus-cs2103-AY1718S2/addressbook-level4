package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Insurance.Insurance;

import javax.xml.bind.annotation.XmlValue;

public class XmlAdaptedInsurance {

//@@author Sebry9
/**
 * JAXB-friendly adapted version of the Insurance.
 */
    @XmlValue
    private String insuranceName;

    /**
     * Constructs an XmlAdaptedInsurance.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedInsurance() {}

    /**
     * Constructs a {@code XmlAdaptedInsurance} with the given {@code insuranceName}.
     */
    public XmlAdaptedInsurance(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    /**
     * Converts a given Insurance into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedInsurance(Insurance source) {
        insuranceName = source.insuranceName;
    }

    /**
     * Converts this jaxb-friendly adapted insurance object into the model's Insurance object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Insurance toModelType() throws IllegalValueException {
        if (!Insurance.isValidInsurance(insuranceName)) {
            throw new IllegalValueException(Insurance.MESSAGE_INSURANCE_CONSTRAINTS);
        }
        return new Insurance(insuranceName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedInsurance)) {
            return false;
        }

        return insuranceName.equals(((XmlAdaptedInsurance) other).insuranceName);
    }
}
