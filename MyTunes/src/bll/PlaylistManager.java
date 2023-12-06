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
    IPlaylistDAO playlistDAO = new PlaylistDAO();

    public void createPlaylist (String playlistName){ //creates single playlist
        Playlist newPlaylist = new Playlist(playlistName);
        playlistList.add(newPlaylist);
        playlistDAO.createPlaylist(newPlaylist);
    }

    public ObservableList<Playlist> returnPlaylist() throws SQLException {
        loadPlaylist();
        return playlistList;
    }

    public void deleteSelectedPlaylists(ObservableList<Playlist> playlistsToDelete) throws SQLException {
        for (Playlist playlist : playlistsToDelete) {
            playlistList.remove(playlist);
        }
    }

    public void loadPlaylist() throws SQLException {
        playlistList.clear();
        playlistList.addAll(playlistDAO.getAllPlaylists());

    }
    public void addSongToPlaylist(Playlist playlist, Song song) throws SQLException {
        playlistDAO.addSongToPlaylist(playlist, song);
    }




}







