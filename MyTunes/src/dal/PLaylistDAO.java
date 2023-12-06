package dal;

import be.Playlist;
import be.Song;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO{
    private final databaseConnector dbConnection = new databaseConnector();


    @Override
    public void createPlaylist(Playlist p) {
        
    }

    @Override
    public Playlist getPlaylist(int id) {
        return null;
    }

    @Override
    public void updatePlaylist(Playlist oldPlaylist, Playlist newPLaylist) throws SQLException {

    }

    @Override
    public List<Playlist> getAllPlaylists() throws SQLException {
        return null;
    }

    @Override
    public void deletePlaylist(ObservableList<Playlist> songsToDelete) throws SQLException {

    }
}
