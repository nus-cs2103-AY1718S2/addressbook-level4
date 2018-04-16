# jill858
###### \java\seedu\address\commons\util\DateUtil.java
``` java
    /**
     * Get the date of the month given the date
     * @param date a date in LocalDate format
     * @return date of the month
     */
    public static int getDateOfMonth(LocalDate date) {
        return date.getDayOfMonth();
    }

    /**
     * Get the month given the date
     * @param date a date in LocalDate format
     * @return the month
     */
    public static String getMonthOfDate(LocalDate date) {
        Locale locale = Locale.getDefault();
        Month month  = date.getMonth();
        return month.getDisplayName(TextStyle.SHORT, locale);
    }

    /**
     * Get the year of the given date
     * @param date a date in LocalDate format
     * @return the year
     */
    public static int getYearOfDate(LocalDate date) {
        return date.getYear();
    }

    /**
     * Format date in terms of dd-MMM-yyy, eg 18-Apr-2018
     * @param date a date in String format
     * @return date in terms of dd-MMM-yyyy
     */
    public static String dateFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        LocalDate localDate = getParsedDate(date);

        return formatter.format(localDate);
    }

}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Joins the words together by space
     * @param args List of String arguement
     * @return words join with space
     */
    public static String concatenateStringWithSpace(String... args) {
        StringJoiner sj = new StringJoiner(" ");

        for (String word : args) {
            sj.add(word);
        }

        return sj.toString();
    }
}
```
###### \java\seedu\address\logic\commands\ConvertCommand.java
``` java
/**
 * Converts any amount from one currency to another
 */
public class ConvertCommand extends Command {

    public static final String COMMAND_WORD = "convert";
    public static final String COMMAND_ALIAS = "cv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts an amount from one currency to another\n"
            + "Example: " + COMMAND_WORD + " 1 SGD USD\n"
            + "Sample currency code: \n"
            + "AUD - Australian dollar\n"
            + "CAD - Canadian dollar\n"
            + "CNY - Renminbi (Chinese) yuan\n"
            + "HKD - Hong Kong dollar\n"
            + "JPY - Japanese yen\n"
            + "MYR - Malaysian ringgit\n"
            + "SGD - Singapore dollar\n"
            + "TWD - New Taiwan dollar\n"
            + "USD - United States dollar\n";

    public static final String MESSAGE_COMPLETE = "Converted %s %,.2f to %s %,.2f as of %s";

    private Currency currency = new Currency();

    private String fromCurrencyCode;
    private String toCurrencyCode;
    private final double value;

    public ConvertCommand(String fromCurrencyCode, String toCurrencyCode, double value) {
        this.fromCurrencyCode = fromCurrencyCode;
        this.toCurrencyCode = toCurrencyCode;
        this.value = value;
    }

    @Override
    public CommandResult execute() {

        double convertedValue = currency.convert(fromCurrencyCode, toCurrencyCode, value);

        String dateString = currency.getDate();
        String dateFormat = DateUtil.dateFormatter(dateString);

        return new CommandResult(String.format(MESSAGE_COMPLETE,
                fromCurrencyCode, value, toCurrencyCode, convertedValue, dateFormat));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConvertCommand that = (ConvertCommand) o;
        return Double.compare(that.value, value) == 0;
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public FindCommand(Predicate<Person> predicate) {
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
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\ConvertCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ConvertCommand object
 */
public class ConvertCommandParser implements Parser<ConvertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ConvertCommand
     * and returns an ConvertCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConvertCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        double value;
        String fromCurrencyCode;
        String toCurrencyCode;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        }

        String[] currencyKeywords = trimmedArgs.split("\\s+");

        try {
            if (currencyKeywords.length == 2) {
                //converting base rate
                value = 1;
                fromCurrencyCode = currencyKeywords[0].toUpperCase();
                toCurrencyCode = currencyKeywords[1].toUpperCase();
            } else {
                //converting some value
                value = Double.parseDouble(currencyKeywords[0]);
                fromCurrencyCode = currencyKeywords[1].toUpperCase();
                toCurrencyCode = currencyKeywords[2].toUpperCase();
            }
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        } catch (ArrayIndexOutOfBoundsException aie) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        }

        boolean isValidFromCurrencyCode = new Currency().containsCurrencyCode(fromCurrencyCode);
        boolean isValidToCurrencyCode = new Currency().containsCurrencyCode(toCurrencyCode);

        if (isValidFromCurrencyCode && isValidToCurrencyCode) {
            return new ConvertCommand(fromCurrencyCode, toCurrencyCode, value);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        }

    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
        if (!isValidPrefixFormat(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String targetPrefix = getPrefix(trimmedArgs);

        if (isEmptyKeywords(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = getKeywords(trimmedArgs);

        if (targetPrefix.equals(PREFIX_NAME.getPrefix())) {
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (targetPrefix.equals(PREFIX_TAG.getPrefix())) {
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (targetPrefix.equals(PREFIX_ADDRESS.getPrefix())) {
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (targetPrefix.equals(PREFIX_PHONE.getPrefix())) {
            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Check if valid prefix format is provided by the user
     * @param args command line input by the user
     * @return true if it is a valid prefix provided by the user
     */
    private static boolean isValidPrefixFormat(String args) {
        return args.length() > 2;
    }

```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Extract the search type
     * @param args command line input
     * @return type of search field
     */
    private static String getPrefix(String args) {
        return args.substring(0, 2);
    }

