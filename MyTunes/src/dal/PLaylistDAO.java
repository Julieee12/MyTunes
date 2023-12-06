package dal;

import be.Playlist;
import be.Song;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO{
    private final databaseConnector dbConnection = new databaseConnector();


    @Override
    public void createPlaylist(Playlist p) {
        try(Connection con = dbConnection.getConnection()) {
            String sql = "INSERT INTO Playlists (Name)" + " VALUES (?)";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, p.getPlaylistName());
            pt.execute();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        List <Playlist> playlists = new ArrayList<>();
        try(Connection con = dbConnection.getConnection()){
            String sql = "SELECT * FROM Playlists";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){

                String name = rs.getString("Name");
                int id = rs.getInt("IDP");

                Playlist p = new Playlist(name,id);
                playlists.add(p);

            }
            return playlists;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePlaylist(ObservableList<Playlist> songsToDelete) throws SQLException {

    }

    public void addSongToPlaylist(Playlist playlist, Song song) throws SQLException {
        try (Connection con = dbConnection.getConnection()) {
            String sql = "INSERT INTO PlaylistsSongs (PlaylistID, SongID) VALUES (?, ?)";
            try (PreparedStatement pt = con.prepareStatement(sql)) {
                pt.setInt(1, playlist.getId());
                pt.setInt(2, song.getId());
                pt.execute();
            }
        }
    }
}
