package dal;

import be.Song;

import java.sql.SQLException;
import java.util.List;

public interface ISongDAO {

    public void createSong (Song s);
    public Song getSong (int id);
    public List<Song> getAllSongs() throws SQLException;
}