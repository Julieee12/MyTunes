package bll;

import be.Playlist;
import be.Song;
import dal.IPlaylistDAO;
import dal.PlaylistDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {
    private static ObservableList <Playlist> playlistList = FXCollections.observableArrayList();
    private final IPlaylistDAO playlistDAO;

    public PlaylistManager(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }
    public void createPlaylist (String playlistName){ //creates single playlist
        Playlist newPlaylist = new Playlist(playlistName);
        playlistList.add(newPlaylist);
        playlistDAO.createPlaylist(newPlaylist);
    }

    public ObservableList<Playlist> returnPlaylist() throws SQLException {
        loadSongs();
        return playlistList;
    }

    public void deleteSelectedPlaylists(ObservableList<Playlist> playlistsToDelete) throws SQLException {
        for (Playlist playlist : playlistsToDelete) {
            playlistList.remove(playlist);
        }
    }

    public void loadSongs() throws SQLException {
        playlistList.clear();
        playlistList.addAll(playlistDAO.getAllPlaylists());

    }

    public void updatePlaylist(Playlist playlistToUpdate) throws SQLException {
        playlistDAO.updatePlaylist(playlistToUpdate);
    }




}







