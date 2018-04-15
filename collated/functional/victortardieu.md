# victortardieu
###### \java\seedu\address\logic\commands\ClearAccountCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;


/**
 * Checks if user is a librarian. If yes, it clears the list of accounts and logs out the current account.
 */
public class ClearAccountCommand extends UndoableCommand {

    /**
     *
     */
    public static final String COMMAND_WORD = "cleara";
    public static final String MESSAGE_SUCCESS = "AccountList has been cleared, and you are logged out!";
    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        UniqueAccountList blankList = new UniqueAccountList();
        try {
            blankList.add(Account.createDefaultAdminAccount());
        } catch (DuplicateAccountException e) {
            e.printStackTrace();
        }
        model.resetAccount(blankList);
        model.logout();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public PrivilegeLevel getPrivilegeLevel() {

        return PRIVILEGE_LEVEL;
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    /**
     * @param myString
     * @return auto, the string that holds the autocomplete string of the chosen command
     */
    public static String autoComplete(String myString) {
        /**
         *  The auto string will hold the autocomplete string of the chosen command
         */
        String auto = "";
        switch (myString) {
        case AddCommand.COMMAND_WORD:
            auto = "add t/ a/ i/ av/ tag/ ";
            break;
        case AddAccountCommand.COMMAND_WORD:
            auto = "addAccount n/ m/ u/ p/ l/ ";
            break;
        case EditCommand.COMMAND_WORD:
            auto = "edit 1 t/ a/ i/ av/ tag/ ";
            break;
        case DeleteCommand.COMMAND_WORD:
            auto = "delete 1";
            break;
        case BorrowCommand.COMMAND_WORD:
            auto = "borrow 1";
            break;
        case ReturnCommand.COMMAND_WORD:
            auto = "return 1";
            break;
        case ReserveCommand.COMMAND_WORD:
            auto = "reserve 1";
            break;
        default:
            auto = myString;
        }
        return auto;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = catalogueParser.parseCommand(commandText);
            if (!isPrivileged(command)) {
                return new CommandResult(Command.MESSAGE_UNPRIVILEGED);
            }
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Book> getFilteredBookList() {
        return model.getFilteredBookList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
```
###### \java\seedu\address\ui\BookCard.java
``` java
package seedu.address.ui;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.book.Book;


/**
 * An UI component that displays information of a {@code Book}.
 */
public class BookCard extends UiPart<Region> {

    private static final String FXML = "BookListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Catalogue</a>
     */

    public final Book book;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label id;
    @FXML
    private Label isbn;
    @FXML
    private Label avail;
    @FXML
    private FlowPane tags;


    public BookCard(Book book, int displayedIndex) {
        super(FXML);
        this.book = book;
        id.setText(displayedIndex + ". ");
        title.setText(book.getTitle().fullTitle);
        author.setText(book.getAuthor().value);
        isbn.setText(book.getIsbn().value);
        avail.setText(book.getAvail().toString());
        colorTags(book);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookCard)) {
            return false;
        }

        // state check
        BookCard card = (BookCard) other;
        return id.getText().equals(card.id.getText())
            && book.equals(card.book);
    }

    private String getTagColor() {
        Random rand = new Random();
        int sCase = rand.nextInt(10);
        switch (sCase) {
        case 0:
            return "-fx-background-color: blue;";
        case 1:
            return "-fx-background-color: green;";
        case 2:
            return "-fx-background-color: red;";
        case 3:
            return "-fx-background-color: maroon;";
        case 4:
            return "-fx-background-color: orange;";
        case 5:
            return "-fx-background-color: violet;";
        case 6:
            return "-fx-background-color: brown;";
        case 7:
            return "-fx-background-color: khaki;";
        case 8:
            return "-fx-background-color: olive;";
        case 9:
            return "-fx-background-color: indigo;";
        default:
            return "-fx-background-color: pink;";
        }

    }

    /**
     * Assign a new color to each new tag
     *
     * @param book
     */

    private void colorTags(Book book) {
        book.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tags.getChildren().add(tagLabel);
            tagLabel.setStyle(getTagColor());
        });
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case TAB:
            keyEvent.consume();
            replaceText(LogicManager.autoComplete(commandTextField.getText()));
            break;
```
