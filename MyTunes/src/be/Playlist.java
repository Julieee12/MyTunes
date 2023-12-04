package be;

//This class represents the whole List of the songs where the songs are gonna be present.

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private ArrayList<Song> allSongs;
    private String playlistName;



    public Playlist(String playlistName){
        this.allSongs = new ArrayList<Song>();
        this.playlistName = playlistName;

    }

    public String getName(){
        return playlistName;
    }
    public void setName(String name) {
        this.playlistName = name;
    }
    public void addSong(Song song){
        allSongs.add(song);
    }
    public List<Song> getAllsongs(){
        return allSongs;
    }

    public void removeSong(Song song){
        allSongs.remove(song);
    }

    public double totalTime() {
        double totalTime = 0;

        for(Song song : allSongs){
            totalTime = totalTime + song.getDuration();
        }
        return totalTime;
    }

    public int songCount(){
        return 0;
    }


}
