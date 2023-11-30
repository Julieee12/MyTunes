package gui;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class NewPLController {

    public PasswordField nameLabel;
    public Button cancelChanges;
    public Button saveChanges;

    public void saveChanges(){

    }
    public void cancelChanges(){

    }

    public void cancelPlaylist(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void savePlaylist(ActionEvent event) {
    }
}
