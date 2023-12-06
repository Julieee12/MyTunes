package dal;

import be.Playlist;
import be.Song;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO{
    @Override
    public void createPlaylist(Song s) {

    }

    @Override
    public Playlist getSong(int id) {
        return null;
    }

    @Override
    public void updatePlaylist(Song oldSong, Song newSong) throws SQLException {

    }

    @Override
    public List<Playlist> getAllPlaylists() throws SQLException {
        return null;
    }

    @Override
    public void deletePlaylist(ObservableList<Song> songsToDelete) throws SQLException {

    }
}
