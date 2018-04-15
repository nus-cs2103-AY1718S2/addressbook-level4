# wenhao53
###### /resources/view/LightTheme.css
``` css
#cardPane {
    -fx-border-radius: 6;
    -fx-background-radius: 6;
}

.ACCEPTABLE {
    -fx-background-color: #ddffdd;
    -fx-border-color: derive(#ddffdd, -30%);
    -fx-border-width: 2;
}

.OBESE {
    -fx-background-color: #ffdddd;
    -fx-border-color: derive(#ffdddd, -30%);
    -fx-border-width: 2;
}

.OVERWEIGHT {
    -fx-background-color: #ffe3bf;
    -fx-border-color: derive(#ffe3bf, -30%);
    -fx-border-width: 2;
}

.UNDERWEIGHT {
    -fx-background-color: #ffffdd;
    -fx-border-color: derive(#ffffdd, -30%);
    -fx-border-width: 2;
}
```
###### /resources/view/DarkTheme.css
``` css

#cardPane {
}

.ACCEPTABLE {
    -fx-background-color: #003000;
    -fx-border-color: derive(#003000, 25%);
    -fx-border-width: 2;
}

.OBESE {
    -fx-background-color: #500000;
    -fx-border-color: derive(#500000, 25%);
    -fx-border-width: 2;
}

.OVERWEIGHT {
    -fx-background-color: #553300;
    -fx-border-color: derive(#553300, 25%);
    -fx-border-width: 2;
}

.UNDERWEIGHT {
    -fx-background-color: #553300;
    -fx-border-color: derive(#553300, 25%);
    -fx-border-width: 2;
}

```
###### /java/seedu/address/ui/PersonCard.java
``` java
        bmiClass = person.getBodyMassIndexClassification();
        setBackgroundColourAccordingToClassification(cardPane, bmiClass);
    }

    private void setBackgroundColourAccordingToClassification(HBox cardPane, String bmiClass) {
        ObservableList<String> styleClass = cardPane.getStyleClass();
        styleClass.setAll("cardPane", bmiClass);
    }

```
###### /java/seedu/address/logic/parser/ClassificationCommandParser.java
``` java
/**
 * Parses input classifications and creates a new ClassificationCommand object
 */
public class ClassificationCommandParser implements Parser<ClassificationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClassificationCommand
     * and returns an ClassificationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClassificationCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassificationCommand.MESSAGE_USAGE));
        }

        String[] classificationKeywords = trimmedArgs.split("\\s+");

        checkClassificationKeywordValidity(classificationKeywords);

        return new ClassificationCommand(
                new NameContainsClassificationPredicate(Arrays.asList(classificationKeywords)));
    }

    /**
     * Checks the validity of the keywords input in the context of a Classification Command
     * @throws ParseException if the keyword does not match any of the acceptable keywords
     */
    public void checkClassificationKeywordValidity(String[] classificationKeywords) throws ParseException {
        for (String keyword:classificationKeywords) {
            if (!isValidClassifcationKeyword(keyword)) {
                throw new ParseException(
                        String.format(ClassificationCommand.INVALID_KEYWORD, ClassificationCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the given keyword in the parser to see it if matches any of the allowable classification
     */
    public boolean isValidClassifcationKeyword(String keyword) {
        return keyword.compareToIgnoreCase(ACCEPTABLE_CLASSIFICATION) == 0
                || keyword.compareToIgnoreCase(OBESE_CLASSIFICATION) == 0
                || keyword.compareToIgnoreCase(OVERWEIGHT_CLASSIFICATION) == 0
                || keyword.compareToIgnoreCase(UNDERWEIGHT_CLASSIFICATION) == 0;
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String height} into a {@code Height}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code height} is invalid.
     */
    public static Height parseHeight(String height) throws IllegalValueException {
        requireNonNull(height);
        String trimmedHeight = height.trim();
        if (!Height.isValidHeight(trimmedHeight)) {
            throw new IllegalValueException(Height.MESSAGE_HEIGHT_CONSTRAINTS);
        }
        return new Height(trimmedHeight);
    }

    /**
     * Parses a {@code Optional<String> height} into an {@code Optional<Height>} if {@code height} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Height> parseHeight(Optional<String> height) throws IllegalValueException {
        requireNonNull(height);
        return height.isPresent() ? Optional.of(parseHeight(height.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/commands/ClassificationCommand.java
``` java
/**
 * Finds and lists all persons in the Personal Trainer Pro application whose BodyMassIndexClassification matches
 * any of the given keywords.
 */
public class ClassificationCommand extends Command {

    public static final String COMMAND_WORD = "bmi";

    public static final String INVALID_KEYWORD = "One or more invalid keyword(s) found! \n%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose BMI classification matches "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " overweight\n"
            + "KEYWORD: 'Underweight'(BMI < 18.5), 'Acceptable' (18.5 <= BMI <= 24.9), "
                    + "'Overweight' (25 <= BMI <= 29.9), 'Obese' (BMI > 30)\n";

