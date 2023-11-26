package bll;

import be.Song;

public class logic {

    public void createSong(String title, String artist, String category, Double time, String path){
        Song newSong = new Song(artist, title, time, category, path); // new object

    }
}
