package gui;

import be.Song;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MPController implements Initializable {

    private MPModel model;

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
    public TableView<Song> songTable;
    public TableColumn<Song, String> titleColumn;
    public TableColumn<Song, String> artistColumn;
    public TableColumn<Song, String> categoryColumn;
    public TableColumn<Song, Double> timeColumn;
    private int currentSong;
    private MediaPlayer mediaPlayer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model = MPModel.getInstance();
        ObservableList<Song> data = null;
        try {
            data = model.returnSongList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songTitle")); //connects data with table view
        artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("category"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Song, Double>("duration"));
        songTable.setItems(data);

        try {
            searchSong();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        songTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Song selectedSong = newSelection;
                System.out.println("Selected Song: " + selectedSong.getSongTitle());
                setMediaPlayer(selectedSong);
            }
            //TODO: After edit, a song is selected, why???
        });
    }

    public void setMediaPlayer(Song song) {
        //If a song was playing, stop it
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String path = song.getPath();
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnPlaying(this::updateCurrentlyPlayingSong);
        mediaPlayer.setOnPaused(this::updateCurrentlyPlayingSong);
    }

    public void playPauseAction(ActionEvent actionEvent) {
        System.out.println("Play/Pause button clicked");
        // Get the selected song from the table

        if (mediaPlayer != null) {
            // Play or pause the selected song based on its path
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                System.out.println("Pausing Song");
                mediaPlayer.pause();
            } else {
                System.out.println("Playing Song");
                mediaPlayer.play();
            }
        } else {
            System.out.println("No song selected");
        }

    }

    private void updateCurrentlyPlayingSong() {
        if (mediaPlayer != null) {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            System.out.println(status);
            String labelText;

            if (status == MediaPlayer.Status.PLAYING || status == MediaPlayer.Status.READY) {
                labelText = "Now Playing: " + getSongTitleFromPath(mediaPlayer.getMedia().getSource());
            } else if (status == MediaPlayer.Status.PAUSED) {
                labelText = "Paused: " + getSongTitleFromPath(mediaPlayer.getMedia().getSource());
            } else {
                labelText = "No song playing";
            }

            currentlyPlayingSong.setText(labelText);
        }
    }

    private String getSongTitleFromPath(String path) {
        File file = new File(path);

        // Get the name of the file (song title) without the extension
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf(".");

        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        } else {
            return fileName; // If there's no extension, return the whole name
        }

    }


    public void searchSong() throws SQLException {
        FilteredList<Song> filteredData = new FilteredList<>(model.returnSongList(), e -> true);

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(song -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all songs if the search field is empty
                }

                // Customize the conditions based on your search criteria
                String lowerCaseFilter = newValue.toLowerCase();
                return song.getSongTitle().toLowerCase().contains(lowerCaseFilter)
                        || song.getArtist().toLowerCase().contains(lowerCaseFilter)
                        || song.getCategory().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Wrap the FilteredList in a SortedList
        SortedList<Song> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(songTable.comparatorProperty());

        // Add sorted (and filtered) data to the table
        songTable.setItems(sortedData);
    }


    public void next() {

    }

    public void previous() {

    }


    public void playPreviousSong(ActionEvent actionEvent) {
    }

    public void playSongPause(ActionEvent actionEvent) {
    }

    public void platNextSong(ActionEvent actionEvent) {
    }

    public void addNewSong(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewSong.fxml"));
        Parent root = loader.load();

        NewSongController newSongController = loader.getController();
        newSongController.setMPController(this);

        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void updateTable() throws SQLException {
        ObservableList<Song> data = model.returnSongList();
        // Update TableView with the latest data...
    }


    public void addNewPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewPlaylist.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void deletePlaylist(ActionEvent actionEvent) {

    }

    public void deleteSongInPlaylist(ActionEvent actionEvent) {
    }

    public void deleteSongs(ActionEvent actionEvent) throws SQLException {
        ObservableList<Song> selectedSongs = songTable.getSelectionModel().getSelectedItems();

        if (!selectedSongs.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure that you want to delete the selected song?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                model.deleteSong(selectedSongs);
                updateTable();
            }
        }

    }

    public void closeApplication(ActionEvent actionEvent) throws SQLException {
        updateTable();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void moveSongsToPlaylists(ActionEvent actionEvent) {
    }


    public void moveSongsUp(ActionEvent actionEvent) {
    }

    public void moveSongsDown(ActionEvent actionEvent) {
    }

    public void editExistingPlaylist(ActionEvent actionEvent) {
    }

    public void editExistingSong(ActionEvent actionEvent) throws IOException {
        // Get the selected song from the table
        Song selectedSong = songTable.getSelectionModel().getSelectedItem();

        if (selectedSong != null) {
            // Open the NewSong window for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewSong.fxml"));
            Parent root = loader.load();

            NewSongController newSongController = loader.getController();
            newSongController.setMPController(this);
            newSongController.setEditMode(true); // Set edit mode to true
            newSongController.setSongToEdit(selectedSong); // Pass the selected song

            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();


        }
    }


}
