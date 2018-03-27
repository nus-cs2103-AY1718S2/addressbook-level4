package seedu.address.model.email;

public class Template {
    private final String purpose;
    private final String title;
    private final String message;

    public Template(String purpose, String title, String message){
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
}
