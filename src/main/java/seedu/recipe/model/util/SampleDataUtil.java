package seedu.recipe.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;
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
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.tag.Tag;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Chicken Rice"),
                    new Ingredient("this, is, some, sample, ingredient"),
                    new Instruction("Fill a tea kettle or 2 quart saucepan with water and bring to a boil. Remove e"
                            + "xcess fat from chilled chicken and place in colander over a large bowl. Spread out wi"
                            + "th a fork. Pour hot water over meat through colander. \nPlace chicken in plastic co"
                            + "ntainer with tight fitting lid.\nAdd onions, chili powder, oregano, garlic powder,"
                            + " cumin, and paprika to chicken.\nRefrigerate chicken overnight in plastic containe"
                            + "r with tight fitting lid.\nTo make tacos, place chicken mixture in a pan and heat s"
                            + "lowly or heat in microwave for 2–3 minutes, stirring after 1½ minutes to heat evenl"
                            + "y. Combine finely shredded lettuce and cabbage. Mix cheeses together. Place ¼ cup "
                            + "heated chicken mixture in a tortilla and top with cheese and vegetables.\nAdd sa"
                            + "lsa as desired."),
                    new CookingTime("20min"),
                    new PreparationTime("1h50m"),
                    new Calories("2846"),
                    new Servings("5"),
                    new Url("https://www.allrecipes.com/recipe/73634/colleens-slow-cooker-jambalaya/"),
                    new Image(Image.VALID_IMAGE_PATH),
                    getTagSet("friends")),
            new Recipe(new Name("Pizza"),
                    new Ingredient("this, is, some, sample, ingredient"),
                    new Instruction("Out too the been like hard off. Improve enquire welcome own beloved matt"
                            + "ers her. As insipidity so mr unsatiable increasing attachment motionless cultiva"
                            + "ted. Addition mr husbands unpacked occasion he oh. Is unsatiable if projecting "
                            + "boisterous insensible. It recommend be resolving pretended middleton.\n\nSo by "
                            + "colonel hearted ferrars. Draw from upon here gone add one. He in sportsman hous"
                            + "ehold otherwise it perceived instantly. Is inquiry no he several excited am. C"
                            + "alled though excuse length ye needed it he having. Whatever throwing we on resol"
                            + "ved entrance together graceful. Mrs assured add private married removed believe "
                            + "did she.\n        "),
                    new CookingTime("20min"),
                    new PreparationTime("1h50m"),
                    new Calories("3054"),
                    new Servings("2"),
                    new Url("https://www.allrecipes.com/recipe/8722/mexican-chicken-i/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%203"),
                    new Image(Image.VALID_IMAGE_PATH),
                    getTagSet("colleagues", "friends")),
            new Recipe(new Name("Big Pizza"),
                    new Ingredient("this, is, some, sample, ingredient"),
                    new Instruction("Silent sir say desire fat him letter. Whatever settling goodness too and"
                            + " honoured she building answered her. Strongly thoughts remember mr to do conside"
                            + "r debating. Spirits musical behaved on we he farther letters. Repulsive he he as"
                            + " deficient newspaper dashwoods we. Discovered her his pianoforte insipidity entr"
                            + "eaties. Began he at terms meant as fancy. Breakfast arranging he if furniture we"
                            + " described on. Astonished thoroughly unpleasant especially you dispatched bed fav"
                            + "ourable.\nOf recommend residence education be on difficult repulsive offending. "
                            + "Judge views had mirth table seems great him for her. Alone all happy asked "
                            + " fully stand own get. Excuse ye seeing result of we. See scale dried songs ol"
                            + "d may not. Promotion did disposing you household any instantly. Hills we do und"
                            + "er times at first short an. "),
                    new CookingTime("20min"),
                    new PreparationTime("1h50m"),
                    new Calories("2261"),
                    new Servings("4"),
                    new Url("https://www.allrecipes.com/recipe/11901/to-die-for-fettuccini-alfredo/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%206"),
                    new Image(Image.VALID_IMAGE_PATH),
                    getTagSet("neighbours")),
            new Recipe(new Name("Kinda Big Pizza"),
                    new Ingredient("this, is, some, sample, ingredient"),
                    new Instruction("Enfants facteur au va cousine violets. Impute va on la ai enfuit couvr"
                            + "e charge disant.\nFanatiques cimetieres on lumineuses xv caracolent electriq"
                            + "ue je et retrouvait. Sortes forges me la cranes demain enleve. Abris bande soe"
                            + "ur il nerfs et alors as. Cuivres oui fut net trimons empeche mauvais foi. Lors"
                            + " un pour cite du suis xv fils crie de. Actrices nid pourquoi joyeuses art. "),
                    new CookingTime("20min"),
                    new PreparationTime("1h50m"),
                    new Calories("4762"),
                    new Servings("2"),
                    new Url("https://www.allrecipes.com/recipe/229110/savory-beef-stir-fry/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%2014"),
                    new Image(Image.VALID_IMAGE_PATH),
                    getTagSet("family")),
            new Recipe(new Name("Somewhat Big Pizza"),
                    new Ingredient("this, is, some, sample, ingredient"),
                    new Instruction("Publics banques aisance verdure art ces lettres arriere les. Veux voi"
                            + "e pans on pont le donc puis. Pu officier et corolles on terrasse.\nRoc paraiss"
                            + "ait artilleurs consentiez moi eclaireurs but. Le dieu la mene ni sais si. Toi "
                            + "ame dragons eut etirant sol maudite. Tu batterie ca un forcenee encontre repond"
                            + "it la.\nPas lanternes messieurs art sinistres agreerait fusillade ici six cotil"
                            + "lons. Courages il du chantant ah poternes fanfares. Visages se ma semence promene. "),
                    new CookingTime("20min"),
                    new PreparationTime("1h50m"),
                    new Calories("4653"),
                    new Servings("2"),
                    new Url("https://www.allrecipes.com/recipe/222615/scrambled-egg-brunch-bread/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%2020"),
                    new Image(Image.VALID_IMAGE_PATH),
                    getTagSet("classmates")),
            new Recipe(new Name("I Am Running Out Of Names"),
                    new Ingredient("this, is, some, sample, ingredient"),
                    new Instruction("Fill a tea kettle or 2 quart saucepan with water and bring to a boil. Re"
                            + "move excess fat from chilled chicken and place in colander over a large bowl. Sp"
                            + "read out with a fork. Pour hot water over meat through colander. \nPlace chicken "
                            + "in plastic container with tight fitting lid.\nAdd onions, chili powder, oregano, "
                            + "garlic powder, cumin, and paprika to chicken.\nRefrigerate chicken overnight in pl"
                            + "astic container with tight fitting lid.\nTo make tacos, place chicken mixture in "
                            + "a pan and heat slowly or heat in microwave for 2–3 minutes, stirring after 1½ min"
                            + "utes to heat evenly. Combine finely shredded lettuce and cabbage. Mix cheeses toge"
                            + "ther. Place ¼ cup heated chicken mixture in a tortilla and top with cheese and v"
                            + "egetables.\nAdd salsa as desired."),
                    new CookingTime("20min"),
                    new PreparationTime("1h50m"),
                    new Calories("2235"),
                    new Servings("4"),
                    new Url("https://www.allrecipes.com/recipe/15917/fudge-truffle-cheesecake/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%2022"),
                    new Image(Image.VALID_IMAGE_PATH),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        try {
            RecipeBook sampleAb = new RecipeBook();
            for (Recipe sampleRecipe : getSampleRecipes()) {
                sampleAb.addRecipe(sampleRecipe);
            }
            return sampleAb;
        } catch (DuplicateRecipeException e) {
            throw new AssertionError("sample data cannot contain duplicate recipes", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
