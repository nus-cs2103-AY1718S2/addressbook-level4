package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_COOKING_TIME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_IMG;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_PREPARATION_TIME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_SERVINGS;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_URL;

import java.util.Set;
import java.util.stream.Stream;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.recipe.Calories;
import seedu.recipe.model.recipe.CookingTime;
import seedu.recipe.model.recipe.Image;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Servings;
import seedu.recipe.model.recipe.Url;
import seedu.recipe.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_INGREDIENT, PREFIX_INSTRUCTION, PREFIX_COOKING_TIME,
                        PREFIX_PREPARATION_TIME, PREFIX_CALORIES, PREFIX_SERVINGS, PREFIX_TAG, PREFIX_URL,
                        PREFIX_IMG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Ingredient ingredient = ParserUtil.parseIngredientOnInitialAdd(argMultimap.getValue(PREFIX_INGREDIENT))
                    .get();
            Instruction instruction =
                    ParserUtil.parseInstructionOnInitialAdd(argMultimap.getValue(PREFIX_INSTRUCTION)).get();
            CookingTime cookingTime =
                    ParserUtil.parseCookingTimeOnInitialAdd(argMultimap.getValue(PREFIX_COOKING_TIME)).get();
            PreparationTime preparationTime =
                    ParserUtil.parsePreparationTimeOnInitialAdd(argMultimap.getValue(PREFIX_PREPARATION_TIME)).get();
            Calories calories =
                    ParserUtil.parseCaloriesOnInitialAdd(argMultimap.getValue(PREFIX_CALORIES)).get();
            Servings servings =
                    ParserUtil.parseServingsOnInitialAdd(argMultimap.getValue(PREFIX_SERVINGS)).get();
            Url url = ParserUtil.parseUrlOnInitialAdd(argMultimap.getValue(PREFIX_URL)).get();
            Image image = ParserUtil.parseImageOnInitialAdd(argMultimap.getValue(PREFIX_IMG)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Recipe recipe = new Recipe(name, ingredient, instruction, cookingTime, preparationTime, calories, servings,
                    url, image, tagList);

            return new AddCommand(recipe);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
