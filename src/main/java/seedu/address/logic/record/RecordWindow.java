//@@author nhs-work
package seedu.address.logic.record;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.model.patient.Record;

/**
 * Class for initializing login popup GUI
 */
public class RecordWindow extends Application {

    @FXML
    private TextField dateField;

    @FXML
    private TextField symptomField;

    @FXML
    private TextField illnessField;

    @FXML
    private TextField treatmentField;

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private Record record;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Opens a new window and starts up the medical record form.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Medical Record Window");

        // Prevent the window resizing
        this.primaryStage.setResizable(false);

        //initRootLayout();

        showRecordForm();
    }

    /**
     * Overloaded method of start.
     */
    public void start(Stage primaryStage, Record record) {
        this.record = record;
        start(primaryStage);
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the medical record form
     */
    public void showRecordForm() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RecordLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RecordController rc = loader.<RecordController>getController();
            rc.initData(record);
            primaryStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
