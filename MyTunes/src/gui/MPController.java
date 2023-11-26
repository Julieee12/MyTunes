package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MPController {

    public Button prevsong;
    public Button nextsong;
    public Button newsong;
    public Button playPause;
    public Label currentlyPlayingSong;
    public ListView songsInPlaylist;
    public TextField searchInput;
    public Button newPlaylist;
    public Button deletePlaylistID;
    public Button deleteSongPlaylistID;
    public Button deleteSongsID;
    public Button close;
    public Button moveSongs;
    public Button searchbutton;
    public Button moveUp;
    public Button moveDown;
    public Button editPlaylist;
    public Button editSong;
    public TableView playlistTable;
    public TableView songTable;

    private int currentSong;

    public void pause(){

    }
    public void play(){

    }
    public void next(){

    }
    public void previous(){

    }


    public void playPreviousSong(ActionEvent actionEvent) {
    }

    public void playSongPause(ActionEvent actionEvent) {
    }

    public void platNextSong(ActionEvent actionEvent) {
    }

    public void addNewSong(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("NewSong.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void addNewPlaylist(ActionEvent actionEvent) {
    }

    public void deletePlaylist(ActionEvent actionEvent) {
    }

    public void deleteSongInPlaylist(ActionEvent actionEvent) {
    }

    public void deleteSongs(ActionEvent actionEvent) {
    }

    public void closeApplication(ActionEvent actionEvent) {
    }

    public void moveSongsToPlaylists(ActionEvent actionEvent) {
    }

    public void searchSong(ActionEvent actionEvent) {
    }

    public void moveSongsUp(ActionEvent actionEvent) {
    }

    public void moveSongsDown(ActionEvent actionEvent) {
    }

    public void editExistingPlaylist(ActionEvent actionEvent) {
    }

    public void editExistingSong(ActionEvent actionEvent) {
    }
}
