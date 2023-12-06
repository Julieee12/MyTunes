package dal;

import be.Playlist;
import be.Song;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistDAO {
    public void createPlaylist (Song s);
    public Playlist getSong (int id);
    void updatePlaylist(Song oldSong, Song newSong) throws SQLException;
    public List<Playlist> getAllPlaylists() throws SQLException;
    public void deletePlaylist(ObservableList<Song> songsToDelete) throws SQLException;

}
