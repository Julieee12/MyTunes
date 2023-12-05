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

    public String getPlaylistName(){
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

    public String getTotalTime() {
        double totalSeconds = 0;

        for (Song song : allSongs) {
            totalSeconds += Math.round(song.getDuration()); // Round to the nearest second
        }

        long hours = (long) (totalSeconds / 3600);
        long minutes = (long) ((totalSeconds % 3600) / 60);
        long seconds = (long) (totalSeconds % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public int getSongCount(){
        return allSongs.size();
    }


}
