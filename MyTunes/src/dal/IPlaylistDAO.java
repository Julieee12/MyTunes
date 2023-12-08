package dal;

import be.Playlist;
import be.Song;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistDAO {
    void createPlaylist (Playlist p);
    Playlist getPlaylist (int id);
    void updatePlaylist(Playlist newPLaylist) throws SQLException;
    List<Playlist> getAllPlaylists() throws SQLException;
    void deletePlaylist(ObservableList<Playlist> playlistsToDelete) throws SQLException;
    void addSongToPlaylist(Playlist playlist, Song song)throws SQLException;
    List<Song> getAllSongsInPlaylist(Playlist playlist)throws SQLException;
    void removeSongsFromPlaylist(Playlist selectedPlaylist, List<Song> songsToDelete);
}
