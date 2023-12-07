package gui;


import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import bll.SongManager;
import dal.PlaylistDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MPModel {
    private final static MPModel instance = new MPModel(); //ensures that by using Singelton all controllers use the same model
    private SongManager playerSongLogic;
    private PlaylistManager playerPlaylistLogic;
    private MPController observerController;



    public MPModel() {
        this.playerSongLogic = new SongManager();
        this.playerPlaylistLogic = new PlaylistManager(new PlaylistDAO());
    }

    public static MPModel getInstance(){
        return instance;
    }

    public void createSong(String title, String artist, String category, Double time, String path){
        playerSongLogic.createSong(title, artist, category, time, path);
        //observerController.initialize(playerLogic.returnSongList());


    }
    public void deleteSong(ObservableList<Song> songsToDelete) throws SQLException {
        playerSongLogic.deleteSelectedSongs(songsToDelete);
    }


    public ObservableList<Song> returnSongList() throws SQLException {
        return playerSongLogic.returnSongList();

    }

    public void updateSong(Song songToEdit, String title, String artist, String category, Double time, String path) throws SQLException {
        playerSongLogic.updateSong(songToEdit, title, artist, category, time, path);
    }

    public void createSinglePlaylist (String playlistName){
        playerPlaylistLogic.createPlaylist(playlistName);
    }

    public void updatePlaylist(Playlist playlistToUpdate) throws SQLException {
        playerPlaylistLogic.updatePlaylist(playlistToUpdate);
    }

    public ObservableList<Playlist> returnPlaylist() throws SQLException {
        return playerPlaylistLogic.returnPlaylist();
    }

    public void deletePlaylists(ObservableList<Playlist> playlistsToDelete) throws SQLException {
        playerPlaylistLogic.deleteSelectedPlaylists(playlistsToDelete);
    }


}
