package gui;

import be.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewPLController implements Initializable {

    @FXML
    private Button cancelChanges;
    @FXML
    private Button saveChanges;
    @FXML
    private TextField nameInput;
    @FXML
    private MPController mpController;
    private static MPModel model;
    @FXML
    private Stage stage;

    private boolean editModePlaylist = false;
    private Playlist playlistToEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model = MPModel.getInstance();
        this.stage = new Stage();

    }
    public void setMPController(MPController mpController) {this.mpController = mpController;
    }

    public void cancelPlaylist(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void savePlaylist(ActionEvent event) throws SQLException {
        String playlistName = nameInput.getText();
        if (editModePlaylist && playlistToEdit != null) {
            // Editing an existing playlist
            playlistToEdit.setPlaylistName(playlistName);
            model.updatePlaylist(playlistToEdit);
        } else {
            // Creating a new playlist
            model.createSinglePlaylist(playlistName);
        }

        mpController.updateTable();

        // Close the stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setEditModePlaylist(boolean editMode) {
        this.editModePlaylist = editMode;
    }

    public void setPlaylistToEdit(Playlist selectedPlaylist) {
        this.playlistToEdit = selectedPlaylist;

        // Set the existing playlist details in the UI for editing
        if (playlistToEdit != null) {
            nameInput.setText(playlistToEdit.getPlaylistName());
        }
    }





}
