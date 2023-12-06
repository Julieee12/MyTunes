package bll;

import be.Playlist;
import be.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistManager {
    private static ObservableList <Playlist> playlistList = FXCollections.observableArrayList();

    public void createSinglePlaylist (String playlistName){ //creates single playlist
        Playlist newPlaylist = new Playlist(playlistName);
        playlistList.add(newPlaylist);
    }

    public ObservableList<Playlist> returnPlaylist() {
        return playlistList;
    }

    public void deleteSelectedPlaylists(ObservableList<Playlist> playlistsToDelete) throws SQLException {
        for (Playlist playlist : playlistsToDelete) {
            playlistList.remove(playlist);
        }
    }




}
