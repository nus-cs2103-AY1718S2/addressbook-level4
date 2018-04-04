# tanhengyeow
###### /java/seedu/address/model/person/NameContainsPrefixesPredicateTest.java
``` java
public class NameContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsPrefixesPredicate firstPredicate =
                new NameContainsPrefixesPredicate(firstPredicateKeywordList);
        NameContainsPrefixesPredicate secondPredicate =
                new NameContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsPrefixesPredicate firstPredicateCopy =
                new NameContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsPrefixes_returnsTrue() {
        // One prefix
        NameContainsPrefixesPredicate predicate =
                new NameContainsPrefixesPredicate(Collections.singletonList("Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case prefix
        predicate = new NameContainsPrefixesPredicate(Arrays.asList("aLi"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        NameContainsPrefixesPredicate predicate = new NameContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching prefix
        predicate = new NameContainsPrefixesPredicate(Arrays.asList("Charl"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
```
###### /java/seedu/address/model/person/NameContainsKeywordsPredicateTest.java
``` java
public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy =
                new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email, expected graduation year and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withExpectedGraduationYear("2020").build()));
    }
}
```
###### /java/seedu/address/model/person/JobAppliedContainsSuffixesPredicateTest.java
``` java
public class JobAppliedContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Software Engineer");
        List<String> secondPredicateKeywordList = Arrays.asList("Front-end Developer", "Data Analyst");

        JobAppliedContainsSuffixesPredicate firstPredicate =
                new JobAppliedContainsSuffixesPredicate(firstPredicateKeywordList);
        JobAppliedContainsSuffixesPredicate secondPredicate =
                new JobAppliedContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobAppliedContainsSuffixesPredicate firstPredicateCopy =
                new JobAppliedContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobAppliedContainsSuffixes_returnsTrue() {
        // One suffix
        JobAppliedContainsSuffixesPredicate predicate =
                new JobAppliedContainsSuffixesPredicate(Collections.singletonList("eer"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Mixed-case suffix
        predicate = new JobAppliedContainsSuffixesPredicate(Arrays.asList("EEr"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }

    @Test
    public void test_jobAppliedDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        JobAppliedContainsSuffixesPredicate predicate =
                new JobAppliedContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Non-matching suffix
        predicate = new JobAppliedContainsSuffixesPredicate(Arrays.asList("loper"));
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }
}
```
###### /java/seedu/address/model/person/CommentContainsKeywordsPredicateTest.java
``` java
public class CommentContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("He is decent");
        List<String> secondPredicateKeywordList = Arrays.asList("He is decent", "She is decent");

        CommentContainsKeywordsPredicate firstPredicate =
                new CommentContainsKeywordsPredicate(firstPredicateKeywordList);
        CommentContainsKeywordsPredicate secondPredicate =
                new CommentContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CommentContainsKeywordsPredicate firstPredicateCopy =
                new CommentContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_commentContainsKeywords_returnsTrue() {
        // One keyword
        CommentContainsKeywordsPredicate predicate =
                new CommentContainsKeywordsPredicate(Collections.singletonList("decent"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Multiple keywords
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("is", "decent"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Only one matching keyword
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("not", "decent"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Mixed-case keywords
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("iS", "dEcEnt"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }

    @Test
    public void test_commentDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CommentContainsKeywordsPredicate predicate =
                new CommentContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Non-matching keyword
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("outstanding"));
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Keywords match phone, email, expected graduation year and name, but does not match comment
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withComment("He is decent").withExpectedGraduationYear("2020").build()));
    }
}
```
###### /java/seedu/address/model/person/MajorContainsKeywordsPredicateTest.java
``` java
public class MajorContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer", "Science");

        MajorContainsKeywordsPredicate firstPredicate =
                new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        MajorContainsKeywordsPredicate secondPredicate =
                new MajorContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsKeywordsPredicate firstPredicateCopy =
                new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsKeywords_returnsTrue() {
        // One keyword
        MajorContainsKeywordsPredicate predicate =
                new MajorContainsKeywordsPredicate(Collections.singletonList("Computer"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Multiple keywords
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Computer", "Science"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Only one matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Computer", "Engineering"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case keywords
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("ComPutEr", "SciEncE"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Non-matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Security"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Keywords match phone, email, expected graduation year and address, but does not match major
        predicate = new MajorContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street", "2020", "Information", "Security"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withExpectedGraduationYear("2020")
                .withMajor("Computer Science").build()));
    }
}
```
###### /java/seedu/address/model/person/AddressContainsPrefixesPredicateTest.java
``` java
public class AddressContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Kent Ridge Drive");
        List<String> secondPredicateKeywordList = Arrays.asList("Kent Ridge Drive", "Computing Drive");

        AddressContainsPrefixesPredicate firstPredicate =
                new AddressContainsPrefixesPredicate(firstPredicateKeywordList);
        AddressContainsPrefixesPredicate secondPredicate =
                new AddressContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsPrefixesPredicate firstPredicateCopy =
                new AddressContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsPrefixes_returnsTrue() {
        // One prefix
        AddressContainsPrefixesPredicate predicate =
                new AddressContainsPrefixesPredicate(Collections.singletonList("Com"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Mixed-case prefix
        predicate = new AddressContainsPrefixesPredicate(Arrays.asList("cOm"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }

    @Test
    public void test_addressDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        AddressContainsPrefixesPredicate predicate =
                new AddressContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Non-matching prefix
        predicate = new AddressContainsPrefixesPredicate(Arrays.asList("Sci"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }
}
```
###### /java/seedu/address/model/person/NameContainsSubstringsPredicateTest.java
``` java
public class NameContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsSubstringsPredicate firstPredicate =
                new NameContainsSubstringsPredicate(firstPredicateKeywordList);
        NameContainsSubstringsPredicate secondPredicate =
                new NameContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsSubstringsPredicate firstPredicateCopy =
                new NameContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsSubstrings_returnsTrue() {
        // One substring
        NameContainsSubstringsPredicate predicate =
                new NameContainsSubstringsPredicate(Collections.singletonList("ce Bo"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case substring
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("Ce BO"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        NameContainsSubstringsPredicate predicate = new NameContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching substring
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("harle"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
```
###### /java/seedu/address/model/person/MajorContainsSubstringsPredicateTest.java
``` java
public class MajorContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer", "Science");

        MajorContainsSubstringsPredicate firstPredicate =
                new MajorContainsSubstringsPredicate(firstPredicateKeywordList);
        MajorContainsSubstringsPredicate secondPredicate =
                new MajorContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsSubstringsPredicate firstPredicateCopy =
                new MajorContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsSubstrings_returnsTrue() {
        // One substring
        MajorContainsSubstringsPredicate predicate =
                new MajorContainsSubstringsPredicate(Collections.singletonList("ter Sci"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case substring
        predicate = new MajorContainsSubstringsPredicate(Arrays.asList("TeR sCi"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        MajorContainsSubstringsPredicate predicate = new MajorContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Non-matching substring
        predicate = new MajorContainsSubstringsPredicate(Arrays.asList("mation Secu"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }
}
```
###### /java/seedu/address/model/person/CommentContainsSuffixesPredicateTest.java
``` java
public class CommentContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("He is decent");
        List<String> secondPredicateKeywordList = Arrays.asList("He is decent", "She is decent");

        CommentContainsSuffixesPredicate firstPredicate =
                new CommentContainsSuffixesPredicate(firstPredicateKeywordList);
        CommentContainsSuffixesPredicate secondPredicate =
                new CommentContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CommentContainsSuffixesPredicate firstPredicateCopy =
                new CommentContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_commentContainsSuffixes_returnsTrue() {
        // One suffix
        CommentContainsSuffixesPredicate predicate =
                new CommentContainsSuffixesPredicate(Collections.singletonList("ent"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Mixed-case suffix
        predicate = new CommentContainsSuffixesPredicate(Arrays.asList("CeNt"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }

    @Test
    public void test_commentDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        CommentContainsSuffixesPredicate predicate =
                new CommentContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Non-matching suffix
        predicate = new CommentContainsSuffixesPredicate(Arrays.asList("alright"));
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }
}
```
###### /java/seedu/address/model/person/AddressContainsKeywordsPredicateTest.java
``` java
public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Kent Ridge Drive");
        List<String> secondPredicateKeywordList = Arrays.asList("Kent Ridge Drive", "Computing Drive");

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Computing"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Computing", "Drive"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Kent Ridge Drive").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Computing", "Test"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("comPuTing", "dRivE"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Com"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Keywords match phone, email, expected graduation year and name, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withExpectedGraduationYear("2020").build()));
    }
}
```
###### /java/seedu/address/model/person/UniversityContainsSubstringsPredicateTest.java
``` java
public class UniversityContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("NUS");
        List<String> secondPredicateKeywordList = Arrays.asList("NUS", "NTU");

        UniversityContainsSubstringsPredicate firstPredicate =
                new UniversityContainsSubstringsPredicate(firstPredicateKeywordList);
        UniversityContainsSubstringsPredicate secondPredicate =
                new UniversityContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniversityContainsSubstringsPredicate firstPredicateCopy =
                new UniversityContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_universityContainsSubstrings_returnsTrue() {
        // One substring
        UniversityContainsSubstringsPredicate predicate =
                new UniversityContainsSubstringsPredicate(Collections.singletonList("e NU"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Mixed-case substring
        predicate = new UniversityContainsSubstringsPredicate(Arrays.asList("LE nu"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));
    }

    @Test
    public void test_universityDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        UniversityContainsSubstringsPredicate predicate =
                new UniversityContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Non-matching substring
        predicate = new UniversityContainsSubstringsPredicate(Arrays.asList("sm"));
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));
    }
}
```
###### /java/seedu/address/model/person/PhoneContainsKeywordsPredicateTest.java
``` java
public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("999");
        List<String> secondPredicateKeywordList = Arrays.asList("999", "555");

        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("999555"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("999555").build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("999555", "555999"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("999555").build()));

        // Only one matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("111222", "222111"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("111222").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("999").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("555"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("999111").build()));

        // Keywords match name, email, expected graduation year and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "Street", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withExpectedGraduationYear("2020").build()));
    }
}
```
###### /java/seedu/address/model/person/NameContainsSuffixesPredicateTest.java
``` java
public class NameContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsSuffixesPredicate firstPredicate =
                new NameContainsSuffixesPredicate(firstPredicateKeywordList);
        NameContainsSuffixesPredicate secondPredicate =
                new NameContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsSuffixesPredicate firstPredicateCopy =
                new NameContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsSuffixes_returnsTrue() {
        // One suffix
        NameContainsSuffixesPredicate predicate =
                new NameContainsSuffixesPredicate(Collections.singletonList("ob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case suffix
        predicate = new NameContainsSuffixesPredicate(Arrays.asList("Ob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        NameContainsSuffixesPredicate predicate = new NameContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching suffix
        predicate = new NameContainsSuffixesPredicate(Arrays.asList("les"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
```
###### /java/seedu/address/model/person/EmailContainsPrefixesPredicateTest.java
``` java
public class EmailContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("hy@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("hy@example.com", "yh@example.com");

        EmailContainsPrefixesPredicate firstPredicate =
                new EmailContainsPrefixesPredicate(firstPredicateKeywordList);
        EmailContainsPrefixesPredicate secondPredicate =
                new EmailContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsPrefixesPredicate firstPredicateCopy =
                new EmailContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsPrefixes_returnsTrue() {
        // One prefix
        EmailContainsPrefixesPredicate predicate =
                new EmailContainsPrefixesPredicate(Collections.singletonList("hy"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));

        // Mixed-case prefix
        predicate = new EmailContainsPrefixesPredicate(Arrays.asList("Hy"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        EmailContainsPrefixesPredicate predicate =
                new EmailContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@lol.com").build()));

        // Non-matching prefix
        predicate = new EmailContainsPrefixesPredicate(Arrays.asList("yh"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }
}
```
###### /java/seedu/address/model/person/CommentContainsSubstringsPredicateTest.java
``` java
public class CommentContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("He is decent");
        List<String> secondPredicateKeywordList = Arrays.asList("He is decent", "She is decent");

        CommentContainsSubstringsPredicate firstPredicate =
                new CommentContainsSubstringsPredicate(firstPredicateKeywordList);
        CommentContainsSubstringsPredicate secondPredicate =
                new CommentContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CommentContainsSubstringsPredicate firstPredicateCopy =
                new CommentContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_commentContainsSubstrings_returnsTrue() {
        // One substring
        CommentContainsSubstringsPredicate predicate =
                new CommentContainsSubstringsPredicate(Collections.singletonList("dec"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Mixed-case substring
        predicate = new CommentContainsSubstringsPredicate(Arrays.asList("s DecE"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }

    @Test
    public void test_commentDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        CommentContainsSubstringsPredicate predicate =
                new CommentContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Non-matching substring
        predicate = new CommentContainsSubstringsPredicate(Arrays.asList("test"));
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }
}
```
###### /java/seedu/address/model/person/MajorContainsPrefixesPredicateTest.java
``` java
public class MajorContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer", "Science");

        MajorContainsPrefixesPredicate firstPredicate =
                new MajorContainsPrefixesPredicate(firstPredicateKeywordList);
        MajorContainsPrefixesPredicate secondPredicate =
                new MajorContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsPrefixesPredicate firstPredicateCopy =
                new MajorContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsPrefixes_returnsTrue() {
        // One prefix
        MajorContainsPrefixesPredicate predicate =
                new MajorContainsPrefixesPredicate(Collections.singletonList("Com"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case prefix
        predicate = new MajorContainsPrefixesPredicate(Arrays.asList("CoM"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        MajorContainsPrefixesPredicate predicate = new MajorContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Non-matching prefix
        predicate = new MajorContainsPrefixesPredicate(Arrays.asList("Info"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }
}
```
###### /java/seedu/address/model/person/PhoneContainsPrefixesPredicateTest.java
``` java
public class PhoneContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("999");
        List<String> secondPredicateKeywordList = Arrays.asList("999", "555");

        PhoneContainsPrefixesPredicate firstPredicate =
                new PhoneContainsPrefixesPredicate(firstPredicateKeywordList);
        PhoneContainsPrefixesPredicate secondPredicate =
                new PhoneContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsPrefixesPredicate firstPredicateCopy =
                new PhoneContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsPrefixes_returnsTrue() {
        // One prefix
        PhoneContainsPrefixesPredicate predicate =
                new PhoneContainsPrefixesPredicate(Collections.singletonList("99"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("999555").build()));
    }

    @Test
    public void test_phoneDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        PhoneContainsPrefixesPredicate predicate =
                new PhoneContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("999").build()));

        // Non-matching prefix
        predicate = new PhoneContainsPrefixesPredicate(Arrays.asList("555"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("999111").build()));
    }
}
```
###### /java/seedu/address/model/person/JobAppliedContainsPrefixesPredicateTest.java
``` java
public class JobAppliedContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Software Engineer");
        List<String> secondPredicateKeywordList = Arrays.asList("Front-end Developer", "Data Analyst");

        JobAppliedContainsPrefixesPredicate firstPredicate =
                new JobAppliedContainsPrefixesPredicate(firstPredicateKeywordList);
        JobAppliedContainsPrefixesPredicate secondPredicate =
                new JobAppliedContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobAppliedContainsPrefixesPredicate firstPredicateCopy =
                new JobAppliedContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobAppliedContainsPrefixes_returnsTrue() {
        // One prefix
        JobAppliedContainsPrefixesPredicate predicate =
                new JobAppliedContainsPrefixesPredicate(Collections.singletonList("Soft"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Mixed-case prefix
        predicate = new JobAppliedContainsPrefixesPredicate(Arrays.asList("sOfT"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }

    @Test
    public void test_jobAppliedDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        JobAppliedContainsPrefixesPredicate predicate =
                new JobAppliedContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Non-matching prefix
        predicate = new JobAppliedContainsPrefixesPredicate(Arrays.asList("Data"));
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }
}
```
###### /java/seedu/address/model/person/AddressContainsSubstringsPredicateTest.java
``` java
public class AddressContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Kent Ridge Drive");
        List<String> secondPredicateKeywordList = Arrays.asList("Kent Ridge Drive", "Computing Drive");

        AddressContainsSubstringsPredicate firstPredicate =
                new AddressContainsSubstringsPredicate(firstPredicateKeywordList);
        AddressContainsSubstringsPredicate secondPredicate =
                new AddressContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsSubstringsPredicate firstPredicateCopy =
                new AddressContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsSubstrings_returnsTrue() {
        // One substring
        AddressContainsSubstringsPredicate predicate =
                new AddressContainsSubstringsPredicate(Collections.singletonList("ing Dr"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Mixed-case substring
        predicate = new AddressContainsSubstringsPredicate(Arrays.asList("tIng Dri"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }

    @Test
    public void test_addressDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        AddressContainsSubstringsPredicate predicate =
                new AddressContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Non-matching substring
        predicate = new AddressContainsSubstringsPredicate(Arrays.asList("olland Dr"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }
}
```
###### /java/seedu/address/model/person/EmailContainsKeywordsPredicateTest.java
``` java
public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("hy@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("hy@example.com", "yh@example.com");

        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("hy@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("hy@example.com", "yh@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@lol.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("lol@lol.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));

        // Keywords match phone, name, expected graduation year and address, but does not match email
        predicate = new EmailContainsKeywordsPredicate(
                Arrays.asList("12345", "Alice", "Main", "Street", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withExpectedGraduationYear("2020").build()));
    }
}
```
###### /java/seedu/address/model/person/AddressContainsSuffixesPredicateTest.java
``` java
public class AddressContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Kent Ridge Drive");
        List<String> secondPredicateKeywordList = Arrays.asList("Kent Ridge Drive", "Computing Drive");

        AddressContainsSuffixesPredicate firstPredicate =
                new AddressContainsSuffixesPredicate(firstPredicateKeywordList);
        AddressContainsSuffixesPredicate secondPredicate =
                new AddressContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsSuffixesPredicate firstPredicateCopy =
                new AddressContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsSuffixes_returnsTrue() {
        // One suffix
        AddressContainsSuffixesPredicate predicate =
                new AddressContainsSuffixesPredicate(Collections.singletonList("ive"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Mixed-case suffix
        predicate = new AddressContainsSuffixesPredicate(Arrays.asList("IvE"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }

    @Test
    public void test_addressDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        AddressContainsSuffixesPredicate predicate =
                new AddressContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Non-matching suffix
        predicate = new AddressContainsSuffixesPredicate(Arrays.asList("road"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }
}
```
###### /java/seedu/address/model/person/GradePointAverageTest.java
``` java
public class GradePointAverageTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GradePointAverage(null));
    }

    @Test
    public void constructor_invalidGradePointAverage_throwsIllegalArgumentException() {
        String invalidGradePointAverage = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GradePointAverage(invalidGradePointAverage));
    }

    @Test
    public void isValidGradePointAverage() {
        // null gradePointAverage
        Assert.assertThrows(NullPointerException.class, () ->
                GradePointAverage.isValidGradePointAverage(null));

        // invalid gradePointAverage
        assertFalse(GradePointAverage.isValidGradePointAverage(" ")); // spaces only
        assertFalse(GradePointAverage.isValidGradePointAverage("5.10")); // not in range
        assertFalse(GradePointAverage.isValidGradePointAverage("test")); // non-numeric
        assertFalse(GradePointAverage.isValidGradePointAverage("-4.00")); // negative number

        // valid gradePointAverage
        assertTrue(GradePointAverage.isValidGradePointAverage("4.93"));
        assertTrue(GradePointAverage.isValidGradePointAverage("4.75"));
    }
}
```
###### /java/seedu/address/model/person/MajorContainsSuffixesPredicateTest.java
``` java
public class MajorContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer", "Science");

        MajorContainsSuffixesPredicate firstPredicate =
                new MajorContainsSuffixesPredicate(firstPredicateKeywordList);
        MajorContainsSuffixesPredicate secondPredicate =
                new MajorContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsSuffixesPredicate firstPredicateCopy =
                new MajorContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsSuffixes_returnsTrue() {
        // One suffix
        MajorContainsSuffixesPredicate predicate =
                new MajorContainsSuffixesPredicate(Collections.singletonList("ence"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case suffix
        predicate = new MajorContainsSuffixesPredicate(Arrays.asList("EnCe"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        MajorContainsSuffixesPredicate predicate = new MajorContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Non-matching suffix
        predicate = new MajorContainsSuffixesPredicate(Arrays.asList("curity"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }
}
```
###### /java/seedu/address/model/person/JobAppliedContainsKeywordsPredicateTest.java
``` java
public class JobAppliedContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Software Engineer");
        List<String> secondPredicateKeywordList = Arrays.asList("Front-end Developer", "Data Analyst");

        JobAppliedContainsKeywordsPredicate firstPredicate =
                new JobAppliedContainsKeywordsPredicate(firstPredicateKeywordList);
        JobAppliedContainsKeywordsPredicate secondPredicate =
                new JobAppliedContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobAppliedContainsKeywordsPredicate firstPredicateCopy =
                new JobAppliedContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobAppliedContainsKeywords_returnsTrue() {
        // One keyword
        JobAppliedContainsKeywordsPredicate predicate =
                new JobAppliedContainsKeywordsPredicate(Collections.singletonList("Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Multiple keywords
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("Software", "Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Only one matching keyword
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("Front-end", "Developer"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Developer").build()));

        // Mixed-case keywords
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("soFtWaRe", "eNGIneeR"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }

    @Test
    public void test_jobAppliedDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        JobAppliedContainsKeywordsPredicate predicate =
                new JobAppliedContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Non-matching keyword
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("analyst"));
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Keywords match phone, email, expected graduation year and name, but does not match job applied
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withJobApplied("Software Engineer")
                .withExpectedGraduationYear("2020").build()));
    }
}
```
###### /java/seedu/address/model/person/PhoneContainsSuffixesPredicateTest.java
``` java
public class PhoneContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("999");
        List<String> secondPredicateKeywordList = Arrays.asList("999", "555");

        PhoneContainsSuffixesPredicate firstPredicate =
                new PhoneContainsSuffixesPredicate(firstPredicateKeywordList);
        PhoneContainsSuffixesPredicate secondPredicate =
                new PhoneContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsSuffixesPredicate firstPredicateCopy =
                new PhoneContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsSuffixes_returnsTrue() {
        // One suffix
        PhoneContainsSuffixesPredicate predicate =
                new PhoneContainsSuffixesPredicate(Collections.singletonList("55"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("999555").build()));
    }

    @Test
    public void test_phoneDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        PhoneContainsSuffixesPredicate predicate =
                new PhoneContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("999").build()));

        // Non-matching suffix
        predicate = new PhoneContainsSuffixesPredicate(Arrays.asList("55"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("999111").build()));
    }
}
```
###### /java/seedu/address/model/person/PhoneContainsSubstringsPredicateTest.java
``` java
public class PhoneContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("999");
        List<String> secondPredicateKeywordList = Arrays.asList("999", "555");

        PhoneContainsSubstringsPredicate firstPredicate =
                new PhoneContainsSubstringsPredicate(firstPredicateKeywordList);
        PhoneContainsSubstringsPredicate secondPredicate =
                new PhoneContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsSubstringsPredicate firstPredicateCopy =
                new PhoneContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsSubstrings_returnsTrue() {
        // One substring
        PhoneContainsSubstringsPredicate predicate =
                new PhoneContainsSubstringsPredicate(Collections.singletonList("95"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("999555").build()));
    }

    @Test
    public void test_phoneDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        PhoneContainsSubstringsPredicate predicate =
                new PhoneContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("999").build()));

        // Non-matching substring
        predicate = new PhoneContainsSubstringsPredicate(Arrays.asList("555"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("999111").build()));
    }
}
```
###### /java/seedu/address/model/person/EmailContainsSuffixesPredicateTest.java
``` java
public class EmailContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("hy@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("hy@example.com", "yh@example.com");

        EmailContainsPrefixesPredicate firstPredicate =
                new EmailContainsPrefixesPredicate(firstPredicateKeywordList);
        EmailContainsPrefixesPredicate secondPredicate =
                new EmailContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsPrefixesPredicate firstPredicateCopy =
                new EmailContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsPrefixes_returnsTrue() {
        // One prefix
        EmailContainsPrefixesPredicate predicate =
                new EmailContainsPrefixesPredicate(Collections.singletonList("hy"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));

        // Mixed-case prefix
        predicate = new EmailContainsPrefixesPredicate(Arrays.asList("Hy"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        EmailContainsPrefixesPredicate predicate =
                new EmailContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@lol.com").build()));

        // Non-matching prefix
        predicate = new EmailContainsPrefixesPredicate(Arrays.asList("yh"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }
}
```
###### /java/seedu/address/model/person/UniversityContainsPrefixesPredicateTest.java
``` java
public class UniversityContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("NUS");
        List<String> secondPredicateKeywordList = Arrays.asList("NUS", "NTU");

        UniversityContainsPrefixesPredicate firstPredicate =
                new UniversityContainsPrefixesPredicate(firstPredicateKeywordList);
        UniversityContainsPrefixesPredicate secondPredicate =
                new UniversityContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniversityContainsPrefixesPredicate firstPredicateCopy =
                new UniversityContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_universityContainsPrefixes_returnsTrue() {
        // One prefix
        UniversityContainsSubstringsPredicate predicate =
                new UniversityContainsSubstringsPredicate(Collections.singletonList("e NU"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Mixed-case substring
        predicate = new UniversityContainsSubstringsPredicate(Arrays.asList("LE nu"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));
    }

    @Test
    public void test_universityDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        UniversityContainsSubstringsPredicate predicate =
                new UniversityContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Non-matching substring
        predicate = new UniversityContainsSubstringsPredicate(Arrays.asList("sm"));
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));
    }
}
```
###### /java/seedu/address/model/person/CommentContainsPrefixesPredicateTest.java
``` java
public class CommentContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("He is decent");
        List<String> secondPredicateKeywordList = Arrays.asList("He is decent", "She is decent");

        CommentContainsPrefixesPredicate firstPredicate =
                new CommentContainsPrefixesPredicate(firstPredicateKeywordList);
        CommentContainsPrefixesPredicate secondPredicate =
                new CommentContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CommentContainsPrefixesPredicate firstPredicateCopy =
                new CommentContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_commentContainsPrefixes_returnsTrue() {
        // One prefix
        CommentContainsPrefixesPredicate predicate =
                new CommentContainsPrefixesPredicate(Collections.singletonList("He"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Mixed-case prefix
        predicate = new CommentContainsPrefixesPredicate(Arrays.asList("hE"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }

    @Test
    public void test_commentDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        CommentContainsPrefixesPredicate predicate =
                new CommentContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Non-matching prefix
        predicate = new CommentContainsPrefixesPredicate(Arrays.asList("She"));
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }
}
```
###### /java/seedu/address/model/person/UniversityContainsSuffixesPredicateTest.java
``` java
public class UniversityContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("NUS");
        List<String> secondPredicateKeywordList = Arrays.asList("NUS", "NTU");

        UniversityContainsSuffixesPredicate firstPredicate =
                new UniversityContainsSuffixesPredicate(firstPredicateKeywordList);
        UniversityContainsSuffixesPredicate secondPredicate =
                new UniversityContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniversityContainsSuffixesPredicate firstPredicateCopy =
                new UniversityContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_universityContainsSuffixes_returnsTrue() {
        // One suffix
        UniversityContainsSuffixesPredicate predicate =
                new UniversityContainsSuffixesPredicate(Collections.singletonList("US"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Mixed-case substring
        predicate = new UniversityContainsSuffixesPredicate(Arrays.asList("uS"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));
    }

    @Test
    public void test_universityDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        UniversityContainsSuffixesPredicate predicate =
                new UniversityContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Non-matching suffix
        predicate = new UniversityContainsSuffixesPredicate(Arrays.asList("yale"));
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));
    }
}
```
###### /java/seedu/address/model/person/UniversityContainsKeywordsPredicateTest.java
``` java
public class UniversityContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("NUS");
        List<String> secondPredicateKeywordList = Arrays.asList("NUS", "NTU");

        UniversityContainsKeywordsPredicate firstPredicate =
                new UniversityContainsKeywordsPredicate(firstPredicateKeywordList);
        UniversityContainsKeywordsPredicate secondPredicate =
                new UniversityContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniversityContainsKeywordsPredicate firstPredicateCopy =
                new UniversityContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_universityContainsKeywords_returnsTrue() {
        // One keyword
        UniversityContainsKeywordsPredicate predicate =
                new UniversityContainsKeywordsPredicate(Collections.singletonList("NUS"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Multiple keywords
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("Yale", "NUS"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Only one matching keyword
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("Cool", "NUS"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Mixed-case keywords
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("yaLe", "nUs"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));
    }

    @Test
    public void test_universityDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        UniversityContainsKeywordsPredicate predicate =
                new UniversityContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Non-matching keyword
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("SMU"));
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Keywords match phone, email, expected graduation year and name, but does not match university
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withUniversity("NUS").withExpectedGraduationYear("2020").build()));
    }
}
```
###### /java/seedu/address/model/person/MajorTest.java
``` java
public class MajorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Major(null));
    }

    @Test
    public void constructor_invalidMajor_throwsIllegalArgumentException() {
        String invalidMajor = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Major(invalidMajor));
    }

    @Test
    public void isValidMajor() {
        // null major
        Assert.assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid major
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only
        assertFalse(Major.isValidMajor("^")); // only non-alphanumeric characters
        assertFalse(Major.isValidMajor("comp*")); // contains non-alphanumeric characters

        // valid major
        assertTrue(Major.isValidMajor("computer science")); // alphabets only
        assertTrue(Major.isValidMajor("12345")); // numbers only
        assertTrue(Major.isValidMajor("2nd major in Business")); // alphanumeric characters
        assertTrue(Major.isValidMajor("Computer Engineering")); // with capital letters
        assertTrue(Major.isValidMajor("Business Analytics and Information Security")); // long names
    }
}
```
###### /java/seedu/address/model/person/JobAppliedContainsSubstringsPredicateTest.java
``` java
public class JobAppliedContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Software Engineer");
        List<String> secondPredicateKeywordList = Arrays.asList("Front-end Developer", "Data Analyst");

        JobAppliedContainsSubstringsPredicate firstPredicate =
                new JobAppliedContainsSubstringsPredicate(firstPredicateKeywordList);
        JobAppliedContainsSubstringsPredicate secondPredicate =
                new JobAppliedContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobAppliedContainsSubstringsPredicate firstPredicateCopy =
                new JobAppliedContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobAppliedContainsSubstrings_returnsTrue() {
        // One substring
        JobAppliedContainsSubstringsPredicate predicate =
                new JobAppliedContainsSubstringsPredicate(Collections.singletonList("are Engin"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Mixed-case substring
        predicate = new JobAppliedContainsSubstringsPredicate(Arrays.asList("ArE EnGiNe"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }

    @Test
    public void test_jobAppliedDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        JobAppliedContainsSubstringsPredicate predicate =
                new JobAppliedContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Non-matching substring
        predicate = new JobAppliedContainsSubstringsPredicate(Arrays.asList("are Dev"));
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }
}
```
###### /java/seedu/address/model/person/EmailContainsSubstringsPredicateTest.java
``` java
public class EmailContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("hy@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("hy@example.com", "yh@example.com");

        EmailContainsSubstringsPredicate firstPredicate =
                new EmailContainsSubstringsPredicate(firstPredicateKeywordList);
        EmailContainsSubstringsPredicate secondPredicate =
                new EmailContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsSubstringsPredicate firstPredicateCopy =
                new EmailContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsSubstrings_returnsTrue() {
        // One substring
        EmailContainsSubstringsPredicate predicate =
                new EmailContainsSubstringsPredicate(Collections.singletonList("y@exa"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));

        // Mixed-case substring
        predicate = new EmailContainsSubstringsPredicate(Arrays.asList("Y@eXa"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        EmailContainsSubstringsPredicate predicate =
                new EmailContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@lol.com").build()));

        // Non-matching substring
        predicate = new EmailContainsSubstringsPredicate(Arrays.asList("yh@e"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }
}
```
###### /java/seedu/address/storage/XmlAddressBookStorageTest.java
``` java
    @Test
    public void backupAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        backupAddressBook(null, TEST_DATA_FOLDER + "test.bak");
    }

    /**
     * Backups {@code addressBook} at the specified {@code filePath}.
     */
    private void backupAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new XmlAddressBookStorage(filePath).backupAddressBook(addressBook);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void backupAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        backupAddressBook(new AddressBook(), null);
    }


}
```
###### /java/seedu/address/logic/commands/FindCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void execute_noKeywords_invalidCommandFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        FindCommand command;

        try {
            command = prepareCommand(" ");
            assertCommandSuccess(command, Collections.emptyList());
        } catch (ParseException pve) {
            if (!pve.getMessage().equals(expectedMessage)) {
                pve.printStackTrace();
            }
        }
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        FindCommand command = prepareCommand("Developer, lydia@example.com");
        assertCommandSuccess(command, Arrays.asList(CARL, FIONA));
    }

    @Test
    public void execute_singlePrefixWithSingleKeyword_onePersonsFound() throws ParseException {
        FindCommand command = prepareCommand(" n/Kurz");
        assertCommandSuccess(command, Arrays.asList(CARL, FIONA)); // due to fuzzy search
    }

    @Test
    public void execute_singlePrefixWithMultipleKeywords_multiplePersonsFound() throws ParseException {
        FindCommand command = prepareCommand(" n/Kurz, Elle, Kunz");
        assertCommandSuccess(command, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multiplePrefixesWithSingleKeyword_onePersonsFound() throws ParseException {
        FindCommand command = prepareCommand(" n/\"arl Kurz\" p/95352563");
        assertCommandSuccess(command, Arrays.asList(CARL));
    }

    @Test
    public void execute_multiplePrefixesWithMultipleKeywords_zeroPersonsFound() throws ParseException {
        FindCommand command = prepareCommand(" n/Kurz, Elle, Kunz p/999, 555, "
                + "000 e/heinz@example.com a/wall street y/2019");
        assertCommandSuccess(command, Arrays.asList());
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) throws ParseException {
        FindCommand command = parser.parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        command.execute();

        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        assertTrue(parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")))
                instanceof FindCommand);
        assertTrue(parser.parseCommand(FindCommand.COMMAND_WORD + " n/foo") instanceof FindCommand);
    }

```
###### /java/systemtests/FindCommandSystemTest.java
``` java
    @Test
    public void find() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        // first names of Benson and Daniel are "Meier", "Meier" fuzzy matched with "Meyer"
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Benson, Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Daniel, Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Daniel, Benson, Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " Daniel, Benson, NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(BENSON));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel, DANIEL); // fuzzy match to "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel, DANIEL, ELLE); // fuzzy match to "Meier" and "Meyer"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " Mark";
        ModelHelper.setFilteredList(expectedModel, CARL); // fuzzy match to Carl
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with n/ prefix (only 1 name) -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + "\"" + DANIEL.getName() + "\"";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with n/ prefix (>=2 names) -> 3 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + "\"" + DANIEL.getName() + "\"" + ","
                + "\"" + BENSON.getName() + "\"" + "," + "\"" + ELLE.getName() + "\"";
        ModelHelper.setFilteredList(expectedModel, DANIEL, BENSON, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with n/ + p/ prefix (only 1 name) -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + "\"" + DANIEL.getName() + "\"" + " p/" + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with n/ + p/ prefix (>= 2 names and p/ matches 1 of the names) -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + "\"" + DANIEL.getName() + "\"" + ","
                + "\"" + BENSON.getName() + "\"" + "," + "\"" + ELLE.getName() + "\"" + " p/" + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with n/ + p/ prefix (>= 2 names and p/ does not match any names) -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + "\"" + DANIEL.getName() + "\"" + ","
                + "\"" + BENSON.getName() + "\"" + "," + "\"" + ELLE.getName() + "\""
                + " p/" + GEORGE.getPhone().value;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with p/ + e/ + u/ prefix (1 value per field - all correct) -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " p/" + DANIEL.getPhone().value + " "
                + " e/" + DANIEL.getEmail().value + " "
                + " u/" + DANIEL.getUniversity().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with n/ + p/ + e/ a/ prefix (1 value per field - one incorrect) -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + DANIEL.getName() + " " + " p/" + DANIEL.getPhone().value
                + " e/" + GEORGE.getEmail().value + " a/" + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + "\"" + DANIEL.getAddress().value + "\"";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 0 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        executeCommand(command);
        String expectedResultMessage = FindResults.getInstance().getTextResults();
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
