# Sebry9
###### /resources/view/PersonListCard.fxml
``` fxml
         <Label prefHeight="134.0" prefWidth="131.0" text="Client Image">
            <graphic>
               <ImageView fitHeight="150.0" fitWidth="119.0" pickOnBounds="true" preserveRatio="true">
                  <image>
                     <Image url="@../images/test.jpg" />
                  </image>
               </ImageView>
            </graphic></Label>
         <Label text="Total Commission:" />
         <Label fx:id="commission" text="\$commission">
            <VBox.margin>
               <Insets />
            </VBox.margin>
            <padding>
               <Insets bottom="10.0" left="30.0" right="10.0" top="10.0" />
            </padding>
         </Label>
      </children>
   </VBox>
  <GridPane prefWidth="201.0" HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
      </padding>
      <HBox alignment="CENTER_LEFT" spacing="5">
        <Label fx:id="id" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
      </HBox>
      <FlowPane fx:id="tags" />
         <FlowPane fx:id="insurances" />
        <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
      <Label fx:id="address" styleClass="cell_small_label" text="\$address" />
      <Label fx:id="email" styleClass="cell_small_label" text="\$email" />
      <Label fx:id="birthday" styleClass="cell_small_label" text="\$birthday" />
      <Label fx:id="appointment" styleClass="cell_small_label" text="\$appointment" />
        <Label fx:id="group" styleClass="cell_small_label" text="\$group" />
    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
   <Group />
</HBox>
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    private String getTagColorStyleFor(String tag) {

        switch(tag) {
        case "friends":
        case "friend":
            return TAG_COLOR_STYLES[0]; //yellow

        case "teacher":
        case "classmates":
            return TAG_COLOR_STYLES[1]; //blue

        case "family":
        case "husband":
            return TAG_COLOR_STYLES[3]; //green

        case "enemy":
        case "owesMoney":
            return TAG_COLOR_STYLES[2]; //red

        case "boyfriend":
        case "girlfriend":
            return TAG_COLOR_STYLES[5]; //purple

        case "grandparent":
        case "neighbours":
            return TAG_COLOR_STYLES[6]; //grey

        case "colleagues":
            return TAG_COLOR_STYLES[4]; //orange

        default:
            return "";
        }
    }


    /**
     * Creates the tag labels for {@code person}.
     */
    private void startTag(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedInsurance.java
``` java
/**
 * JAXB-friendly version of the Insurance.
 */
public class XmlAdaptedInsurance {

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
```
###### /java/seedu/address/model/person/Person.java
``` java
/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Birthday birthday;
    private final Appointment appointment;
    private final Group group;
    private final String totalCommission;

    private final UniqueInsuranceList insurance;
    private final UniqueTagList tags;
    private final UniqueGroupList groups;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Birthday birthday,
            Appointment appointment, Group group, Set<Insurance> insurance) {
        requireAllNonNull(name, phone, email, address, tags, birthday);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.appointment = appointment;
        this.group = group;
        this.insurance = new UniqueInsuranceList(insurance);
        this.totalCommission = calculateTotalCommission(insurance);
        this.tags = new UniqueTagList(tags);
        this.groups = new UniqueGroupList(group);

    }


    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Group getGroup() {
        return group;
    }

    public String getTotalCommission() {
        return totalCommission;
    }

