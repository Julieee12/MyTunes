package gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewSongController implements Initializable {
    public TextField titlefield;
    public TextField artistfield;
    public TextField timeField;
    public TextField fileField;
    public Button choose;
    public Button save;
    public Button cancel;
    public Button more;
    public ChoiceBox <String> categoryBox;
    private String [] categories = {"Pop", "Rap", "Jazz", "Rock"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryBox.getItems().addAll(categories);
    }



    public void choose(){

    }
    public void save(){

    }
    public void cancel(){

    }
    public void more(){

    }

    public void saveSong(ActionEvent actionEvent) { //once save is clicked input will be saved also for the song
        String title = titlefield.getText();
        String artist = artistfield.getText();
        String category = categoryBox.getValue();
        Double time = Double.parseDouble(timeField.getText());
        String path = fileField.getText();


    }


}
