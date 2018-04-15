# neilish3re
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "order";
    public static final String COMMAND_ALIAS = "o";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all the coins the user has input into the Coin Book as a list"
            + " sorted by lexicographical order of cryptocurrency coin name\n";

    public static final String MESSAGE_SUCCESS = "Sorted all coins lexicographically";

    private final boolean isSort;

    public SortCommand(boolean isSort) {
        this.isSort = isSort;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortCoinList(isSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses sortOrder and returns it
     * Throws IllegalValueException if index that is specified is invalid
     */
    public static boolean parseSort(String sortOrder) throws IllegalValueException {
        sortOrder = sortOrder.trim();
        switch (sortOrder) {
        case "":
        case "a":                       //ascending alphabetical order
            return false;
        case "z":
            return true;                //descending alphabetical order
        default:
            throw new IllegalValueException(MESSAGE_INVALID_ARG);
        }
    }
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java

/** SortCommand Parser is implemented from Parser
 *
 */
public class SortCommandParser implements Parser<SortCommand> {
    /** Parses given arguments in SortCommand context and returns sortCommand object to execute
     * throws an exception(ParseException) if user input format is invalid
     */

    public SortCommand parse(String args) throws ParseException {
        try {
            boolean isSort = ParserUtil.parseSort(args);
            return new SortCommand(isSort);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}

```
###### \java\seedu\address\model\coin\UniqueCoinList.java
``` java

    /**
     * Sorts the coins using compareTo
     * @param isSort
     */
    public void sort(boolean isSort) {
        if (isSort) {
            internalList.sort((coin1, coin2) -> (coin2.getCode().fullName.compareTo(coin1.getCode().fullName)));
        } else {
            internalList.sort((coin1, coin2) -> (coin1.getCode().fullName.compareTo(coin2.getCode().fullName)));
        }
    }
```
###### \java\seedu\address\model\CoinBook.java
``` java

    public void sortCoinBook(boolean isSort) {
        coins.sort(isSort);
    }
    //@@ author

```
###### \java\seedu\address\model\Model.java
``` java
    void sortCoinList(boolean isSort);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortCoinList(boolean isSort) {
        coinBook.sortCoinBook(isSort);
    }

```
