package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.ExpectedGraduationYearInKeywordsRangePredicate;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.GradePointAverageInKeywordsRangePredicate;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.InterviewDateInKeywordsRangePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.model.person.RatingInKeywordsRangePredicate;
import seedu.address.model.util.InterviewDateUtil;
//@@author mhq199657
/**
 * A utility class for parsing FilterCommand
 */
public class FilterUtil {
    /**
     * Parses a Optional of  predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseExpectedGraduationYear(Optional<String> predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        if (predicateString.isPresent()) {
            return parseExpectedGraduationYear(predicateString.get());
        } else {
            return null;
        }
    }

    /**
     * Parses a predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseExpectedGraduationYear(String predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        String[] predicateStrings = predicateString.split(",");
        Arrays.stream(predicateStrings).map(String::trim).toArray(unused -> predicateStrings);
        if (predicateStrings.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        Predicate<Person> predicate = processExpectedGraduationYearPredicateStrings(predicateStrings);
        return predicate;
    }

    /**
     * Parses the string array of all single predicate strings to a predicate
     * @param predicateStrings array of predicateString
     * @return the predicate user demanded
     * @throws IllegalValueException
     */
    private static Predicate<Person> processExpectedGraduationYearPredicateStrings(String[] predicateStrings)
            throws IllegalValueException {
        List<Predicate<Person>> allPredicates = new ArrayList<Predicate<Person>>();
        for (String s: predicateStrings) {
            Predicate<Person> predicate = formExpectedGraduationYearPredicateFromPredicateString(s);
            allPredicates.add(predicate);
        }
        return combineAllPredicates(allPredicates);
    }

