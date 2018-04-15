# ZacZequn
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple orders - last order accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ORDER_DESC_AMY + ORDER_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // missing order prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ORDER_BOB
                + ADDRESS_DESC_BOB, expectedMessage);
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid order
        assertParseFailure(parser, NAME_DESC_BOB +  PHONE_DESC_BOB + INVALID_ORDER_DESC
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Order.MESSAGE_ORDER_CONSTRAINTS);
```
###### \java\seedu\address\storage\XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidOrder_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_ORDER,
                        VALID_ADDRESS, VALID_HALAL, VALID_VEGETARIAN, VALID_TAGS);
        String expectedMessage = Order.MESSAGE_ORDER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullOrder_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_HALAL, VALID_VEGETARIAN, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Order.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
```
###### \java\seedu\address\storage\XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidHalal_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_ORDER,
                        VALID_ADDRESS, INVALID_HALAL, VALID_VEGETARIAN, VALID_TAGS);
        String expectedMessage = Halal.MESSAGE_HALAL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullHalal_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_ORDER,
                VALID_ADDRESS, null, VALID_VEGETARIAN, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Halal.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidVegetarian_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_ORDER,
                        VALID_ADDRESS, VALID_HALAL, INVALID_VEGETARIAN, VALID_TAGS);
        String expectedMessage = Vegetarian.MESSAGE_VEGETARIAN_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullVegetarian_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_ORDER,
                VALID_ADDRESS, VALID_HALAL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Vegetarian.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
```
###### \java\seedu\address\testutil\DishBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.dish.Dish;
import seedu.address.model.dish.Name;
import seedu.address.model.dish.Price;

/**
 * A utility class to help with building Dish objects.
 */
public class DishBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PRICE = "3";

    private Name name;
    private Price price;

    public DishBuilder() {
        name = new Name(DEFAULT_NAME);
        price = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the DishBuilder with the data of {@code personToCopy}.
     */
    public DishBuilder(Dish dishToCopy) {
        name = dishToCopy.getName();
        price = dishToCopy.getPrice();
    }

    /**
     * Sets the {@code Name} of the {@code Dish} that we are building.
     */
    public DishBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Dish} that we are building.
     */
    public DishBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }


    public Dish build() {
        return new Dish(name, price);
    }

}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Order} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withOrder(String order) {
        descriptor.setOrder(new Order(order));
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Order} of the {@code Person} that we are building.
     */
    public PersonBuilder withOrder(String order) {
        this.order = new Order(order);
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Halal} of the {@code Person} that we are building.
     */
    public PersonBuilder withHalal(String halal) {
        this.halal = new Halal(halal);
        return this;
    }

    /**
     * Sets the {@code Vegetarian} of the {@code Person} that we are building.
     */
    public PersonBuilder withVegetarian(String vegetarian) {
        this.vegetarian = new Vegetarian(vegetarian);
        return this;
    }
```
###### \java\seedu\address\testutil\TypicalDishes.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Menu;
import seedu.address.model.dish.Dish;

/**
 * A utility class containing a list of {@code Dish} objects to be used in tests.
 */
public class TypicalDishes {

    public static final Dish CHICKENRICE = new DishBuilder().withName("Chicken Rice").withPrice("3").build();
    public static final Dish CURRYCHICKEN = new DishBuilder().withName("Curry Chicken").withPrice("4").build();
    public static final Dish CHICKENCHOP = new DishBuilder().withName("Chicken Chop").withPrice("5").build();
    public static final Dish BANMIAN = new DishBuilder().withName("Ban Mian").withPrice("4").build();
    public static final Dish ICEMILO = new DishBuilder().withName("Ice Milo").withPrice("2").build();
    public static final Dish COFFEE = new DishBuilder().withName("Coffee").withPrice("2").build();


    private TypicalDishes() {} // prevents instantiation

    /**
     * Returns an {@code Menu} with all the typical dishes.
     */
    public static Menu getTypicalMenu() {
        Menu menu = new Menu();
        for (Dish dish : getTypicalDishes()) {
            menu.addDish(dish);
        }
        return menu;
    }

    public static List<Dish> getTypicalDishes() {
        return new ArrayList<>(Arrays.asList(CHICKENRICE, CURRYCHICKEN, CHICKENCHOP, BANMIAN, ICEMILO, COFFEE));
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     *  Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     *  in {@code PersonCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     *
     *  @see PersonCard#getTagColorStyleFor(String)
     */
    private static String getTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "classmates":
        case "owesMoney":
            return "teal";

        case "colleagues":
        case "neighbours":
            return "yellow";

        case "family":
        case "friend":
            return "orange";

        case "friends":
            return "brown";

        case "husband":
            return "grey";

        default:
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     *  Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     *  color.
     */
    private static void assertTagsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }
```
