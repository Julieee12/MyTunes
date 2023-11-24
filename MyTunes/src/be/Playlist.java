package be;

//This class represents the whole List of the songs where the songs are gonna be present.

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private List<Song> allsongs;
    private String name;
    private int id;


    public Playlist(){
        this.allsongs=new ArrayList<>();
        this.id=id;
        this.name=name;
    }
    public List<Song> getAllsongs(){
        return allsongs;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void addSong(Song song){
        allsongs.add(song);
    }
    public void removeSong(Song song){
        allsongs.remove(song);
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }


}
