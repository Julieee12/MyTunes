package be;
//This class represents the song entity.It creates the parameters of the songs.There is also set and get methods wich allow us to change those parameters.
public class Song {
    private String artist;
    private String songTitle;
    private double duration;
    private String category;
    private int currentSong;
    private String filePath;




    public Song(String artist, String songtitle, double duration, String category, String filePath) {
        this.artist = artist;
        this.songTitle = songtitle;
        this.duration = duration;
        this.category =category;
        this.filePath = filePath;
    }
    public String getFilePath(){
        return filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public double getDuration() {
        return duration;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
