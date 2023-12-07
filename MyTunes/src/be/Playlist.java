package be;



import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private ArrayList<Song> allSongs;
    private String playlistName;
    private int id ;



    public Playlist(String playlistName){
        this.allSongs = new ArrayList<Song>();
        this.playlistName = playlistName;

    }
    public Playlist(String playlistName, int id){
        this.allSongs = new ArrayList<Song>();
        this.playlistName = playlistName;
        this.id = id;

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

    public void setAllSongs(ArrayList<Song> songs) {
        this.allSongs = songs;
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


    public int getId() {
        return id;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
