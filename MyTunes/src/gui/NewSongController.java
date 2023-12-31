package gui;

import be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML
    private TextField titlefield;
    @FXML
    private TextField artistfield;
    @FXML
    private TextField timeField;
    @FXML
    private TextField fileField;
    @FXML
    private Button choose;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private Button more;
    @FXML
    private ChoiceBox<String> categoryBox;
    private String[] categories = {"Pop", "Rap", "Jazz", "Rock"};

    private boolean editMode = false; // Flag to indicate edit mode
    private Song songToEdit; // Store the song to edit

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryBox.getItems().addAll(categories);
        this.model = MPModel.getInstance();
        this.stage = new Stage();

        //other initialization code
        more.setOnAction(event -> addNewCategory());

    }

    public void setMPController(MPController mpController) {
        this.mpController = mpController;
    }


    public void chooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser(); //creating instance of fileChooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        File selectedFile = fileChooser.showOpenDialog(stage); //shows open dialog

        if (selectedFile != null) { //if file is selected add it to textField
            fileField.setText(selectedFile.getAbsolutePath());
        }
    }

    public void setSongToEdit(Song songToEdit) {
        this.songToEdit = songToEdit;

        // Set the existing song details in the UI for editing
        titlefield.setText(songToEdit.getSongTitle());
        artistfield.setText(songToEdit.getArtist());
        timeField.setText(String.valueOf(songToEdit.getDuration()));
        fileField.setText(songToEdit.getPath());
        categoryBox.setValue(songToEdit.getCategory());
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


    /**
     * Saves the song information provided by the user. It retrieves input values for title, artist, category,
     * duration, and file path. If in edit mode, it updates the existing song in the model; otherwise, it creates
     * a new song. After saving, it notifies the MPController to update the TableView and closes the stage.
     */
    public void saveSong (ActionEvent actionEvent) throws SQLException {
        String title = titlefield.getText();
        String artist = artistfield.getText();
        String category = categoryBox.getValue();
        String time = timeField.getText();
        String path = fileField.getText();

        //let user type time in String
        double duration = 0;
        String[] parts = time.split(":");
        if (parts.length == 2) {
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            duration = minutes * 60 + seconds;
        }
        // Create or update the song in the model
        if (editMode) {
            model.updateSong(songToEdit, title, artist, category, duration, path);
        } else {
            model.createSong(title, artist, category, duration, path);
        }
        mpController.updateTable();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void cancelSong(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
