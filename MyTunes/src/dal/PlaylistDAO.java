package dal;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
        try (Connection con = dbConnection.getConnection()) {
            String sql = "SELECT * FROM Songs WHERE ID=?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setInt(1, id);
            ResultSet rs = pt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("Title");

                return new Playlist(title, id);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePlaylist(Playlist newPlaylist) throws SQLException {
        try (Connection connection = dbConnection.getConnection()) {
            String sql = "UPDATE Playlists SET Name=? WHERE IDP=?";
            // When updating a playlist we only care about what to change (given by the id,
            // which will not change on the new one either) and what to overwrite the fields to
            // (the modified version of the playlist contains that)

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newPlaylist.getPlaylistName());
                preparedStatement.setInt(2, newPlaylist.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throw new SQLException("Could not update playlist.", throwables);
        }
    }

    @Override
    public List<Playlist> getAllPlaylists() {
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
    public void deletePlaylist(ObservableList<Playlist> playlistsToDelete) {
        try (Connection con = dbConnection.getConnection()) {
            String sql = "DELETE FROM Playlists WHERE IDP=?";
            try (PreparedStatement pt = con.prepareStatement(sql)) {
                for (Playlist playlist : playlistsToDelete) {
                    int id = playlist.getId();
                    pt.setInt(1, id);
                    pt.addBatch();
                }
                pt.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    /**
     * Retrieves a list of songs that belong to a specific playlist from the database.
     * @param playlist The playlist for which songs are to be retrieved.
     * @return A list of songs associated with the given playlist.
     */
    public List<Song> getAllSongsInPlaylist(Playlist playlist) throws SQLServerException {
        ArrayList<Song> songsInPlaylist = new ArrayList<>();
        try (Connection con = dbConnection.getConnection()) {
            // SQL query to join Songs and PlaylistsSongs tables and retrieve songs for the current playlist
            String sql = "SELECT * FROM Songs " +
                    "JOIN PlaylistsSongs ON Songs.ID = PlaylistsSongs.SongID " +
                    "WHERE PlaylistsSongs.PlaylistID = ?";
            try (PreparedStatement pt = con.prepareStatement(sql)) {
                // Set the PlaylistID parameter in the SQL query to the ID of the current playlist
                pt.setInt(1, playlist.getId());

                try (ResultSet rs = pt.executeQuery()) {
                    while (rs.next()) {
                        String title = rs.getString("Title");
                        String artist = rs.getString("Artist");
                        String category = rs.getString("Category");
                        double time = rs.getDouble("Time");
                        int id = rs.getInt("ID");
                        String path = rs.getString("Path");

                        Song s = new Song(artist,title,time,category,path,id);
                        songsInPlaylist.add(s);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return songsInPlaylist;
    }
}
