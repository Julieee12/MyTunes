package dal;

import be.Playlist;
import be.Song;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistDAO {
    public void createPlaylist (Playlist p);
    public Playlist getPlaylist (int id);
    void updatePlaylist(Playlist newPLaylist) throws SQLException;
    public List<Playlist> getAllPlaylists() throws SQLException;
    public void deletePlaylist(ObservableList<Playlist> songsToDelete) throws SQLException;

}
