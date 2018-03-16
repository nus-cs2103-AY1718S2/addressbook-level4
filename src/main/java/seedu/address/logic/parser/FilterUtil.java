package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

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
import seedu.address.model.person.Person;

public class FilterUtil {

    public static Predicate<Person> parseExpectedGraduationYear(Optional<String> predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        if(predicateString.isPresent()){
            return parseExpectedGraduationYear(predicateString.get());
        }else{
            throw new ParseException(FilterCommand.MESSAGE_USAGE);
        }
    }

    public static Predicate<Person> parseExpectedGraduationYear(String predicateString)
            throws IllegalValueException {
        requireNonNull(predicateString);
        String[] predicateStrings = predicateString.split(",");
        Arrays.stream(predicateStrings).map(String::trim).toArray(unused -> predicateStrings);
        if(predicateStrings.length==0) {
            throw new ParseException(FilterCommand.MESSAGE_USAGE);
        }
        Predicate<Person> predicate = processExpectedGraduationYearPredicateStrings(predicateStrings);
        return predicate;

    }

    private static Predicate<Person> processExpectedGraduationYearPredicateStrings(String[] predicateStrings)
            throws IllegalValueException {
        List<Predicate<Person>> allPredicates = new ArrayList<Predicate<Person>>();
        for(String s: predicateStrings){
            Predicate<Person> predicate = formExpectedGraduationYearPredicateFromPredicateString(s);
            allPredicates.add(predicate);
        }
        return combineAllPredicates(allPredicates);

    }

    private static Predicate<Person> combineAllPredicates(List<Predicate<Person>> predicateList) {
        assert(predicateList.size() >= 1);
        Predicate<Person> allPredicates = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            allPredicates.or(predicateList.get(i));
        }
        return allPredicates;
    }

    private static Predicate<Person> formExpectedGraduationYearPredicateFromPredicateString(String s)
            throws IllegalValueException {
        FilterRange<ExpectedGraduationYear> filterRange;
        if (s.contains("-")) { //It is a range
            String[] range = s.split("-");
            if(range.length!=2) {
                throw new ParseException(FilterCommand.MESSAGE_USAGE);
            } else if (ExpectedGraduationYear.isValidExpectedGraduationYear(range[0].trim())
                    && ExpectedGraduationYear.isValidExpectedGraduationYear(range[1].trim())) {
                filterRange = new FilterRange<ExpectedGraduationYear>
                        (new ExpectedGraduationYear(range[0].trim()), new ExpectedGraduationYear(range[1].trim()));
            } else{
                throw new IllegalValueException(ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
            }

        } else { //It is a value instead
            if(ExpectedGraduationYear.isValidExpectedGraduationYear(s.trim())) {
                filterRange = new FilterRange<ExpectedGraduationYear>(new ExpectedGraduationYear(s));
            } else {
                throw new IllegalValueException(ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
            }
        }
        Predicate<Person> predicate = new ExpectedGraduationYearInKeywordsRangePredicate(filterRange);
        return predicate;
    }
}
