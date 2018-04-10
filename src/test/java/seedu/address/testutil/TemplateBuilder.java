package seedu.address.testutil;

import seedu.address.model.email.Template;

//@@author ng95junwei

/**
 * Utility class to help with building default templates for testing
 */
public class TemplateBuilder {
    public static final String DEFAULT_PURPOSE = "Purpose";
    public static final String DEFAULT_SUBJECT = "Default Subject";
    public static final String DEFAULT_MESSAGE = "Default Message";

    private String purpose;
    private String subject;
    private String message;

    /**
     * Constructor with default purpose, subject message
     */
    public TemplateBuilder() {
        this.purpose = DEFAULT_PURPOSE;
        this.subject = DEFAULT_SUBJECT;
        this.message = DEFAULT_MESSAGE;
    }

    /**
     * mutates the purpose of the builder
     * @param purpose
     * @return original object to be chained
     */
    public TemplateBuilder withPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    /**
     * mutates the subject of the builder
     * @param subject
     * @return original object to be chained
     */
    public TemplateBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * mutates the message of the builder
     * @param message
     * @return original object to be chained
     */
    public TemplateBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * builds template from builder
     * @return template with attributes of builder
     */
    public Template build() {
        return new Template(purpose, subject, message);
    }
}
