package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Objects;
/**
 * Represents all predicates to form {@code Predicate<Person>}
 */
public class AllPredicate {

    private NamePredicate namePredicate;
    private PhonePredicate phonePredicate;
    private EmailPredicate emailPredicate;
    private AddressPredicate addressPredicate;
    private UniversityPredicate universityPredicate;
    private MajorPredicate majorPredicate;
    private JobAppliedPredicate jobAppliedPredicate;
    private CommentPredicate commentPredicate;

    /**
     * Default constructor for no arguments
     */
    public AllPredicate() {
        this.namePredicate = new NamePredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.phonePredicate = new PhonePredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.emailPredicate = new EmailPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.addressPredicate = new AddressPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.universityPredicate = new UniversityPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.majorPredicate = new MajorPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.jobAppliedPredicate = new JobAppliedPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        this.commentPredicate = new CommentPredicate(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
    }
    /**
     * Constructor to initialize all predicates
     */
    public AllPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                        ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {
        this.namePredicate = new NamePredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.phonePredicate = new PhonePredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.emailPredicate = new EmailPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.addressPredicate = new AddressPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.universityPredicate = new UniversityPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.majorPredicate = new MajorPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.jobAppliedPredicate = new JobAppliedPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
        this.commentPredicate = new CommentPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public NamePredicate getNamePredicate() {
        return namePredicate;
    }

    public void setNamePredicate(ArrayList<String> exactKeywords,
                                 ArrayList<String> substringKeywords,
                                 ArrayList<String> prefixKeywords,
                                 ArrayList<String> suffixKeywords) {
        this.namePredicate = new NamePredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public PhonePredicate getPhonePredicate() {
        return phonePredicate;
    }

    public void setPhonePredicate(ArrayList<String> exactKeywords,
                                  ArrayList<String> substringKeywords,
                                  ArrayList<String> prefixKeywords,
                                  ArrayList<String> suffixKeywords) {
        this.phonePredicate = new PhonePredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public EmailPredicate getEmailPredicate() {
        return emailPredicate;
    }

    public void setEmailPredicate(ArrayList<String> exactKeywords,
                                  ArrayList<String> substringKeywords,
                                  ArrayList<String> prefixKeywords,
                                  ArrayList<String> suffixKeywords) {
        this.emailPredicate = new EmailPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public AddressPredicate getAddressPredicate() {
        return addressPredicate;
    }

    public void setAddressPredicate(ArrayList<String> exactKeywords,
                                    ArrayList<String> substringKeywords,
                                    ArrayList<String> prefixKeywords,
                                    ArrayList<String> suffixKeywords) {
        this.addressPredicate = new AddressPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public UniversityPredicate getUniversityPredicate() {
        return universityPredicate;
    }

    public void setUniversityPredicate(ArrayList<String> exactKeywords,
                                    ArrayList<String> substringKeywords,
                                    ArrayList<String> prefixKeywords,
                                    ArrayList<String> suffixKeywords) {
        this.universityPredicate = new UniversityPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public MajorPredicate getMajorPredicate() {
        return majorPredicate;
    }

    public void setMajorPredicate(ArrayList<String> exactKeywords,
                                  ArrayList<String> substringKeywords,
                                  ArrayList<String> prefixKeywords,
                                  ArrayList<String> suffixKeywords) {
        this.majorPredicate = new MajorPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public JobAppliedPredicate getJobAppliedPredicate() {
        return jobAppliedPredicate;
    }

    public void setJobAppliedPredicate(ArrayList<String> exactKeywords,
                                       ArrayList<String> substringKeywords,
                                       ArrayList<String> prefixKeywords,
                                       ArrayList<String> suffixKeywords) {
        this.jobAppliedPredicate = new JobAppliedPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    public CommentPredicate getCommentPredicate() {
        return commentPredicate;
    }

    public void setCommentPredicate(ArrayList<String> exactKeywords,
                                       ArrayList<String> substringKeywords,
                                       ArrayList<String> prefixKeywords,
                                       ArrayList<String> suffixKeywords) {
        this.commentPredicate = new CommentPredicate(exactKeywords, substringKeywords,
                prefixKeywords, suffixKeywords);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AllPredicate)) {
            return false;
        }

        AllPredicate otherPerson = (AllPredicate) other;
        return otherPerson.getNamePredicate().equals(this.getNamePredicate())
                && otherPerson.getPhonePredicate().equals(this.getPhonePredicate())
                && otherPerson.getEmailPredicate().equals(this.getEmailPredicate())
                && otherPerson.getAddressPredicate().equals(this.getAddressPredicate())
                && otherPerson.getUniversityPredicate().equals(this.getUniversityPredicate())
                && otherPerson.getMajorPredicate().equals(this.getMajorPredicate())
                && otherPerson.getJobAppliedPredicate().equals(this.getJobAppliedPredicate())
                && otherPerson.getCommentPredicate().equals(this.getCommentPredicate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(namePredicate, phonePredicate, emailPredicate,
                addressPredicate, universityPredicate, majorPredicate,
                jobAppliedPredicate, commentPredicate);
    }
}
