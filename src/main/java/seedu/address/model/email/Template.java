package seedu.address.model.email;

/**
 * A Class to hold the required information for an email.
 */

//@@ author ng95junwei
public class Template {
    private final String purpose;
    private final String title;
    private final String message;

    public Template(String purpose, String title, String message) {
        this.purpose = purpose;
        this.title = title;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Purpose: ")
                .append(getPurpose())
                .append(",")
                .append(" Subject: ")
                .append(getTitle())
                .append(",")
                .append(" Message: ")
                .append(getMessage());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Template)) {
            return false;
        }

        Template otherTemplate = (Template) other;
        return otherTemplate.getTitle().equals(this.getTitle())
                && otherTemplate.getPurpose().equals(this.getPurpose())
                && otherTemplate.getMessage().equals(this.getMessage());
    }
}
//@@author
