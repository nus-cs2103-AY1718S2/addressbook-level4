package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.KeywordPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) {
        List<Predicate<Person>> predicateList = new ArrayList<>();
        predicateList.add(new KeywordPredicate(args.trim()));
        Predicate<Person> allPredicates = combineAllPredicates(predicateList);
        return new FilterCommand(allPredicates);
    }

    /**
     * Combines all the predicates in the predicateList into a single Predicate
     * @param predicateList a list of non-empty predicates
     * @return a single Predicate combining all the predicates in the predicateList
     */
    private Predicate<Person> combineAllPredicates(List<Predicate<Person>> predicateList) {
        assert(predicateList.size() >= 1);
        Predicate<Person> allPredicates = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            allPredicates.and(predicateList.get(i));
        }
        return allPredicates;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
