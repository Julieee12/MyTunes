package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class NewSongController implements Initializable {

    private MPModel model;
    private MPController mpController;

    private Stage stage;
    public TextField titlefield;
    public TextField artistfield;
    public TextField timeField;
    public TextField fileField;
    public Button choose;
    public Button save;
    public Button cancel;
    public Button more;
    public ChoiceBox<String> categoryBox;
    private String[] categories = {"Pop", "Rap", "Jazz", "Rock"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryBox.getItems().addAll(categories);
        this.model = MPModel.getInstance();
        this.stage = new Stage();

        //other initialization code
        more.setOnAction(event -> addNewCategory());

    }

    public void setMPController(MPController mpController) {this.mpController = mpController;
    }


    public void chooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser(); //creating instance of fileChooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        File selectedFile = fileChooser.showOpenDialog(stage); //shows open dialog

        if (selectedFile != null) { //if file is selected add it to textField
            fileField.setText(selectedFile.getAbsolutePath());
        }
    }

    public void more() {
        addNewCategory();

    }


    private void addNewCategory() {
        //Adds the new window where you set the category
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Category");
        dialog.setHeaderText("Enter a new category:");
        dialog.setContentText("Category:");

        // Waits for the user to type something
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newCategory -> {
            // Add the new category to the list
            categoryBox.getItems().add(newCategory);
            categoryBox.setValue(newCategory);
        });
    }



        public void saveSong (ActionEvent actionEvent) throws SQLException { //once save is clicked input will be saved also for the song
            String title = titlefield.getText();
            String artist = artistfield.getText();
            String category = categoryBox.getValue();
            Double time = Double.parseDouble(timeField.getText());
            String path = fileField.getText();
            model.createSong(title, artist, category, time, path);
            mpController.updateTable(); // Notify MPController to update TableView

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();


        }


    public void cancelSong(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

