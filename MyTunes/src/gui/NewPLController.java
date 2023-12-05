package gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewPLController implements Initializable {

    public Button cancelChanges;
    public Button saveChanges;
    public TextField nameInput;
    private MPController mpController;
    private MPModel model;
    private Stage stage;


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
        model.createPlaylist(playlistName);

        mpController.updateTable();

        //Close the stage
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.close();
    }




}