```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Calculate the total commission based on the number of plan this person/client have.
     */
    public String calculateTotalCommission(Set<Insurance> insurances) {
        int commission = 0;
        Insurance[] insuranceList = insurances.toArray(new Insurance[insurances.size()]);

        for (int i = 0; i < insurances.size(); i++) {
            Commission plans = new Commission(insuranceList[i]);
            commission += parseInt(plans.getCommission());
        }
        return Integer.toString(commission);
    }

    public Set<Insurance> getInsurance() {
        return Collections.unmodifiableSet(insurance.toSet());
    }

    /* public String getCommission() {
        return commission;
    }
    */

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getBirthday().equals(this.getBirthday())
                && otherPerson.getGroup().equals(this.getGroup());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, birthday, appointment, group, insurance/*, commission*/);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
```
###### /java/seedu/address/model/Insurance/Commission.java
``` java
/**
 * Represents the commission recieved from a insurance plan in reInsurance.
 * Guarantees: immutable;
 */
public class Commission {

    private final String commission;

    /**
     * Every field must be present and not null.
     */
    public Commission(Insurance insurance) {
        requireNonNull(insurance);
        Insurance insurance1 = insurance;
        String insuranceName = insurance.toString();
        String commission = new String("0");
        Pattern p1 = Pattern.compile("\\{(.*?)\\}");
        Matcher m1 = p1.matcher(insuranceName);
        Pattern p2 = Pattern.compile("\\[(.*?)\\]");
        Matcher m2 = p2.matcher(insuranceName);

        while (m1.find()) {
            if (commission.equals("0")) {
                commission = m1.group().substring(1, m1.group().length() - 1);
            }
        }
        while (m2.find()) {
            if (commission.equals("0")) {
                commission = m2.group().substring(1, m2.group().length() - 1);
            }
        }

        this.commission = commission;

    }

    public String getCommission() {
        return commission;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Commission)) {
            return false;
        }

        Commission otherCommission = (Commission) other;
        return otherCommission.getCommission().equals(this.getCommission());
    }

    @Override
    public int hashCode() {
        return Objects.hash(commission);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCommission());
        return builder.toString();
    }
}
```
###### /java/seedu/address/model/Insurance/UniqueInsuranceList.java
``` java
/**
 * A list of insurance that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the reInsurance's features.
 *
 * @see Insurance#equals(Object)
 */
public class UniqueInsuranceList implements Iterable<Insurance> {
    private final ObservableList<Insurance> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty InsuranceList.
     */
    public UniqueInsuranceList() {}

    /**
     * Creates a UniqueInsuranceList using given insurances.
     * Enforces no nulls.
     */
    public UniqueInsuranceList(Set<Insurance> insurance) {
        requireAllNonNull(insurance);
        internalList.addAll(insurance);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all insurance in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Insurance> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Insurance in this list with those in the argument insurance list.
     */
    public void setInsurances(Set<Insurance> insurance) {
        requireAllNonNull(insurance);
        internalList.setAll(insurance);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every insurance in the argument list exists in this object.
     */
    public void mergeFrom(UniqueInsuranceList from) {
        final Set<Insurance> alreadyInside = this.toSet();
        from.internalList.stream()
            .filter(insurance -> !alreadyInside.contains(insurance))
            .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Insurance as the given argument.
     */
    public boolean contains(Insurance toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Insurance to the list.
     *
     * @throws DuplicateInsuranceException if the Insuranceto add is a duplicate of an existing Tag in the list.
     */
    public void add(Insurance toAdd) throws DuplicateInsuranceException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInsuranceException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Insurance> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Insurance> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
            || (other instanceof UniqueInsuranceList // instanceof handles nulls
            && this.internalList.equals(((UniqueInsuranceList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueInsuranceList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateInsuranceException extends DuplicateDataException {
        protected DuplicateInsuranceException() {
            super("Operation would result in duplicate insurances");
        }
    }

}















```
###### /java/seedu/address/model/Insurance/Insurance.java
``` java
/**
 * Represents a Insurance plan in reInsurance.
 * Guarantees: immutable;
 */
public class Insurance {

    public static final String MESSAGE_INSURANCE_CONSTRAINTS =
        "Insurance should only contain alphanumeric characters";

    private static final String SPECIAL_CHARACTERS = "\\[\\]{|}";
    private static final String INSURANCE_NAME = "[\\p{Alnum} ]*";
    private static final String COMMISSION_FORMAT = "^[\\w" + SPECIAL_CHARACTERS + "]+";

    /*
     * The first character of the address must not be a whitespace,
     */
    public static final String INSURANCE_VALIDATION_REGEX =  INSURANCE_NAME + COMMISSION_FORMAT;


    public final String insuranceName;

    /**
     * Constructs a {@code Name}.
     *
     * @param insurance A valid insurance.
     */
    public Insurance(String insurance) {
        checkArgument(isValidInsurance(insurance), MESSAGE_INSURANCE_CONSTRAINTS);
        this.insuranceName = insurance;
    }

    /**
     * Returns true if a given string is a valid insurance.
     */
    public static boolean isValidInsurance(String test) {
        if (test == null) {
            return true;
        }
        return test.matches(INSURANCE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return insuranceName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Insurance // instanceof handles nulls
            && this.insuranceName.equals(((Insurance) other).insuranceName)); // state check
    }

    @Override
    public int hashCode() {
        return insuranceName.hashCode();
    }

}
```
