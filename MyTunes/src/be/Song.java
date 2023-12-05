package be;
//This class represents the song entity.It creates the parameters of the songs.There is also set and get methods wich allow us to change those parameters.
public class Song {
    private String artist;
    private String songTitle;
    private double duration;
    private String category;
    private int currentSong;
    private String filePath;
    private int id ;


// two constructors were created because one is for database where id is created and one is for gui where id is not needed

    public Song(String artist, String songtitle, double duration, String category, String filePath) {
        this.artist = artist;
        this.songTitle = songtitle;
        this.duration = duration;
        this.category =category;
        this.filePath = filePath;
    }
    public Song(String artist, String songtitle, double duration, String category, String filePath, int id) {
        this.artist = artist;
        this.songTitle = songtitle;
        this.duration = duration;
        this.category =category;
        this.filePath = filePath;
        this.id = id;
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
    public int getId(){
        return id;
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

    public String getPath() {
        return filePath;
    }

    //saves from seconds to String for table view
    public String getSongDurationString() {

        long minutes = (long) ((duration % 3600) / 60);
        long seconds = (long) (duration % 60);

        return String.format("%02d:%02d", minutes, seconds);
    }
}


