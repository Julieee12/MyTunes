package be;

//This class represents the whole List of the songs where the songs are gonna be present.

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private List<Song> allsongs;


    public Playlist(){
        this.allsongs=new ArrayList<>();

    }
    public void addSong(Song song){
        allsongs.add(song);
    }
    public void removeSong(Song song){
        allsongs.remove(song);
    }


}
