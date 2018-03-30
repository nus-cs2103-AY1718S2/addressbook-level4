package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.smplatform.Facebook;
import seedu.address.model.smplatform.Link;
import seedu.address.model.smplatform.SocialMediaPlatform;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(personWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personCard = new PersonCard(personWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTags, 2);

        //@@author Nethergale
        // with platforms
        String[] links = {"www.facebook.com/examplepage", "www.twitter.com/examplepage"};
        Person personWithPlatforms = new PersonBuilder().withPlatforms(links).build();
        personCard = new PersonCard(personWithPlatforms, 3);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithPlatforms, 3);

        // with platforms purposely put into wrong key, should not display any icons
        Map<String, SocialMediaPlatform> customSmpMap = new HashMap<String, SocialMediaPlatform>();
        customSmpMap.put("unknown", new Facebook(new Link("www.facebook.com/testlink")));
        Set<Tag> defaultTags = new HashSet<>();
        defaultTags.add(new Tag("friends"));

        Person personWithIncorrectSmpMap = new Person(
                new Name("Alice Pauline"), new Phone("85355255"),
                new Email("alice@gmail.com"), new Address("123, Jurong West Ave 6, #08-111"),
                customSmpMap, defaultTags);
        personCard = new PersonCard(personWithIncorrectSmpMap, 4);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithIncorrectSmpMap, 4);
        //@@author
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
