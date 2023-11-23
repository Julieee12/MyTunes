package be;
//This class represents the song entity.It creates the parameters of the songs.There is also set and get methods wich allow us to change those parameters.
public class Song {
    private String artist;
    private String songtitle;
    private double duration;
    private String album;


    public Song(String artist, String songtitle, int duration, String album) {
        this.artist = artist;
        this.songtitle = songtitle;
        this.duration = duration;
        this.album=album;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongtitle() {
        return songtitle;
    }

    public double getDuration() {
        return duration;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSongtitle(String songtitle) {
        this.songtitle = songtitle;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