    /**
     * Combines a list of predicate into a single predicate
     * @param predicateList list of predicates
     * @return a single equivalent predicate
     */
    private static Predicate<Person> combineAllPredicates(List<Predicate<Person>> predicateList) {
        assert(predicateList.size() >= 1);
        Predicate<Person> allPredicates = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            allPredicates = allPredicates.or(predicateList.get(i));
        }
        return allPredicates;
    }

    /**
     * Form a single predicate from a single predicate string
     * @param s a single predicate string
     * @return a single predicate
     * @throws IllegalValueException
     */
    private static Predicate<Person> formExpectedGraduationYearPredicateFromPredicateString(String s)
            throws IllegalValueException {
        FilterRange<ExpectedGraduationYear> filterRange;
        if (s.contains("-")) { //It is a range
            String[] range = s.split("-");
            if (range.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            } else if (ExpectedGraduationYear.isValidExpectedGraduationYear(range[0].trim())
                    && ExpectedGraduationYear.isValidExpectedGraduationYear(range[1].trim())) {
                filterRange = new FilterRange<ExpectedGraduationYear>(
                        new ExpectedGraduationYear(range[0].trim()), new ExpectedGraduationYear(range[1].trim()));
            } else {
                throw new IllegalValueException(ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
            }

        } else { //It is a value instead
            if (ExpectedGraduationYear.isValidExpectedGraduationYear(s.trim())) {
                filterRange = new FilterRange<ExpectedGraduationYear>(new ExpectedGraduationYear(s));
            } else {
                throw new IllegalValueException(ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
            }
        }
        Predicate<Person> predicate = new ExpectedGraduationYearInKeywordsRangePredicate(filterRange);
        return predicate;
    }

    /**
     * Parses a Optional of  predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseRating(Optional<String> predicateString) throws IllegalValueException {
        requireNonNull(predicateString);
        if (predicateString.isPresent()) {
            return parseRating(predicateString.get());
        } else {
            return null;
        }
    }

    /**
     * Parses a predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseRating(String predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        String[] predicateStrings = predicateString.split(",");
        Arrays.stream(predicateStrings).map(String::trim).toArray(unused -> predicateStrings);
        if (predicateStrings.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        Predicate<Person> predicate = processRatingPredicateStrings(predicateStrings);
        return predicate;
    }

    /**
     * Parses the string array of all single predicate strings to a predicate
     * @param predicateStrings array of predicateString
     * @return the predicate user demanded
     * @throws IllegalValueException
     */
    private static Predicate<Person> processRatingPredicateStrings(String[] predicateStrings)
            throws IllegalValueException {
        List<Predicate<Person>> allPredicates = new ArrayList<Predicate<Person>>();
        for (String s: predicateStrings) {
            Predicate<Person> predicate = formRatingPredicateFromPredicateString(s);
            allPredicates.add(predicate);
        }
        return combineAllPredicates(allPredicates);
    }

    /**
     * Form a single predicate from a single predicate string
     * @param s a single predicate string
     * @return a single predicate
     * @throws IllegalValueException
     */
    private static Predicate<Person> formRatingPredicateFromPredicateString(String s)
            throws IllegalValueException {
        FilterRange<Rating> filterRange;
        if (s.contains("-")) { //It is a range
            String[] range = s.split("-");
            if (range.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            } else {
                double lowerRating = Rating.DEFAULT_SCORE;
                double higherRating = Rating.DEFAULT_SCORE;
                try {
                    lowerRating = Double.valueOf(range[0].trim());
                    higherRating = Double.valueOf(range[1].trim());
                } catch (NumberFormatException nfe) {
                    throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
                }
                if (Rating.isValidScore(lowerRating)
                        && Rating.isValidScore(higherRating)) {
                    filterRange = new FilterRange<Rating>(
                            new Rating(lowerRating, lowerRating, lowerRating, lowerRating),
                            new Rating(higherRating, higherRating, higherRating, higherRating));
                } else {
                    throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
                }
            }
        } else { //It is a value instead
            double exactRating = Rating.DEFAULT_SCORE;
            try {
                exactRating = Double.valueOf(s.trim());
            } catch (NumberFormatException nfe) {
                throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
            }
            if (Rating.isValidScore(exactRating)) {
                filterRange = new FilterRange<Rating>(new Rating(exactRating, exactRating, exactRating, exactRating));
            } else {
                throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
            }
        }
        Predicate<Person> predicate = new RatingInKeywordsRangePredicate(filterRange);
        return predicate;
    }

    /**
     * Parses a Optional of predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseGradePointAverage(Optional<String> predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        if (predicateString.isPresent()) {
            return parseGradePointAverage(predicateString.get());
        } else {
            return null;
        }
    }

    /**
     * Parses a predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseGradePointAverage(String predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        String[] predicateStrings = predicateString.split(",");
        Arrays.stream(predicateStrings).map(String::trim).toArray(unused -> predicateStrings);
        if (predicateStrings.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        Predicate<Person> predicate = processGradePointAveragePredicateStrings(predicateStrings);
        return predicate;
    }

    /**
     * Parses the string array of all single predicate strings to a predicate
     * @param predicateStrings array of predicateString
     * @return the predicate user demanded
     * @throws IllegalValueException
     */
    private static Predicate<Person> processGradePointAveragePredicateStrings(String[] predicateStrings)
            throws IllegalValueException {
        List<Predicate<Person>> allPredicates = new ArrayList<Predicate<Person>>();
        for (String s: predicateStrings) {
            Predicate<Person> predicate = formGradePointAveragePredicateFromPredicateString(s);
            allPredicates.add(predicate);
        }
        return combineAllPredicates(allPredicates);
    }

    /**
     * Form a single predicate from a single predicate string
     * @param s a single predicate string
     * @return a single predicate
     * @throws IllegalValueException
     */
    private static Predicate<Person> formGradePointAveragePredicateFromPredicateString(String s)
            throws IllegalValueException {
        FilterRange<GradePointAverage> filterRange;
        if (s.contains("-")) { //It is a range
            String[] range = s.split("-");
            if (range.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            } else {
                try {
                    Double.valueOf(range[0].trim());
                    Double.valueOf(range[1].trim());
                } catch (NumberFormatException nfe) {
                    throw new IllegalValueException(GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
                }
                if (GradePointAverage.isValidGradePointAverage(range[0].trim())
                        && GradePointAverage.isValidGradePointAverage(range[1].trim())) {
                    filterRange = new FilterRange<GradePointAverage>(
                            new GradePointAverage(range[0].trim()),
                            new GradePointAverage(range[1].trim()));
                } else {
                    throw new IllegalValueException(GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
                }
            }
        } else { //It is a value instead
            try {
                Double.valueOf(s.trim());
            } catch (NumberFormatException nfe) {
                throw new IllegalValueException(GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
            }
            if (GradePointAverage.isValidGradePointAverage(s.trim())) {
                filterRange = new FilterRange<GradePointAverage>(new GradePointAverage(s.trim()));
            } else {
                throw new IllegalValueException(GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
            }
        }
        Predicate<Person> predicate = new GradePointAverageInKeywordsRangePredicate(filterRange);
        return predicate;
    }

    /**
     * Parses a Optional of  predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseInterviewDate(Optional<String> predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        if (predicateString.isPresent()) {
            return parseInterviewDate(predicateString.get());
        } else {
            return null;
        }
    }

    /**
     * Parses a predicateString to a Predicate used to filter Person
     * @param predicateString a predicate string read from user input
     * @return a Predicate for filter command
     * @throws IllegalValueException
     */
    public static Predicate<Person> parseInterviewDate(String predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        String[] predicateStrings = predicateString.split(",");
        Arrays.stream(predicateStrings).map(String::trim).toArray(unused -> predicateStrings);
        if (predicateStrings.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        Predicate<Person> predicate = processInterviewDatePredicateStrings(predicateStrings);
        return predicate;
    }

    /**
     * Parses the string array of all single predicate strings to a predicate
     * @param predicateStrings array of predicateString
     * @return the predicate user demanded
     * @throws IllegalValueException
     */
    private static Predicate<Person> processInterviewDatePredicateStrings(String[] predicateStrings)
            throws IllegalValueException {
        List<Predicate<Person>> allPredicates = new ArrayList<Predicate<Person>>();
        for (String s: predicateStrings) {
            Predicate<Person> predicate = formInterviewDatePredicateFromPredicateString(s);
            allPredicates.add(predicate);
        }
        return combineAllPredicates(allPredicates);
    }

    /**
     * Form a single predicate from a single predicate string
     * @param s a single predicate string
     * @return a single predicate
     * @throws IllegalValueException
     */
    private static Predicate<Person> formInterviewDatePredicateFromPredicateString(String s)
            throws IllegalValueException {
        FilterRange<InterviewDate> filterRange;
        if (s.contains("-")) { //It is a range
            String[] range = s.split("-");
            if (range.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            } else if (InterviewDateUtil.isValidInterviewDate(range[0].trim())
                    && InterviewDateUtil.isValidInterviewDate(range[1].trim())) {
                try {
                    filterRange = new FilterRange<InterviewDate>(
                            new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime(range[0].trim())),
                            new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime(range[1].trim())));
                } catch (DateTimeParseException dtpe) {
                    throw new ParseException(InterviewDateUtil.MESSAGE_INTERVIEW_DATE_CONSTRAINT);
                }
            } else {
                throw new IllegalValueException(InterviewDateUtil.MESSAGE_INTERVIEW_DATE_CONSTRAINT);
            }

        } else { //It is a value instead
            if (InterviewDateUtil.isValidInterviewDate(s.trim())) {
                try {
                    filterRange = new FilterRange<InterviewDate>(
                            new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime(s.trim())),
                            new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime(s.trim())));
                } catch (DateTimeParseException dtpe) {
                    throw new ParseException(InterviewDateUtil.MESSAGE_INTERVIEW_DATE_CONSTRAINT);
                }
            } else {
                throw new IllegalValueException(InterviewDateUtil.MESSAGE_INTERVIEW_DATE_CONSTRAINT);
            }
        }
        Predicate<Person> predicate = new InterviewDateInKeywordsRangePredicate(filterRange);
        return predicate;
    }

}
