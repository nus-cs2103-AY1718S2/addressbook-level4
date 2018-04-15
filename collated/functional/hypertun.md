# hypertun
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    public static final String CALCULATOR_PREFIX_URL = "http://www.calculator.net/calorie-calculator.html?ctype=metric";

    public static final String CALCULATOR_AGE_PREFIX = "&cage=";

    public static final String CALCULATOR_GENDER_PREFIX = "&csex=";

    public static final String CALCULATOR_HEIGHT_PREFIX = "&cheightfeet=5&cheightinch=10&cpound=160&cheightmeter=";

    public static final String CALCULATOR_WEIGHT_PREFIX = "&ckg=";

    public static final String CALCULATOR_ACTIVITY_LEVEL_PREFIX = "&cactivity=";

    public static final String CALCULATOR_SUFFIX_URL = "&printit=0";

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    private void loadPersonPage(Person person) {
        browser.getEngine().loadContent(HtmlFormatter.getHtmlFormat(person));
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /**
     * Creates calories from given person
     */
    public void loadPersonCalories(Person person) {
        loadPage(CALCULATOR_PREFIX_URL
                + CALCULATOR_AGE_PREFIX + person.getAge().value
                + CALCULATOR_GENDER_PREFIX + person.getGender().value
                + CALCULATOR_HEIGHT_PREFIX + person.getHeight().value
                + CALCULATOR_WEIGHT_PREFIX + person.getWeight().value
                + CALCULATOR_ACTIVITY_LEVEL_PREFIX + person.getActivityLevel().value
                + CALCULATOR_SUFFIX_URL);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    public void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handleShowCaloriesEvent(ShowCaloriesEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Processing Calories of " + event.person.getName().fullName));
        loadPersonCalories(event.person);
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleShowCaloriesEvent(ShowCaloriesEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleBrowser();
    }
```
###### /java/seedu/address/commons/events/ui/ShowCaloriesEvent.java
``` java
/**
 * Event raised on 'goal' command's successful execution
 */
public class ShowCaloriesEvent extends BaseEvent {

    public final Person person;

    public ShowCaloriesEvent(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/model/util/HtmlFormatter.java
``` java
/**
 * Change PersonCard to html format
 */
public class HtmlFormatter {

    public static String getHtmlFormat(Person person) {

        String name = person.getName().toString();
        String phone = person.getPhone().toString();
        String email = person.getEmail().toString();
        String address = person.getAddress().toString();
        String height = person.getHeight().toString();
        String weight = person.getWeight().toString();
        String bmi = person.getBodyMassIndex().toString();
        String age = person.getAge().toString();
        String activityLevel = person.getActivityLevel().toString();

        String gender;

        if (person.getGender().toString().equals("m")) {
            gender = "Male";
        }
        else {
            gender = "Female";
        }

        URL personcardcss = MainApp.class.getResource(FXML_FILE_FOLDER + "PersonCard.css");

        String bodyhtmlcode = "<div class=\"container\">\n"
                + "<h1>Personal Trainer Pro</h1>\n"
                + "<h2>v1.5</h2>\n"
                + "<div class=\"service-details\">"
                + "<img src=\"https://cdn.pixabay.com/photo/2014/03/24/13/40/dumbbells-293955_960_720.png\""
                + " alt=\"realm\" />\n"
                + "<div class=\"service-hover-text\">\n"
                + "<h3>"
                + gender
                + "</h3>\n"
                + "<h4>BMI :"
                + bmi
                + "</h4>\n"
                + "<p>Phone Number :</p>\n"
                + phone
                + "<p>Address :</p>\n"
                + address
                + "<p>Height :</p>\n"
                + height
                + "<p>Weight :</p>\n"
                + weight
                + "<p>Age :</p>\n"
                + age
                + "<p>Activity Level :</p>\n"
                + activityLevel
                + "</div>\n"
                + "<div class=\"service-white service-text\">\n"
                + "<p>"
                + name
                + "</p>\n"
                + "<a href=\"#\">@"
                + email
                + "</a></div>\n"
                + "</div>\n"
                + "</div>";

        String htmlcode = "<html>"
                + "<head>"
                + "<link rel = 'stylesheet' type='text/css' href='" + personcardcss.toExternalForm() + "' />"
                + "</head>"
                + "<body>"
                + bodyhtmlcode
                + "</body>"
                + "</html>";

        return htmlcode;
    }
}
```
###### /java/seedu/address/model/person/ActivityLevel.java
``` java
/**
 * Represents a Person's activity in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidActivityLevel(String)}
 */
public class ActivityLevel {

    public static final String MESSAGE_ACTIVITYLEVEL_CONSTRAINTS =
            "Activity Level can only be Basal Metabolic Rate(1.0), Sedentary(1.2) - little to no exercise per week,"
                    + " Lightly Active(1.375) - exercise 1-3 times per week,"
                    + " Moderately Active(1.55) - exercise 3-5 times per week,"
                    + " Very Active(1.725) -  exercise 6-7 times per week,"
                    + " Extra Active(1.9) - very hard exercise or physical job";
    public final String value;

    /**
     * Constructs a {@code ActivityLevel}.
     *
     * @param activityLevel A valid ActivityLevel.
     */
    public ActivityLevel(String activityLevel) {
        requireNonNull(activityLevel);
        checkArgument(isValidActivityLevel(activityLevel), MESSAGE_ACTIVITYLEVEL_CONSTRAINTS);
        this.value = activityLevel;
    }

    /**
     * Returns true if a given string is a valid activityLevel.
     */
    public static boolean isValidActivityLevel(String test) {
        if (test.equals("1.0") || test.equals("1.2") || test.equals("1.375") || test.equals("1.55")
                || test.equals("1.725") || test.equals("1.9")) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityLevel // instanceof handles nulls
                && this.value.equals(((ActivityLevel) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

```
###### /java/seedu/address/model/person/Age.java
``` java
/**
 * Represents a Person's age in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age {

    public static final String MESSAGE_AGE_CONSTRAINTS =
            "Age can only be up to three numbers long";
    public static final String AGE_VALIDATION_REGEX = "\\d{1,3}+";
    public final String value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_AGE_CONSTRAINTS);
        this.value = age;
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        return test.matches(AGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age // instanceof handles nulls
                && this.value.equals(((Age) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/Gender.java
``` java
/**
 * Represents a Person's Gender (whether male or female) in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Gender can only be the alphabets M or F or m or f";
    public static final String GENDER_VALIDATION_REGEX = "[mfMF]{1}+";
    public final String value;

    /**
     * Constructs a {@code Gender}.
     *
     * @param sex A valid gender.
     */
    public Gender(String sex) {
        requireNonNull(sex);
        checkArgument(isValidGender(sex), MESSAGE_GENDER_CONSTRAINTS);
        this.value = sex.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid Gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(GENDER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
