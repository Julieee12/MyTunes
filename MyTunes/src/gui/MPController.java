package gui;

import be.Playlist;
import be.Song;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.util.Duration;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class MPController implements Initializable {

    private MPModel model;

    @FXML
    private Button prevsong;
    @FXML
    private Button nextsong;
    @FXML
    private Button newsong;
    @FXML
    private Button playPause;
    @FXML
    private Label currentlyPlayingSong;
    @FXML
    private ListView songsInPlaylist;
    @FXML
    private TextField searchInput;
    @FXML
    private Button newPlaylist;
    @FXML
    private Button deletePlaylistID;
    @FXML
    private Button deleteSongPlaylistID;
    @FXML
    private Button deleteSongsID;
    @FXML
    private Button close;
    @FXML
    private Button moveSongs;
    @FXML
    private Button searchbutton;
    @FXML
    private Button moveUp;
    @FXML
    private Button moveDown;
    @FXML
    private Button editPlaylist;
    @FXML
    private Button editSong;

    //region @FXML TableView and Columns
    @FXML
    private TableView<Playlist> playlistTable;
    @FXML
    private TableColumn <Playlist, String> columnPlaylistName;
    @FXML
    private TableColumn <Playlist, Integer> columnSongCount;
   @FXML
   private TableColumn <Playlist, String> columnTotalTime;
    @FXML
    private TableView<Song> songTable;
    @FXML
    private TableColumn<Song, String> titleColumn;
    @FXML
    private TableColumn<Song, String> artistColumn;
    @FXML
    private TableColumn<Song, String> categoryColumn;
    @FXML
    private TableColumn<Song, Double> timeColumn;
    private int currentSong;
    private MediaPlayer mediaPlayer;
    private Slider songSlider;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model = MPModel.getInstance();
        ObservableList<Song> data;
        try {
            updateTable();
            data = model.returnSongList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

        //shows songs for the selected playlist
        playlistTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateSongsInPlaylistTable(newSelection);
            }
        });

        //plays selected song from songs in playlist
        songsInPlaylist.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Selected Song: " + newSelection);
                for (Song song : data) {
                    if (song.getSongTitle().equals(newSelection.toString())) {
                        setMediaPlayer(song);
                    }
                }
            }
        });


        //IDEA:
        /*
        volumeSlider.getValueProperty().addlisten(
                mediaPlayer.setVolume(newValue);
        )
         */

    }



    public void setMediaPlayer(Song song) {
        //If a song was playing, stop it
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String path = song.getPath();
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(this::updateCurrentlyPlayingSong);
        mediaPlayer.setOnPlaying(this::updateCurrentlyPlayingSong);
        mediaPlayer.setOnPaused(this::updateCurrentlyPlayingSong);

        // Update slider value based on current time
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                System.out.println(newValue.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
                songSlider.setValue(newValue.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
            });
        });

        mediaPlayer.setOnEndOfMedia(this::playNextSong);
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

    private void updateCurrentlyPlayingSong() { //currentlyPlayingSong is a label, displaying what song is playing
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


    public void selectNextSong() {
        if (currentSong < songTable.getItems().size() - 1) {
            currentSong++;
            Song selectedSong = songTable.getItems().get(currentSong);
            songTable.getSelectionModel().select(selectedSong);
        }
        //TOOO: Add 'else' to switch to first song
    }

    public void selectPreviousSong() {
        if (currentSong > 0) {
            currentSong--;
            Song selectedSong = songTable.getItems().get(currentSong);
            songTable.getSelectionModel().select(selectedSong);
        }
    }

    public void playPreviousSong(ActionEvent actionEvent) {
        selectPreviousSong();
        mediaPlayer.play(); //Having known that when a song is selected, a media player is created for it (event listener)
    }

    public void playNextSong() { //When Next button is clicked (but ActionEvent parameter is omitted, as it's not necessary
        selectNextSong();
        mediaPlayer.play();
    }

    public void handleSliderDragDetected(MouseEvent event) {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            }
            // Also stop the slider from updating during drag
            // songSlider.setDisable(true);
        }
    }

    public void handleSliderDragDone(MouseEvent event) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            // Calculate the new time based on the slider value
            double currentTimeMillis = songSlider.getValue() / 100 * mediaPlayer.getTotalDuration().toMillis();
            Duration seekTime = Duration.millis(currentTimeMillis);

            // Seek to the new time
            mediaPlayer.seek(seekTime);
            mediaPlayer.play();
        }
        // Enable the slider after dragging is done
        // songSlider.setDisable(false);
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
        ObservableList<Song> data = null;
        ObservableList<Playlist> playlistData = null;
        try {
            data = model.returnSongList();
            playlistData = model.returnPlaylist();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songTitle")); //connects song data with song table view
        artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("category"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Song, Double>("SongDurationString"));
        songTable.setItems(data);

        columnPlaylistName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("playlistName"));
        columnSongCount.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("songCount"));
        columnTotalTime.setCellValueFactory(new PropertyValueFactory<Playlist, String>("totalTime"));
        playlistTable.setItems(playlistData);
        // Update TableView with the latest data...
    }
    public void addNewPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewPlaylist.fxml"));
        Parent root = loader.load();

        NewPLController newPLController = loader.getController();
        newPLController.setMPController(this);

        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void deletePlaylist(ActionEvent actionEvent) throws SQLException {
        ObservableList<Playlist> selectedPlaylists = playlistTable.getSelectionModel().getSelectedItems();

        if (!selectedPlaylists.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure that you want to delete the selected playlist?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                model.deletePlaylists(selectedPlaylists);
                updateTable();
            }
        }
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
    private void updateSongsInPlaylistTable(Playlist playlist) {

        ObservableList<String> songTitles = FXCollections.observableArrayList();

        for (Song song : playlist.getAllsongs()) {
            songTitles.add(song.getSongTitle());
        }

        songsInPlaylist.setItems(songTitles);
    }

    public void moveSongsToPlaylists(ActionEvent actionEvent) throws SQLException {
        // Get the selected song from the main song library table
        Song selectedSong = songTable.getSelectionModel().getSelectedItem();

        // Get the selected playlist from the playlist table
        Playlist selectedPlaylist = playlistTable.getSelectionModel().getSelectedItem();

        if (selectedSong == null || selectedPlaylist == null) {
            //  alert to the user
            showAlert("Please select a song and a playlist to move them.");
            return;
        }

        // Adds the copied song to the selected playlist
        //selectedPlaylist.addSong(copiedSong);

        try {
            model.addSongToPlaylist(selectedPlaylist, selectedSong);
        } catch (Exception e ) {}

        // Updates the songs in the playlist table dynamically
        updateSongsInPlaylistTable(selectedPlaylist);
        updateTable();
        // Show a success message to the user
        showAlert("Song moved to the playlist successfully!");
    }

    // Method to show an alert to the user
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void moveSongsUp(ActionEvent actionEvent) throws SQLException {
        int selectedIndex = songsInPlaylist.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {

            ObservableList<String> songs = songsInPlaylist.getItems();
            Collections.swap(songs, selectedIndex, selectedIndex - 1);
            songsInPlaylist.setItems(songs);
            songsInPlaylist.getSelectionModel().select(selectedIndex - 1);
        }

    }

    public void moveSongsDown(ActionEvent actionEvent) throws SQLException {
        int selectedIndex = songsInPlaylist.getSelectionModel().getSelectedIndex();
        if (selectedIndex < songsInPlaylist.getItems().size() - 1) {

            ObservableList<String> songs = songsInPlaylist.getItems();
            Collections.swap(songs, selectedIndex, selectedIndex + 1);
            songsInPlaylist.setItems(songs);
            songsInPlaylist.getSelectionModel().select(selectedIndex + 1);
        }
    }

    public void editExistingPlaylist(ActionEvent actionEvent) throws IOException {
        // Get the selected playlist from the table
        Playlist selectedPlaylist = playlistTable.getSelectionModel().getSelectedItem();

        if (selectedPlaylist != null) {
            // Open the EditPlaylist window for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewPlaylist.fxml"));
            Parent root = loader.load();


            NewPLController newPLController = loader.getController();
            newPLController.setMPController(this);
            newPLController.setEditModePlaylist(true); // Set edit mode to true
            newPLController.setPlaylistToEdit(selectedPlaylist); // Pass the selected playlist

            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
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


    public void removeSongFromPlaylist(ActionEvent actionEvent) {
        Playlist selectedPlaylist = playlistTable.getSelectionModel().getSelectedItem();
        String selectedSongTitle = (String) songsInPlaylist.getSelectionModel().getSelectedItem();

        if (selectedPlaylist != null && selectedSongTitle != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure that you want to remove the selected song from the playlist?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Song selectedSong = selectedPlaylist.findSongByTitle(selectedSongTitle);
                if (selectedSong != null) {
                    try {
                        model.deleteSongsFromPlaylist(selectedPlaylist, Collections.singletonList(selectedSong));
                        updateSongsInPlaylistTable(selectedPlaylist);
                        updateTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("Error removing song from the playlist.");
                    }
            }   }
        } else {
            showAlert("Please select a playlist and a song from the playlist to remove.");
        }
    }
}