```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Check if there is keyword input provided by the user
     * @param keywords keywords input by the user
     * @return true is there is no keywords entered by the user
     */
    private static boolean isEmptyKeywords(String keywords) {
        return (keywords.substring(2)).length() == 0;
    }

```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Extract keywords out from the command input
     * @param args command line input
     * @return list of keywords
     */
    private static String[] getKeywords(String args) {
        String removePrefixKeywords = args.substring(2);

        String trimKeywords = removePrefixKeywords.trim();

        return trimKeywords.split("\\s+");
    }



}
```
###### \java\seedu\address\model\Currency.java
``` java
/**
 * Represent currencies and its rate
 */
public class Currency {

    private static final String API_PROVIDIER = "http://api.fixer.io/latest?base=";

    private String base;
    private String date;

    private Map<String, String> rates = new TreeMap<String, String>();
    private List<String> currencyCodes = Arrays.asList("SGD",
            "AUD", "CAD", "CHF", "CNY", "GBP",
            "HKD",  "ILS", "INR", "JPY", "MYR",
            "NZD", "PHP", "SEK", "THB", "TWD",
            "USD");

    public Currency() {
        this.date = "2018-04-13";
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    /**
     * Convert from source currency to the the destination currency given the value
     * Uses API:Fixer to get rates instantly
     * If API is unavailable, rates given are fixed by developer
     *
     * @param fromCurrencyCode Source currency code
     * @param toCurrencyCode Destination currency code
     * @param value Value to be converted
     * @return value in terms of the destination currency code
     */
    public double convert(String fromCurrencyCode, String toCurrencyCode, double value) {
        double convertedValue;

        Currency currencyResponse = getResponse(API_PROVIDIER + fromCurrencyCode);

        try {
            String rate = currencyResponse.getRates().get(toCurrencyCode);

            double conversionRate = Double.valueOf(rate);

            convertedValue = conversionRate * value;

            this.date = currencyResponse.getDate();

            return convertedValue;

        } catch (NullPointerException npe) {
            return convertByFixedValue(fromCurrencyCode, toCurrencyCode, value);
        }

    }

    /**
     * Convert from source currency to the destination currency given the value
     * Rates are fixed
     * @param fromCurrencyCode Source currency code
     * @param toCurrencyCode Destination currency code
     * @param value Value to be converted
     * @return
     */
    public double convertByFixedValue(String fromCurrencyCode, String toCurrencyCode, double value) {
        double convertedValue;

        if (fromCurrencyCode.equals("SGD")) {
            //rate is already in the form of base rate
            //therefore multiply to the rate to be converted
            double rate = getCurrencyRate(toCurrencyCode);

            convertedValue = value * rate;
        } else {
            //convert the value to the base rate (SGD)
            //followed by multiplying the rate to be converted
            double fromRate = getCurrencyRate(fromCurrencyCode);
            double toRate = getCurrencyRate(toCurrencyCode);

            double toSgd = value / fromRate;
            convertedValue = toSgd * toRate;
        }

        return convertedValue;
    }

    /**
     * Response from the API
     * @param strUrl API URL string
     * @return Currency object
     */
    private static Currency getResponse(String strUrl) {

        Currency currencyResponse = null;

        JsonUtil json = new JsonUtil();
        StringBuffer sb = new StringBuffer();

        if (strUrl == null || strUrl.isEmpty()) {
            return null;
        }

        URL url;
        try {
            url = new URL(strUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();

            int data = stream.read();

            while (data != -1) {

                sb.append((char) data);

                data = stream.read();
            }

            stream.close();

            currencyResponse = json.fromJsonString(sb.toString(), Currency.class);

        } catch (MalformedURLException e) {
            return null;

        } catch (IOException e) {
            return null;
        }

        return currencyResponse;
    }

    /**
     * Check the currency code given contains in the list of currency codes
     * @param currencyCode
     * @return true is a given currency code is found in the list
     */
    public boolean containsCurrencyCode(String currencyCode) {
        boolean isCurrencyCode = currencyCodes.stream().anyMatch(code -> code.equals(currencyCode));

        return isCurrencyCode;
    }

    /**
     * Retrieve the rate of the currency given the currency code
     * The default based rate is in SGD (Singapore Dollar)
     * Rate as of 13/04/2018
     *
     * @param currencyCode
     * @return rate of the currency code
     */
    public double getCurrencyRate(String currencyCode) {

        switch (currencyCode) {

        //Singapore Dollar
        case "SGD":
            return 1.000;

        //Australian dollar
        case "AUD":
            return 0.977;

        //Canadian dollar
        case "CAD":
            return 0.958;

        //Swiss franc
        case "CHF":
            return 0.733;

        //Renminbi (Chinese) yuan
        case "CNY":
            return 4.787;

        //Pound sterling
        case "GBP":
            return 0.534;

        //Hong Kong dollar
        case "HKD":
            return 5.983;

        //Israeli new shekel
        case "ILS":
            return 2.680;

        //Indian rupee
        case "INR":
            return 49.707;

        //Japanese yen
        case "JPY":
            return 82.089;

        //Malaysian ringgit
        case "MYR":
            return 2.953;

        //New Zealand dollar
        case "NZD":
            return 1.033;

        //Philippine piso
        case "PHP":
            return 39.590;

        //Swedish krona/kronor
        case "SEK":
            return 6.424;

        //Thai baht
        case "THB":
            return 23.745;

        //New Taiwan dollar
        case "TWD":
            return 22.285;

        //United States dollar
        case "USD":
            return 0.762;

        default: return 1;
        }
    }
}
```
###### \java\seedu\address\model\person\AddressContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\tag\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String stringifyTags = Joiner.on(" ").join(person.getTags());
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(stringifyTags, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
