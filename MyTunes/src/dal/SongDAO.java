package dal;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongDAO {
    private final databaseConnector dbConnection = new databaseConnector();

    /**
     * This class implements the interface for managing songs in a database. It includes methods for creating,
     * updating, retrieving, editing, and deleting song records. The class utilizes a database connection and
     * SQL queries to perform these operations.
     *
     * Methods:
     * - createSong(Song s): Inserts a new song record into the database with the provided Song object.
     * - updateSong(Song oldSong, Song newSong): Updates the information of an existing song in the database.
     * - getSong(int id): Retrieves a song record from the database based on the provided ID.
     * - editSong(int id): Placeholder method for editing a song; returns the provided ID.
     * - getAllSongs(): Retrieves a list of all songs from the database.
     * - deleteSongs(ObservableList<Song> songsToDelete): Deletes multiple songs from the database based on the provided list.
     */
    @Override
    public void createSong(Song s) {
        try(Connection con = dbConnection.getConnection()) {
            String sql = "INSERT INTO Songs (Title, Artist, Category, Time, Path)" + " VALUES (?,?,?,?,?)";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, s.getSongTitle());
            pt.setString(2, s.getArtist());
            pt.setString(3, s.getCategory());
            pt.setDouble(4, s.getDuration());
            pt.setString(5, s.getFilePath());
            pt.execute();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateSong(Song oldSong, Song newSong) throws SQLException {
        try {
            String sql = "UPDATE songs SET Title=?, Artist=?, Category=?, Time=?, Path=? WHERE id=?";

            Connection connection = dbConnection.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newSong.getSongTitle());
                preparedStatement.setString(2, newSong.getArtist());
                preparedStatement.setString(3, newSong.getCategory());
                preparedStatement.setDouble(4, newSong.getDuration());
                preparedStatement.setString(5, newSong.getPath());
                preparedStatement.setInt(6, oldSong.getId());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException throwables) {
            throw new SQLException("Could not update song.", throwables);
        }
    }

    @Override
    public Song getSong(int id) {

        try (Connection con = dbConnection.getConnection()) {
            String sql = "SELECT * FROM Songs WHERE ID=?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setInt(1, id);
            ResultSet rs = pt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                Double time = rs.getDouble("Time");
                String path = rs.getString("Path");

                return new Song(artist, title, time, category, path, id);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editSong (int id){
        return id;
    }


    @Override
    public List<Song> getAllSongs() throws SQLException {
        List <Song> songs = new ArrayList<>();
        try(Connection con = dbConnection.getConnection()){
            String sql = "SELECT * FROM Songs";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){

                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                Double time = rs.getDouble("Time");
                int id = rs.getInt("ID");
                String path = rs.getString("Path");

                Song s = new Song(artist,title,time,category,path,id);
                songs.add(s);

            }
            return songs;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Deletes the song with specific ID from the database.
     * @param songsToDelete song the user wants to delete.
     * @throws SQLException
     */
    @Override
    public void deleteSongs(ObservableList<Song> songsToDelete) throws SQLException {
        try (Connection con = dbConnection.getConnection()) {
            String sql = "DELETE FROM Songs WHERE ID=?";
            try (PreparedStatement pt = con.prepareStatement(sql)) {
                for (Song song : songsToDelete) {
                    int id = song.getId();
                    pt.setInt(1, id);
                    pt.addBatch();
                }
                pt.clearParameters();
                pt.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
