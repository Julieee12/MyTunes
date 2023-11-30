package bll;

import be.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class logic {

    private ObservableList<Song> songList = FXCollections.observableArrayList();

    //ArrayList<Song> songList = new ArrayList<Song>();

    public void createSong(String title, String artist, String category, Double time, String path){
        Song newSong = new Song(artist, title, time, category, path); // new object
        songList.add(newSong);

    }

    public ObservableList<Song> returnSongList(){
        Song testSong = new Song("Test","as",22,"Pop","abx");
        songList.add(testSong);
        return songList;
    }



}
