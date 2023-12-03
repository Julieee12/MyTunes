package bll;

import be.Song;
import dal.ISongDAO;
import dal.SongDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class SongManager {

    private static ObservableList<Song> songList = FXCollections.observableArrayList();
    ISongDAO songDAO = new SongDAO();




    //ArrayList<Song> songList = new ArrayList<Song>();

    public void createSong(String title, String artist, String category, Double time, String path){
        Song newSong = new Song(artist, title, time, category, path); // new object
        songList.add(newSong);
        songDAO.createSong(newSong); //creates a song in database as well
    }

    public ObservableList<Song> returnSongList() throws SQLException {
        loadSongs();
        return songList;
    }

    public void deleteSelectedSongs(ObservableList<Song> songsToDelete){
        songList.removeAll(songsToDelete);
    }

    public void loadSongs() throws SQLException {
        songList.clear();
        songList.addAll(songDAO.getAllSongs());

    }



}
