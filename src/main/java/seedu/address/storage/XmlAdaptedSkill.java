package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.skill.Skill;

/**
 * JAXB-friendly adapted version of the Skill.
 */
public class XmlAdaptedSkill {

    @XmlValue
    private String skillName;

    /**
     * Constructs an XmlAdaptedSkill.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSkill() {}

    /**
     * Constructs a {@code XmlAdaptedSkill} with the given {@code skillName}.
     */
    public XmlAdaptedSkill(String tagName) {
        this.skillName = tagName;
    }

    /**
     * Converts a given Skill into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSkill(Skill source) {
        skillName = source.skillName;
    }

    /**
     * Converts this jaxb-friendly adapted skill object into the model's Skill object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Skill toModelType() throws IllegalValueException {
        if (!Skill.isValidSkillName(skillName)) {
            throw new IllegalValueException(Skill.MESSAGE_SKILL_CONSTRAINTS);
        }
        return new Skill(skillName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSkill)) {
            return false;
        }

        return skillName.equals(((XmlAdaptedSkill) other).skillName);
    }
}