    private final NameContainsClassificationPredicate predicate;

    public ClassificationCommand(NameContainsClassificationPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassificationCommand // instanceof handles nulls
                && this.predicate.equals(((ClassificationCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/model/person/Height.java
``` java
/**
 * Represents a Person's height (in cm) in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Height {


    public static final String MESSAGE_HEIGHT_CONSTRAINTS =
            "Height(in cm) can only contain numbers and decimals, and should be at between 10.0 to 400.0";
    public static final String HEIGHT_VALIDATION_REGEX = "\\d{2,}(\\.\\d+)?";
    public final String value;

    /**
     * Constructs a {@code Height}.
     *
     * @param height A valid height (in cm).
     */
    public Height(String height) {
        requireNonNull(height);
        checkArgument(isValidHeight(height), MESSAGE_HEIGHT_CONSTRAINTS);
        this.value = height;
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidHeight(String test) {
        return test.matches(HEIGHT_VALIDATION_REGEX) && heightWithinRange(test);
    }

    /**
     * Returns true if a given Height string is within the allowable range of input
     */
    private static boolean heightWithinRange(String test) {
        return Double.parseDouble(test) >= 10.0 && Double.parseDouble(test) <= 400.0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height // instanceof handles nulls
                && this.value.equals(((Height) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/BodyMassIndex.java
``` java
/**
 * Represents a Person's Body Mass Index (BMI) (in kg/m^2) in the Personal Trainer Pro app.
 * Guarantees: mutable; is valid long as Height as declared in {@link #isValidHeight(String)} and Weight as
 * declared in {@Link #isValidWeight(String)} is valid.
 */
public class BodyMassIndex {

    public static final String ACCEPTABLE_CLASSIFICATION = "ACCEPTABLE";
    public static final String OBESE_CLASSIFICATION = "OBESE";
    public static final String OVERWEIGHT_CLASSIFICATION = "OVERWEIGHT";
    public static final String UNDERWEIGHT_CLASSIFICATION = "UNDERWEIGHT";

    public final String value;
    public final String classification;

    private double bodyMassIndexValue;
    private double heightValue;
    private double weightValue;

    /**
     * Constructs a {@code BodyMassIndex}.
     *
     * @param height A valid height (in cm).
     * @param weight A valid weight (in kg).
     */
    public BodyMassIndex(String height, String weight) {
        bodyMassIndexValue = getBodyMassIndexValue(height, weight);
        this.classification = getBodyMassIndexClassificationFromValue(bodyMassIndexValue);
        this.value = convertBodyMassIndexValueToStringForDisplay(bodyMassIndexValue);
    }

    /*
     * Formats the BMI value of a Person into a String, displayed to two decimal places.
     */
    private String convertBodyMassIndexValueToStringForDisplay(Double value) {
        return String.format("%.2f", value);

    }

    /*
     * Returns the BMI Classification of a Person when given the BMI value.
     */
    private String getBodyMassIndexClassificationFromValue(Double bodyMassIndexValue) {
        if (bodyMassIndexValue < 18.5) {
            return UNDERWEIGHT_CLASSIFICATION;
        } else if (bodyMassIndexValue < 25) {
            return ACCEPTABLE_CLASSIFICATION;
        } else if (bodyMassIndexValue < 30) {
            return OVERWEIGHT_CLASSIFICATION;
        } else {
            return OBESE_CLASSIFICATION;
        }
    }

    /*
     * Returns the BMI value of a Person when given valid Height and Weight
     */
    private double getBodyMassIndexValue(String height, String weight) {
        heightValue = Double.parseDouble(height);
        weightValue = Double.parseDouble(weight);
        bodyMassIndexValue = calculateBodyMassIndexValueFromHeightAndWeight(heightValue, weightValue);
        return bodyMassIndexValue;

    }

    /*
     * Returns the BMI value calculated from heightValue and weightValue
     */
    private double calculateBodyMassIndexValueFromHeightAndWeight(double heightValue, double weightValue) {
        return weightValue / ((heightValue / 100) * (heightValue / 100));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/Name.java
``` java
    /**
     * Returns true if two Names in comparison are alphabetically the same (regardless of case).
     */
    public boolean alphabeticallyEquals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.toLowerCase().equals(((Name) other).fullName.toLowerCase())); // state check
    }

```
###### /java/seedu/address/model/person/Weight.java
``` java
    /**
     * Returns true if a given Weight string is within the allowable range of input
     */
    private static boolean weightWithinRange(String test) {
        return Double.parseDouble(test) >= 10.0 && Double.parseDouble(test) <= 400.0;
    }

```
###### /java/seedu/address/model/person/NameContainsClassificationPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code BodyMassIndexClassification} matches the given search query.
 */
public class NameContainsClassificationPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsClassificationPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(person.getBodyMassIndexClassification(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsClassificationPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsClassificationPredicate) other).keywords)); // state check
    }

}
```
