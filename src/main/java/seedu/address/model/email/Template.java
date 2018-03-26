package seedu.address.model.email;

public class Template {
    private final String purpose;
    private final String title;
    private final String message;

    public Template(){
        this.purpose = "coldEmail"; // in camelCase
        this.title = "Meet up over Coffee";
        this.message = "Hey, I am from Addsurance and would like you ask if you are interested in planning your" +
                "finances with us. Would you care to meet over coffee in the next week or so?";
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
