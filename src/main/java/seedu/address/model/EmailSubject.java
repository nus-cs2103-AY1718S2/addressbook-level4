package seedu.address.model;

//@@author KevinCJH
/**
 * Creates an EmailSubject object for the Draft Email UI.
 */
public class EmailSubject {

    private static EmailSubject instance = null;
    private static String subject;

    private EmailSubject() {
    }

    public static EmailSubject getInstance() {
        if (instance == null) {
            instance = new EmailSubject();
        }
        return instance;
    }

    public static String getSubject() {
        return subject;
    }

    public static void setSubject(String subject) {
        EmailSubject.subject = subject;
    }
}

