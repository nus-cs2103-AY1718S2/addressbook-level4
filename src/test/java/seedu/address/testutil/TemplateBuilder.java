package seedu.address.testutil;

import seedu.address.model.email.Template;

//@@author ng95junwei
public class TemplateBuilder {
    public static final String DEFAULT_PURPOSE = "Default Purpose";
    public static final String DEFAULT_SUBJECT = "Default Subject";
    public static final String DEFAULT_MESSAGE = "Default Message";

    private String purpose;
    private String subject;
    private String message;

    public TemplateBuilder(){
        this.purpose = DEFAULT_PURPOSE;
        this.subject = DEFAULT_SUBJECT;
        this.message = DEFAULT_MESSAGE;
    }

    public TemplateBuilder withPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public TemplateBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public TemplateBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public Template build() {
        return new Template(purpose, subject, message);
    }
}
