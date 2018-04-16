package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Expenditure;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Date;
import seedu.address.model.policy.Issue;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.Price;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private Double income;
    @XmlElement(required = true)
    private Double actualSpending;
    @XmlElement(required = true)
    private Double expectedSpending;
    @XmlElement(required = true)
    private Integer age;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private String policyBegDate;
    @XmlElement
    private String policyExpDate;
    @XmlElement
    private Double policyPrice;
    @XmlElement
    private List<String> policyIssues = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address,
                            List<XmlAdaptedTag> tagged, Double income, Double actualSpending,
                            Double expectedSpending, Integer age, String policyBegDate,
                            String policyExpDate, Double policyPrice, List<String> policyIssues) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.income = income;
        this.actualSpending = actualSpending;
        this.expectedSpending = expectedSpending;
        this.age = age;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.policyBegDate = policyBegDate;
        this.policyExpDate = policyExpDate;
        this.policyPrice = policyPrice;
        if (policyIssues != null) {
            this.policyIssues = new ArrayList<>(policyIssues);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        income = source.getIncome().value;
        actualSpending = source.getActualSpending().value;
        expectedSpending = source.getExpectedSpending().value;
        age = source.getAge().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        policyIssues = new ArrayList<>();
        if (source.getPolicy().isPresent()) {
            policyBegDate = source.getPolicy().get().getBeginning().toString();
            policyExpDate = source.getPolicy().get().getExpiration().toString();
            policyPrice = source.getPolicy().get().getPrice().price;
            for (Issue issue : source.getPolicy().get().getCoverage().getIssues()) {
                policyIssues.add(issue.toString());
            }
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValid(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        if (this.income == null) {
            throw new IllegalValueException(Income.MESSAGE_INCOME_CONSTRAINTS);
        }
        if (!Income.isValid(this.income)) {
            throw new IllegalValueException(Income.MESSAGE_INCOME_CONSTRAINTS);
        }
        final Income income = new Income(this.income);

        if (this.actualSpending == null) {
            throw new IllegalValueException(Expenditure.MESSAGE_EXPENDITURE_CONSTRAINTS);
        }
        if (!Expenditure.isValid(this.actualSpending)) {
            throw new IllegalValueException(Expenditure.MESSAGE_EXPENDITURE_CONSTRAINTS);
        }
        final Expenditure actualSpending = new Expenditure(this.actualSpending);

        if (this.expectedSpending == null) {
            throw new IllegalValueException(Expenditure.MESSAGE_EXPENDITURE_CONSTRAINTS);
        }
        if (!Expenditure.isValid(this.expectedSpending)) {
            throw new IllegalValueException(Expenditure.MESSAGE_EXPENDITURE_CONSTRAINTS);
        }
        final Expenditure expectedSpending = new Expenditure(this.expectedSpending);

        if (this.age == null) {
            throw new IllegalValueException(Age.AGE_CONSTRAINTS);
        }
        if (!Age.isValidAge(this.age)) {
            throw new IllegalArgumentException(Age.AGE_CONSTRAINTS);
        }
        final Age age = new Age(this.age);

        Optional<Policy> policy;
        if (this.policyBegDate == null || this.policyExpDate == null || this.policyPrice == null) {
            policy = Optional.empty();
        } else {
            final Date begDate = ParserUtil.parsePolicyDate(policyBegDate);
            final Date expDate = ParserUtil.parsePolicyDate(policyExpDate);

            List<Issue> issues = new ArrayList<>();
            for (String issueString : policyIssues) {
                issues.add(Issue.valueOf(issueString));
            }
            final Coverage coverage = new Coverage(issues);

            if (!Price.isValidPrice(policyPrice)) {
                throw new IllegalArgumentException(Price.PRICE_CONSTRAINTS);
            }
            final Price price = new Price(policyPrice);


            if (!Policy.isValidDuration(begDate, expDate)) {
                throw new IllegalArgumentException(Policy.DURATION_CONSTRAINTS);
            }
            policy = Optional.of(new Policy(price, coverage, begDate, expDate));
        }

        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, tags, income,
                actualSpending, expectedSpending, age, policy);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
