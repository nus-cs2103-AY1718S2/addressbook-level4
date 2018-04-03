package seedu.address.ui;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PasswordCorrectEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.logic.ListElementPointer;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;

//@@author yeggasd
/**
 * The UI component that is responsible for receiving user password inputs.
 */
public class PasswordBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "PasswordBox.fxml";

    private final Logger logger = LogsCenter.getLogger(PasswordBox.class);
    private ListElementPointer historySnapshot;
    private final Storage storage;
    private final Model model;

    @FXML
    private PasswordField passwordTextField;

    public PasswordBox(Storage storage, Model model) {
        super(FXML);

        this.storage = storage;
        this.model = model;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        passwordTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        default:
            // let JavaFx handle the keypress
        }
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handlePasswordInputChanged() {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        String input = passwordTextField.getText();
        try {
            passwordTextField.setText("");
            addressBookOptional = storage.readAddressBook(new Password(input));
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            model.resetData(initialData);
            raise(new PasswordCorrectEvent());
        } catch (WrongPasswordException e) {
            logger.warning("Wrong password used. Trying again.");
            setStyleToIndicateCommandFailure();
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }
    }

    /**
     * Sets the password box style to use the default style.
     */
    private void setStyleToDefault() {
        passwordTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the password box style to indicate a wrong password.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = passwordTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
