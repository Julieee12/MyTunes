package dal;

import be.Playlist;
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
}
